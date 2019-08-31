package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.vo.QtyUserVO;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;

/**
 * @包名 com.ziwow.scrmapp.api.core.user.persistence.mapper
 * @文件名 WechatUserMapper.java
 * @作者 john.chen
 * @创建日期 2017-2-15
 * @版本 V 1.0
 */
public interface WechatUserMapper {
     WechatUser getUserByOpenId(@Param("openId") String openId);

     WechatUser getUserByUnionId(@Param("unionId") String unionId);

     WechatUser getUserByMobilePhone(@Param("mobilePhone") String mobilePhone);

     void saveUser(WechatUser wechatUser);

    Integer updateUser(@Param("wechatUser") WechatUser wechatUser, @Param("wfId") Long wfId);

    WechatUser getUserByUserId(@Param("userId") String userId);

    List<WechatUser> getUserByRegisterSrc(@Param("src") Integer src);

    WechatUserVo getBaseUserInfoByUserId(String userId);

    QtyUserVO getQtyUserByUserId(@Param("userId") String userId);

    WechatUser selectUserByFansUnionId(@Param("unionId") String unionId);
}