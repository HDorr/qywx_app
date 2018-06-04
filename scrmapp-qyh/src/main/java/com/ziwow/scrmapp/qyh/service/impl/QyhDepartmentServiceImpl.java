/**   
 * @Title: TDepartmentServiceImpl.java
 * @Package com.ziwow.qyhapp.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-7 上午10:29:25
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ziwow.scrmapp.qyh.vo.WxSaaSCallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhDepartment;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhDepartmentMapper;
import com.ziwow.scrmapp.qyh.service.QyhDepartmentService;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.qyh.service.QyhWxSaaSService;
import com.ziwow.scrmapp.qyh.vo.AddreddBookDataVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: TDepartmentServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-7 上午10:29:25
 * 
 */
@Service
public class QyhDepartmentServiceImpl extends GenericServiceImpl<QyhDepartment, Long> implements QyhDepartmentService {

	Logger LOG = LoggerFactory.getLogger(QyhDepartmentServiceImpl.class);
	@Autowired
	private QyhDepartmentMapper qyhDepartmentMapper;

	@Autowired
	private QyhWxSaaSService qyhWxSaaSService;

	@Autowired
	private QyhUserService qyhUserService;

	private Lock lock = new ReentrantLock();// 锁对象

	@Override
	public void batchInsertDepartment(List<QyhDepartment> list) {
		qyhDepartmentMapper.batchInsertDepartment(list);
	}

	/*
	 * 参数 说明 errcode 返回码 errmsg 返回码的文本描述信息 next_seq 下一页数据的序号 next_offset
	 * 下一页数据的偏移量 is_last 1/0表示是否最后一页数据 data 数据数组 type 数据项类型，分别表示 1 用户进入管理范围 2
	 * 用户退出管理范围 3 删除用户 4 修改用户 5 部门进入管理范围 6 部门退出管理范围 7 删除部门 8 修改部门 9 新增部门 user
	 * 用户详情，当type为1、4时设置 userid 用户id，当type为2、3时设置 department 部门详情，当type为5、8、9时设置
	 * department_id 部门ID，当type为6、7时设置
	 */
	@Override
	public void contactSync(String suiteId, String authCorpid) {
		LOG.info("通讯录同步,传入参数suiteId=[{}],authCorpid=[{}]", suiteId, authCorpid);
		String cropAccessToken = qyhWxSaaSService.getCorpAccessToken(suiteId, authCorpid);
		List<AddreddBookDataVo> addreddBookDataVos = qyhWxSaaSService.getAddreddBookData(cropAccessToken, 0, 0);

		for (AddreddBookDataVo addreddBookDataVo : addreddBookDataVos) {
			lock.lock();
			try {
				if (addreddBookDataVo != null) {
					if (1 == addreddBookDataVo.getType()) {// 1 用户进入管理范围
						LOG.info("1 用户进入管理范围");
						List<QyhUser> tWeixinUsers = new ArrayList<QyhUser>();
						QyhUser vo = addreddBookDataVo.getUser();
						vo.setCorpid(authCorpid);
						tWeixinUsers.add(vo);
						qyhUserService.batchInsertWeiXinUser(tWeixinUsers);
					} else if (2 == addreddBookDataVo.getType()) {// 2 用户退出管理范围
						LOG.info("2 用户退出管理范围");
						// TODO

					} else if (3 == addreddBookDataVo.getType()) {// 3 删除用户
						LOG.info("3 删除用户");
						List<String> userIds = new ArrayList<String>();
						userIds.add(addreddBookDataVo.getUserid());
						qyhUserService.batchDeleteWeiXinUser(userIds, authCorpid);

					} else if (4 == addreddBookDataVo.getType()) {// 4 修改用户
						LOG.info("4 修改用户");
						List<QyhUser> tWeixinUsers = new ArrayList<QyhUser>();
						QyhUser vo = addreddBookDataVo.getUser();
						vo.setCorpid(authCorpid);
						tWeixinUsers.add(vo);
						qyhUserService.batchUpdateWeiXinUser(tWeixinUsers);

					} else if (5 == addreddBookDataVo.getType()) {// 5 部门进入管理范围
						LOG.info("5 部门进入管理范围");
						List<QyhDepartment> departments = new ArrayList<QyhDepartment>();
						QyhDepartment vo = new QyhDepartment();
						vo = addreddBookDataVo.getDepartment();
						vo.setCorpid(authCorpid);
						departments.add(vo);
						this.batchInsertDepartment(departments);

					} else if (6 == addreddBookDataVo.getType()) {// 6 部门退出管理范围
						LOG.info("6 部门退出管理范围");
						// TODO

					} else if (7 == addreddBookDataVo.getType()) {// 7 删除部门
						LOG.info("7 删除部门");
						List<Integer> departmentId = new ArrayList<Integer>();
						departmentId.add(addreddBookDataVo.getDepartment_id());
						this.batchDeleteDepartment(departmentId, authCorpid);

					} else if (8 == addreddBookDataVo.getType()) {// 8 修改部门
						LOG.info("8 修改部门");
						List<QyhDepartment> departments = new ArrayList<QyhDepartment>();
						QyhDepartment vo = new QyhDepartment();
						vo = addreddBookDataVo.getDepartment();
						vo.setCorpid(authCorpid);
						departments.add(vo);
						this.batchUpdateDepartment(departments);
					} else if (9 == addreddBookDataVo.getType()) {// 9 新增部门
						LOG.info("9 新增部门");
						List<QyhDepartment> departments = new ArrayList<QyhDepartment>();
						QyhDepartment vo = new QyhDepartment();
						vo = addreddBookDataVo.getDepartment();
						vo.setCorpid(authCorpid);
						departments.add(vo);
						this.batchInsertDepartment(departments);

					}
				}
			} finally {
				lock.unlock();
			}
		}

	}

	@Override
	public void batchUpdateDepartment(List<QyhDepartment> departments) {
		qyhDepartmentMapper.batchUpdateDepartment(departments);

	}

	@Override
	public void batchDeleteDepartment(List<Integer> departmentId, String corpId) {
		qyhDepartmentMapper.batchDeleteDepartment(departmentId, corpId);
	}

	@Override
	public void saveDepartment(WxSaaSCallbackInfo wxSaaSCallbackInfo) {
		QyhDepartment qyhDepartment = new QyhDepartment();
		qyhDepartment.setId(Long.parseLong(wxSaaSCallbackInfo.getId()));
		qyhDepartment.setCorpid(wxSaaSCallbackInfo.getAuthCorpId());
		qyhDepartment.setName(wxSaaSCallbackInfo.getName());
		qyhDepartment.setParentid(Long.parseLong(wxSaaSCallbackInfo.getParentId()));
		if (null == wxSaaSCallbackInfo.getOrder()) {
			qyhDepartment.setOrder(200L);
		}else{
			qyhDepartment.setOrder(Long.parseLong(wxSaaSCallbackInfo.getOrder()));
		}
		qyhDepartmentMapper.insert(qyhDepartment);
	}

	@Transactional
	@Override
	public void updateDepartment(WxSaaSCallbackInfo wxSaaSCallbackInfo) {
		QyhDepartment qyhDepartment = new QyhDepartment();
		qyhDepartment.setId(Long.parseLong(wxSaaSCallbackInfo.getId()));
		qyhDepartment.setCorpid(wxSaaSCallbackInfo.getAuthCorpId());
		if (null != wxSaaSCallbackInfo.getName()) {
			qyhDepartment.setName(wxSaaSCallbackInfo.getName());
		}
		if (null != wxSaaSCallbackInfo.getParentId()) {
			qyhDepartment.setParentid(Long.parseLong(wxSaaSCallbackInfo.getParentId()));
		}
		if (null == wxSaaSCallbackInfo.getOrder()) {
			qyhDepartment.setOrder(Long.parseLong(wxSaaSCallbackInfo.getOrder()));
		}
		qyhDepartmentMapper.updateByPrimaryKeySelective(qyhDepartment);
	}

	@Transactional
	@Override
	public void deleteDepartment(Long id) {
		qyhDepartmentMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public QyhDepartment getDepartmentByName(String deptName, String corpId) {
		return qyhDepartmentMapper.getDepartmentByName(deptName, corpId);
	}
}