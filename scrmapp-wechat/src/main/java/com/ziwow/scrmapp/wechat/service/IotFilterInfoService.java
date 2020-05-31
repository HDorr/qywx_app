package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;

import java.util.List;

/**
 * iot滤芯信息
 */
public interface IotFilterInfoService {
    void saveFilterInfos(List<IotFilterInfo> iotFilterInfos);
}
