package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;

import java.util.List;

/**
 * iot产品信息
 */
public interface IotEquipmentInfoService {

    /**
     * 同步iot设备信息
     * @param equipmentInfos
     */
    void saveEquipmentInfos(List<IotEquipmentInfo> equipmentInfos);

    /**
     * 根据sncode查询设备信息
     * @param sncode
     * @return
     */
    IotEquipmentInfo queryBySnCode(String sncode);
}
