package com.ziwow.scrmapp.qyh.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthCorp;

public interface QyhAuthCorpMapper extends GenericMapper<QyhAuthCorp, String>{
   
	@Select("SELECT id,corpId as corpid,suiteId as suiteid,corpName as corpname,suitePermanentCode as suitepermanentcode FROM `t_qyh_auth_corp` WHERE corpId = #{corpId} AND suiteId=#{suiteId}")
	public QyhAuthCorp checkAuthCorpExist(@Param("corpId")String corpId,@Param("suiteId") String suiteId);
}