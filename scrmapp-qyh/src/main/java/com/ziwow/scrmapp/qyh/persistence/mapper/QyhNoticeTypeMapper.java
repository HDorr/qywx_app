package com.ziwow.scrmapp.qyh.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeType;

public interface QyhNoticeTypeMapper extends GenericMapper<QyhNoticeType, Integer>{
	
	
	@Select("SELECT assortment_id FROM t_qyh_notice_type WHERE assortment_parent_id=#{assortmentParentId}")
	public List<String> getQyhNoticeTypeByAssortmentParentId(@Param("assortmentParentId")Integer assortmentParentId);
}