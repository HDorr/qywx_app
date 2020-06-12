package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.vo.QtyUserVO;
import com.ziwow.scrmapp.wechat.vo.WechatUserVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import org.apache.ibatis.annotations.Update;

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


   int findUserLuckyByPhone(String mobilePhone);

    WechatUser selectUserByFansUnionIdIgnoreIsCancel(String unionId);

    /**
     * 通过手机号修改用户状态
     * @param status
     * @param phone
     */
    @Update("update t_wechat_user set status = #{status} where mobilePhone = #{phone} and status = 0")
    void updateStatusByPhone(@Param("status") int status,@Param("phone") String phone);

    /**
     * 修改手机号
     * @param fromPhone 依据手机号
     * @param toPhone 修改后的手机号
     */
    @Update("update t_wechat_user set mobilePhone = #{toPhone} where mobilePhone = #{fromPhone} and status = 0")
    void updatePhoneByPhone(@Param("fromPhone") String fromPhone, @Param("toPhone") String toPhone);

}