package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;

import java.util.List;

/**
 * iot滤芯信息
 */
public interface IotFilterInfoService {

    /**
     * 同步iot滤芯寿命信息
     * @param iotFilterInfos
     */
    void saveFilterInfos(List<IotFilterInfo> iotFilterInfos);

    /**
     * 根据滤芯剩余使用天数查询滤芯
     * @param i
     */
    void queryByFilterLife(int i);
}
