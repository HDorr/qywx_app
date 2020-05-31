package com.ziwow.scrmapp.wechat.service.impl;


import com.ziwow.scrmapp.common.iot.IotUserInfo;
import com.ziwow.scrmapp.wechat.persistence.mapper.IotUserInfoMapper;
import com.ziwow.scrmapp.wechat.service.IotUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IotUserInfoServiceImpl implements IotUserInfoService {

    @Autowired
    private IotUserInfoMapper iotUserInfoMapper;


    @Override
    public void saveUserInfos(List<IotUserInfo> userInfos) {
        iotUserInfoMapper.saveUserInfos(userInfos);
    }

    @Override
    public IotUserInfo queryUserInfoBySncode(String sncode) {
        return iotUserInfoMapper.queryUserInfoBySncode(sncode);
    }
}
