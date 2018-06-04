package com.ziwow.scrmapp.wechat.service;

import com.ziwow.scrmapp.common.bean.vo.SmsStatisticsVo;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-08 15:14
 */
public interface SmsSendRecordService {
    public void saveSmsRecord(SmsSendRecord smsSendRecord);
    public void updateSmsRecord(SmsSendRecord smsSendRecord);
    public void updateSmsRecordRegTime(String mobile);
    public List<SmsSendRecord> get2SendSmsRecord();
    public List<SmsSendRecord> get5SendSmsRecord();
    public void batchSaveSmsSendRecord(List<SmsSendRecord> list);
    public boolean checkFirstSmsSend(String mobile, String smsContent);
    public boolean checkLaterSmsSend(String mobile, String smsContent);
    public void sendDispatchSms(Integer orderType, String mobile);
    public List<SmsStatisticsVo> getSmsStatistics(String dispatchDate, Integer smsType);
}