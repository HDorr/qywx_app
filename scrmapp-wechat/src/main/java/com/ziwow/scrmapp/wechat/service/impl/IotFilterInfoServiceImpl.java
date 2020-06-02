package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.iot.IotFilterInfo;
import com.ziwow.scrmapp.common.iot.IotFilterLifeInfo;
import com.ziwow.scrmapp.wechat.persistence.mapper.IotFilterInfoMapper;
import com.ziwow.scrmapp.wechat.schedule.iot.dto.IotFilterReminder;
import com.ziwow.scrmapp.wechat.service.IotFilterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IotFilterInfoServiceImpl implements IotFilterInfoService {

    @Autowired
    private IotFilterInfoMapper iotFilterInfoMapper;


    @Override
    public void saveFilterLifeInfos(List<IotFilterLifeInfo> iotFilterInfos) {
        iotFilterInfoMapper.saveFilterLifeInfos(iotFilterInfos);
    }

    @Override
    public List<IotFilterReminder> queryByFilterLife(int i) {
        return iotFilterInfoMapper.queryByFilterLife(i);
    }

    @Override
    public List<IotFilterReminder> queryByOverdueDate(String date) {
        return iotFilterInfoMapper.queryByOverdueDate(date);
    }

    @Override
    public void saveFilterInfos(List<IotFilterInfo> filterInfos) {
        iotFilterInfoMapper.saveFilterInfos(filterInfos);
    }
}
