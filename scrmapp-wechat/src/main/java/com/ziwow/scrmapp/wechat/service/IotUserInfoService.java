package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.iot.IotUserInfo;

import java.util.List;

/**
 * iot用户信息
 */
public interface IotUserInfoService {
    /**
     * 同步用户信息
     * @param userInfos
     */
    void saveUserInfos(List<IotUserInfo> userInfos);

    /**
     * 根据设备编码获取用户信息
     * @param sncode
     * @return
     */
    IotUserInfo queryUserInfoBySncode(String sncode);
}
