package com.ziwow.scrmapp.common.persistence.mapper;

import com.ziwow.scrmapp.common.bean.GenericMapper;
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
 * @Create: 2018-04-08 14:04
 */
public interface SmsSendRecordMapper extends GenericMapper<SmsSendRecord, Long> {
    public List<SmsSendRecord> get2SendSmsRecord();
    public List<SmsSendRecord> get5SendSmsRecord();
    public void saveSmsRecord(SmsSendRecord smsSendRecord);
    public void updateSmsRecord(SmsSendRecord smsSendRecord);
    public void updateSmsRecordRegTime(@Param("mobile") String mobile);
    public void batchSaveSmsRecord(@Param("list")List<SmsSendRecord> list);
    public void batchUpdateSmsRecord(@Param("list")List<SmsSendRecord> list);
    public List<SmsStatisticsVo> getSmsStatistics(@Param("dispatchDate")String dispatchDate, @Param("smsType")Integer smsType);
    public List<SmsSendRecord> getSmsSendRecordByMobile(String mobile);
}