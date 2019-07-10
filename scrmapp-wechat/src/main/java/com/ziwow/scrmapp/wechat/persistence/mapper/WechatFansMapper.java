package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;


/**
 * 
* @包名   com.ziwow.scrmapp.api.core.user.persistence.mapper   
* @文件名 WechatUserMapper.java   
* @作者   john.chen   
* @创建日期 2017-2-15   
* @版本 V 1.0
 */
public interface WechatFansMapper {
    public void saveWechatFans(WechatFans wechatFans);
    public void syncUserFansAndGetId(WechatFans wechatFans);
    public void updateWechatFans(WechatFans wechatFans);
    public void updWechatFansById(WechatFans wechatFans);
    public WechatFans getWechatFans(@Param("openId") String openId);
    public WechatFans getWechatFansByOpenId(@Param("openId") String openId);
    public WechatFans getFans(@Param("unionId") String unionId);
    public WechatFans getWechatFansById(@Param("pkId") Long pkId);
    public WechatFans getWechatFansByUserId(@Param("userId") String userId);
    public List<WechatFans> getWechatFansByPage(@Param("page")int page, @Param("size")int size);
    public Integer countWechatFans();
    List<TempWechatFans> selectTempWechatFansBtach1();
    List<TempWechatFans> selectTempWechatFansBtach2();
    List<TempWechatFans> selectTempWechatFansBtach3();
}