/**   
 * @Title: TAuthAgentServiceImpl.java
 * @Package com.ziwow.qyhapp.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-3 下午5:00:07
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthAgent;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthCorp;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhAuthAgentMapper;
import com.ziwow.scrmapp.qyh.service.QyhAuthAgentService;
import com.ziwow.scrmapp.qyh.service.QyhAuthCorpService;
import com.ziwow.scrmapp.qyh.service.QyhWxSaaSService;
import com.ziwow.scrmapp.qyh.vo.AgentVo;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;

/**
 * @ClassName: TAuthAgentServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午5:00:07
 * 
 */
@Service
public class QyhAuthAgentServiceImpl extends GenericServiceImpl<QyhAuthAgent, Long> implements QyhAuthAgentService {

	Logger LOG = LoggerFactory.getLogger(QyhAuthAgentServiceImpl.class);
	@Autowired
	private QyhAuthAgentMapper qyhAuthAgentMapper;
	
	@Autowired
	private QyhWxSaaSService qyhWxSaaSService;
	
	@Autowired
	private QyhAuthCorpService qyhAuthCorpService;
	@Override
	public boolean checkAuthAgenExist(String appid,
			String authcorpid) {
		return qyhAuthAgentMapper.checkAuthAgenExist(appid, authcorpid);
	}
	
	@Transactional
	@Override
	public void changeAuth(String suiteId, String authCorpId) {
		QyhAuthCorp authCorp = qyhAuthCorpService.checkAuthCorpExist(authCorpId, suiteId);
		LOG.info("变更授权,id=[{}]",authCorp.getId());
		//删除原来授权的应用
		int count = qyhAuthAgentMapper.deleteAUthAgenByAuthCorpId(authCorp.getId());
		LOG.info("删除原来授权的应用,返回条数：[{}]",count);
		//获取应用信息
		PermanentCodeVo permanentCodeVo=qyhWxSaaSService.getAuthInfo(suiteId, authCorpId);
		//t_qyh_auth_agent
		for(AgentVo vo:permanentCodeVo.getAuth_info().getAgent()){
			QyhAuthAgent tAuthAgent = new QyhAuthAgent();
			tAuthAgent.setAppid(vo.getAppid());
			tAuthAgent.setAgentid(vo.getAgentid());
			tAuthAgent.setAuthcorpid(authCorp.getId());
			tAuthAgent.setCreatetime(new Date());
			if(!this.checkAuthAgenExist(vo.getAppid(),authCorp.getId())){
				qyhAuthAgentMapper.insertSelective(tAuthAgent);
			}
			
		}
		
		//更新t_qyh_auth_corp
		QyhAuthCorp authCorpVo = new QyhAuthCorp();
		authCorpVo.setId(authCorp.getId());
		authCorpVo.setAuthcorpinfo(JSON.toJSONString(permanentCodeVo.getAuth_corp_info()));
		authCorpVo.setAuthappinfo(JSON.toJSONString(permanentCodeVo.getAuth_info()));
		qyhAuthCorpService.updateByPrimaryKeySelective(authCorpVo);
	}
}