package com.ziwow.scrmapp.wechat.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.mapper.SmsMarketingMapper;
import com.ziwow.scrmapp.wechat.service.SmsMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 15:15
 */
@Service
public class SmsMarketingServiceImpl implements SmsMarketingService {

    @Autowired
    private SmsMarketingMapper smsMarketingMapper;

    @Override
    public List<SmsMarketing> getSmsMarketingLst() {
        return smsMarketingMapper.getSmsMarketingLst();
    }

    @Override
    public SmsMarketing getSmsMarketingByType(Integer orderType, Integer smsType) {
        return smsMarketingMapper.getSmsMarketingByType(orderType, smsType);
    }

    @Override
    public HashMap<Integer, SmsMarketing> getCacheSmsMarketing() {
        List<SmsMarketing> smsMarketingLst = smsMarketingMapper.getSmsMarketingLst();
        HashMap<Integer, SmsMarketing> smsmarketingMap = Maps.newHashMap();
        for(SmsMarketing smsMarketing : smsMarketingLst) {
            smsmarketingMap.put(smsMarketing.getOriginalType(), smsMarketing);
        }
        return smsmarketingMap;
    }
}