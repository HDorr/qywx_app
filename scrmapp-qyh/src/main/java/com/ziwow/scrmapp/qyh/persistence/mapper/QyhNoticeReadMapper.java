package com.ziwow.scrmapp.qyh.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeRead;

public interface QyhNoticeReadMapper extends GenericMapper<QyhNoticeRead, Long>{
   
	@Select("SELECT id as id, notice_id as noticeId, user_id as userId, is_read as isRead, background_img_url as backgroundImgUrl, create_time as createTime, update_time as updateTime FROM t_qyh_notice_read WHERE notice_id=#{noticeId} AND user_id=#{userId}")
	public QyhNoticeRead getQyhNoticeReadByNoticeIdAndUserId(@Param("noticeId")String noticeId,@Param("userId")String userId);
	
	@Select("SELECT COUNT(1) FROM `t_qyh_notice_read` WHERE notice_id=#{noticeId} AND is_read = #{isRead}")
	public Integer getCountQyhNoticeRead(@Param("noticeId")String noticeId, @Param("isRead")int isRead);
	
	@Select("SELECT qu.userId as userId,qu.avatar as avatar,qu.`name` FROM t_qyh_user qu LEFT JOIN t_qyh_notice_read qnr ON qu.userId = qnr.user_id WHERE 1=1 AND notice_id=#{noticeId} AND is_read = 0 ")
	public List<QyhUser> getUnReadeUserList(@Param("noticeId")String noticeId);
}