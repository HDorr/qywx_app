package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * iot用户信息
 */
public interface IotUserInfoMapper {

    /**
     * 同步用户信息
     * @param userInfos
     */
    void saveUserInfos(List<IotUserInfo> userInfos);

    /**
     * 根据设备条码获取用户信息
     * @param sncode
     * @return
     */
    IotUserInfo queryUserInfoBySncode(@Param("sncode") String sncode);
}
