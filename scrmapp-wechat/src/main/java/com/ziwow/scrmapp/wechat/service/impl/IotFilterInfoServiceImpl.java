package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.wechat.persistence.mapper.IotFilterInfoMapper;
import com.ziwow.scrmapp.wechat.service.IotFilterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IotFilterInfoServiceImpl implements IotFilterInfoService {

    @Autowired
    private IotFilterInfoMapper iotFilterInfoMapper;


    @Override
    public void saveFilterInfos(List<IotFilterInfo> iotFilterInfos) {
        iotFilterInfoMapper.saveFilterInfos(iotFilterInfos);
    }
}
