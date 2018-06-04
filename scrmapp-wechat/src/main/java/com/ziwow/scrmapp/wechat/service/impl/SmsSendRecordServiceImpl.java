package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.common.bean.vo.SmsStatisticsVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.persistence.mapper.SmsMarketingMapper;
import com.ziwow.scrmapp.common.persistence.mapper.SmsSendRecordMapper;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.wechat.enums.SmsMarketingEmus;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatUserMapper;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-04-11 16:49
 */
@Service
public class SmsSendRecordServiceImpl implements SmsSendRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsSendRecordServiceImpl.class);

    @Value("${dispatch.mobile}")
    private String dispatchMobile;
    @Autowired
    SmsSendRecordMapper smsSendRecordMapper;
    @Autowired
    SmsMarketingMapper smsMarketingMapper;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private MobileService mobileService;

    @Override
    public void saveSmsRecord(SmsSendRecord smsSendRecord) {
        smsSendRecordMapper.saveSmsRecord(smsSendRecord);
    }

    @Override
    public void updateSmsRecord(SmsSendRecord smsSendRecord) {
        smsSendRecordMapper.updateSmsRecord(smsSendRecord);
    }

    @Override
    @Async
    public void updateSmsRecordRegTime(String mobile) {
        smsSendRecordMapper.updateSmsRecordRegTime(mobile);
    }

    @Override
    public List<SmsSendRecord> get2SendSmsRecord() {
        return smsSendRecordMapper.get2SendSmsRecord();
    }

    @Override
    public List<SmsSendRecord> get5SendSmsRecord() {
        return smsSendRecordMapper.get5SendSmsRecord();
    }

    @Override
    public void batchSaveSmsSendRecord(List<SmsSendRecord> list) {
        if (null == list || list.isEmpty())
            return;
        smsSendRecordMapper.batchSaveSmsRecord(list);
    }

    @Override
    public boolean checkFirstSmsSend(String mobile, String smsContent) {
        if (mobile.equals(dispatchMobile) || StringUtils.isBlank(smsContent))
            return false;
        WechatUser wechatUser = wechatUserMapper.getUserByMobilePhone(mobile);
        if(null != wechatUser) {
            return false;
        }
        List<SmsSendRecord> list = smsSendRecordMapper.getSmsSendRecordByMobile(mobile);
        // 校验最近6天是否发送过
        if (list != null && !list.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkLaterSmsSend(String mobile, String smsContent) {
        if (mobile.equals(dispatchMobile) || StringUtils.isBlank(smsContent))
            return false;
        WechatUser wechatUser = wechatUserMapper.getUserByMobilePhone(mobile);
        return wechatUser == null;
    }

    @Override
    @Async
    public void sendDispatchSms(Integer orderType, String mobile) {
        try {
            SmsMarketing smsMarketing = smsMarketingMapper.getSmsMarketingByType(orderType, SmsMarketingEmus.SmsTypeEnum.TODAY.getCode());
            String smsContent = null != smsMarketing ? smsMarketing.getSmsContent() : "";
            if (checkFirstSmsSend(mobile, smsContent)) {
                LOG.info("400派单发送短信手机:{},短信内容:{}", mobile, smsContent);
                boolean flag = mobileService.sendContentByEmay(mobile, smsContent, Constant.MARKETING);
                // 短信发送成功加入记录表
                if (flag) {
                    SmsSendRecord smsSendRecord = new SmsSendRecord();
                    smsSendRecord.setMobile(mobile);
                    smsSendRecord.setSyncTime(new Date());
                    smsSendRecord.setOrderType(orderType);
                    smsSendRecord.setSendCount(SmsMarketingEmus.SmsSendEnum.ONE.getCode());
                    saveSmsRecord(smsSendRecord);
                }
            }
        } catch (Exception e) {
            LOG.info("400派单发送短信失败:", e);
        }
    }

    @Override
    public List<SmsStatisticsVo> getSmsStatistics(String dispatchDate, Integer smsType) {
        return smsSendRecordMapper.getSmsStatistics(dispatchDate, smsType);
    }
}