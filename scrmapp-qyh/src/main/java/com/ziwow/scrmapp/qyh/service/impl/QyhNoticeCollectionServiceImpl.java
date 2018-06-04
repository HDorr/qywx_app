/**   
* @Title: QyhNoticeCollectionServiceImpl.java
* @Package com.ziwow.scrmapppc.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:21:50
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeCollection;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhNoticeCollectionMapper;
import com.ziwow.scrmapp.qyh.service.QyhNoticeCollectionService;

/**
 * @ClassName: QyhNoticeCollectionServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:21:50
 * 
 */
@Service
public class QyhNoticeCollectionServiceImpl extends GenericServiceImpl<QyhNoticeCollection, Long> implements QyhNoticeCollectionService{

	@Resource
	private QyhNoticeCollectionMapper qyhNoticeCollectionMapper;
	
	
	@Override
	public boolean isCollectionNotice(String noticeId, String userId) {
		return qyhNoticeCollectionMapper.isCollectionNotice(noticeId, userId);
	}


	@Override
	public int deleteCollectionNotice(String noticeId, String userId) {
		return qyhNoticeCollectionMapper.deleteCollectionNotice(noticeId, userId);
	}

}
