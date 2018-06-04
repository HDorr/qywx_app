package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;
import com.ziwow.scrmapp.common.bean.vo.AppointmentMsgVo;

import java.sql.SQLDataException;
import java.util.List;

/**
 * Created by xiaohei on 2017/3/9.
 * 管理用户地址接口
 */
public interface WechatUserAddressService {

    List<WechatUserAddress> findUserAddresList(String userId);

    Long findAddressByAid(Long aId);

    WechatUserAddress findAddress(Long addressId);

    void saveAddress(WechatUserAddress wechatUserAddress);

    int insertAndGetId(WechatUserAddress wechantUserAddress);

    Integer updateAddress(WechatUserAddress wechatUserAddress);

    void updateAddressSelective(WechatUserAddress wechatUserAddress);

    Integer deleteAddress(String userId, Long addressId);

    void delAddressById(Long addressId);

    boolean updateDefault(String userId, Long addressId) throws SQLDataException;

    void syncSaveAddressToMiniApp(WechatUserAddress wechatUserAddress);

    void syncUpdateAddressToMiniApp(WechatUserAddress wechatUserAddress);

    void syncDelAddressToMiniApp(String aId);

    void syncSetDefaultAddressToMiniApp(String aId);
}
