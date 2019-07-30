package com.ziwow.scrmapp.wechat.persistence.mapper;


import com.ziwow.scrmapp.wechat.persistence.entity.WechatRegister;
import org.apache.ibatis.annotations.Insert;

public interface WechatRegisterMapper {

    @Insert("insert into t_engineer_pull_register (openid,phone,content,created_at,updated_at) values (#{openId},#{phone},#{content},now(),now());")
    int insertPullNewRegisterByEngineer(WechatRegister wechatRegister);
}