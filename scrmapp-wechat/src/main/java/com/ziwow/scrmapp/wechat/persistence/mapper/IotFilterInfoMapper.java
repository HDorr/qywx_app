package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;

import java.util.List;

public interface IotFilterInfoMapper {
    void saveFilterInfos(List<IotFilterInfo> iotFilterInfos);
}
