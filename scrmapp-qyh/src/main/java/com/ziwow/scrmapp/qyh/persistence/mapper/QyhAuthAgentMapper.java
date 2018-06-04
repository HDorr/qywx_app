package com.ziwow.scrmapp.qyh.persistence.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthAgent;

public interface QyhAuthAgentMapper extends GenericMapper<QyhAuthAgent, Long>{
   
	@Select("SELECT COUNT(1) FROM `t_qyh_auth_agent` WHERE appid = #{appid}  AND authCorpId=#{authcorpid}")
	public boolean checkAuthAgenExist(@Param("appid")String appid,@Param("authcorpid")String authcorpid);

	@Delete("DELETE FROM t_qyh_auth_agent  WHERE authCorpId = #{authCorpId}")
	public int deleteAUthAgenByAuthCorpId(@Param("authCorpId")String authCorpId);
	
	public QyhAuthAgent getQyhAuthAgentByAppId(@Param("corpId") String corpId, @Param("appId") String appId,@Param("suitId")String suitId);

}