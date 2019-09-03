package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.pagehelper.Page;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;

import java.sql.SQLDataException;
import java.util.List;

/**
 * Created by xiaohei on 2017/3/9.
 * 管理用户地址接口
 */
public interface WechatUserAddressService {

    List<WechatUserAddress> pageUserAddress(String userId, Page page);

    long getCountAddress(String userId);

    List<WechatUserAddress> findUserAddresList(String userId);

    Long findAddressByAid(Long aId);

    WechatUserAddress findAddress(Long addressId);

    int saveAddress(WechatUserAddress wechatUserAddress);

    int insertAndGetId(WechatUserAddress wechantUserAddress);

    Integer updateAddress(WechatUserAddress wechatUserAddress);

    void updateAddressSelective(WechatUserAddress wechatUserAddress);

    Integer deleteAddress(String userId, Long addressId);

    int deleteUserAddress(String userId, Long addressId);

    void delAddressById(Long addressId);

    boolean updateDefault(String userId, Long addressId) throws SQLDataException;

    void syncSaveAddressToMiniApp(WechatUserAddress wechatUserAddress);

    /**
     * 异步保存地址到小程序
     *
     * @param wechatUserAddress {@link WechatUserAddress}
     */
    void syncSaveAddressToQysc(WechatUserAddress wechatUserAddress);

    void syncUpdateAddressToMiniApp(WechatUserAddress wechatUserAddress);

    /**
     * 异步更新地址到小程序
     *
     * @param wechatUserAddress {@link WechatUserAddress}
     */
    void syncUpdateAddressToQysc(WechatUserAddress wechatUserAddress);

    void syncDelAddressToMiniApp(String aId);

    void syncSetDefaultAddressToMiniApp(String aId);
}
