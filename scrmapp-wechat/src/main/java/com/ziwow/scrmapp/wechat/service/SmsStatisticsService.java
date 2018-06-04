package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.persistence.entity.SmsStatistics;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 15:14
 */
public interface SmsStatisticsService {
    public void batchSaveSmsStatisticsData(List<SmsStatistics> list);
    public void batchUpdateSmsStatisticsData(List<SmsStatistics> list);
    public SmsStatistics getSmsStatisticsByDateAndType(String syncTime, int type);
}