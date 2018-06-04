package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.bean.GenericMapper;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.persistence.entity.SmsStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 14:04
 */
public interface SmsStatisticsMapper extends GenericMapper<SmsStatistics, Long> {
    public void batchSaveSmsStatisticsData(@Param("list")List<SmsStatistics> list);
    public void batchUpdateSmsStatisticsData(@Param("list")List<SmsStatistics> list);
    public SmsStatistics getSmsStatisticsByDateAndType(@Param("syncTime")String syncTime, @Param("smsType")int smsType);
}