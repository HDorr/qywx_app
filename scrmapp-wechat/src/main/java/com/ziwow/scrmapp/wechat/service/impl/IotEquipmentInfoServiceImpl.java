package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.iot.IotEquipmentInfo;
import com.ziwow.scrmapp.wechat.persistence.mapper.IotEquipmentInfoMapper;
import com.ziwow.scrmapp.wechat.service.IotEquipmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IotEquipmentInfoServiceImpl implements IotEquipmentInfoService {

    @Autowired
    private IotEquipmentInfoMapper iotEquipmentInfoMapper;

    @Override
    public void saveEquipmentInfos(List<IotEquipmentInfo> equipmentInfos) {
        iotEquipmentInfoMapper.saveEquipmentInfos(equipmentInfos);
    }

    @Override
    public IotEquipmentInfo queryBySnCode(String sncode) {
        return iotEquipmentInfoMapper.queryBySnCode(sncode);
    }
}
