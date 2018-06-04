package com.ziwow.scrmapp.wechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.MallPcUser;

/**
 * 
* @包名   com.ziwow.scrmapp.wechat.persistence.mapper   
* @文件名 MallPcUserMapper.java   
* @作者   john.chen   
* @创建日期 2017-5-18   
* @版本 V 1.0
 */
public interface MallPcUserMapper {
    public void saveMallPcUser(MallPcUser mallPcUser);
    MallPcUser getMallPcUserByMobile(@Param("mobile") String mobile);
}