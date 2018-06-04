/**   
* @Title: QyhNoticeReadServiceImpl.java
* @Package com.ziwow.scrmapppc.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:25:42
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeRead;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhNoticeReadMapper;
import com.ziwow.scrmapp.qyh.service.QyhNoticeReadService;

/**
 * @ClassName: QyhNoticeReadServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:25:42
 * 
 */
@Service
public class QyhNoticeReadServiceImpl extends GenericServiceImpl<QyhNoticeRead, Long> implements QyhNoticeReadService{

	@Resource
	private QyhNoticeReadMapper qyhNoticeReadMapper;
	@Override
	public QyhNoticeRead getQyhNoticeReadByNoticeIdAndUserId(String noticeId,
			String userId) {
		return qyhNoticeReadMapper.getQyhNoticeReadByNoticeIdAndUserId(noticeId, userId);
	}
	@Override
	public Integer getCountQyhNoticeRead(String noticeId, int isRead) {
		return qyhNoticeReadMapper.getCountQyhNoticeRead(noticeId, isRead);
	}

	@Override
	public PageInfo<QyhUser> getUnReadeUserList(String noticeId,int pageSize, int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<QyhUser>(qyhNoticeReadMapper.getUnReadeUserList(noticeId));
	}

}
