package com.ziwow.scrmapp.qyh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeType;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhNoticeTypeMapper;
import com.ziwow.scrmapp.qyh.service.QyhNoticeTypeService;

/**
 * @ClassName: QyhNoticeTypeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:29:52
 * 
 */
@Service
public class QyhNoticeTypeServiceImpl extends GenericServiceImpl<QyhNoticeType, Integer> implements QyhNoticeTypeService{

	@Resource
	private QyhNoticeTypeMapper qyhNoticeTypeMapper;
	
	@Override
	public List<String> getQyhNoticeTypeByAssortmentParentId(
			Integer assortmentParentId) {
		return qyhNoticeTypeMapper.getQyhNoticeTypeByAssortmentParentId(assortmentParentId);
	}
	
}
