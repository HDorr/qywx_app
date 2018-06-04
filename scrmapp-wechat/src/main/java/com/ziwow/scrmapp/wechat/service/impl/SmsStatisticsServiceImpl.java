package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.persistence.entity.SmsStatistics;
import com.ziwow.scrmapp.common.persistence.mapper.SmsMarketingMapper;
import com.ziwow.scrmapp.common.persistence.mapper.SmsSendRecordMapper;
import com.ziwow.scrmapp.common.persistence.mapper.SmsStatisticsMapper;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.wechat.enums.SmsMarketingEmus;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatUserMapper;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import com.ziwow.scrmapp.wechat.service.SmsStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-11 16:49
 */
@Service
public class SmsStatisticsServiceImpl implements SmsStatisticsService {
    @Autowired
    SmsStatisticsMapper smsStatisticsMapper;

    @Override
    public void batchSaveSmsStatisticsData(List<SmsStatistics> list) {
        if(list == null || list.isEmpty())
            return;
        smsStatisticsMapper.batchSaveSmsStatisticsData(list);
    }

    @Override
    public void batchUpdateSmsStatisticsData(List<SmsStatistics> list) {
        if(list == null || list.isEmpty())
            return;
        smsStatisticsMapper.batchUpdateSmsStatisticsData(list);
    }

    @Override
    public SmsStatistics getSmsStatisticsByDateAndType(String syncTime, int type) {
        return smsStatisticsMapper.getSmsStatisticsByDateAndType(syncTime, type);
    }
}