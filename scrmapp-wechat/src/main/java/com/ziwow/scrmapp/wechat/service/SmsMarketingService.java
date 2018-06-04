package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 15:14
 */
public interface SmsMarketingService {
    public List<SmsMarketing> getSmsMarketingLst();
    public SmsMarketing getSmsMarketingByType(Integer orderType, Integer smsType);
    public HashMap<Integer, SmsMarketing> getCacheSmsMarketing();
}