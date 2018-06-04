package com.ziwow.scrmapp.qyh.persistence.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeCollection;

public interface QyhNoticeCollectionMapper extends GenericMapper<QyhNoticeCollection, Long>{
   
	@Select("SELECT COUNT(1) FROM t_qyh_notice_collection WHERE notice_id=#{noticeId} AND user_id=#{userId}")
	public boolean isCollectionNotice(@Param("noticeId")String noticeId, @Param("userId")String userId);
	
	@Delete("DELETE FROM t_qyh_notice_collection WHERE notice_id=#{noticeId} AND user_id=#{userId}")
	public int deleteCollectionNotice(@Param("noticeId")String noticeId,@Param("userId")String userId);
}