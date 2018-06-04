package com.ziwow.scrmapp.qyh.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNotice;
import com.ziwow.scrmapp.qyh.vo.QyhNoticeVo;

public interface QyhNoticeMapper extends GenericMapper<QyhNotice, Long>{
	
	public QyhNotice getQyhNoticeByNoticeId(@Param("noticeId")String noticeId);
	
	public List<QyhNoticeVo> getQyhNoticeByType(@Param("noticeTypeId")String noticeTypeId,@Param("noticeTitle")String noticeTitle);
	
	public List<QyhNoticeVo> getAllNoticeList(@Param("noticeTitle")String noticeTitle);
	
	public List<QyhNoticeVo> getMyCollectionNotice(@Param("noticeTitle")String noticeTitle, @Param("userId")String userId);
}