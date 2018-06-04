package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.wechat.vo.AppointmentMsgVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xiaohei on 2017/3/9.
 */
public interface WechatUserAddressMapper {
    List<WechatUserAddress> findListByUserId(@Param("userId") String userId);

    WechatUserAddress findAddress(@Param("id") Long addressId);

    Long findAddressByAid(@Param("aId") Long aId);

    int saveAddress(WechatUserAddress wechantUserAddress);

    Integer updateAddress(WechatUserAddress wechatUserAddress);

    void updateAddressSelective(WechatUserAddress wechatUserAddress);

    Integer deleteAddress(@Param("userId") String userId, @Param("id") Long addressId);

    void delAddressById(@Param("id") Long addressId);

    Integer setDefault(@Param("userId") String userId, @Param("id") Long addressId);

    Integer cancelDefault(@Param("userId") String userId);

}
