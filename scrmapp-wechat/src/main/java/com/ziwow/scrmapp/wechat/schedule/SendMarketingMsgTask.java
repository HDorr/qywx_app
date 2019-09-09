package com.ziwow.scrmapp.wechat.schedule;

import com.google.common.collect.Lists;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.wechat.enums.SmsMarketingEmus;
import com.ziwow.scrmapp.wechat.service.SmsMarketingService;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import com.ziwow.scrmapp.wechat.service.SmsStatisticsService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author songkaiqi
 * @since 2019/07/22/下午4:40
 */
@Component
@JobHandler("SendMarketingMsgTask")
public class SendMarketingMsgTask extends IJobHandler {

    private final Logger logger = LoggerFactory.getLogger(SendMarketingMsgTask.class);

    @Autowired
    private SmsSendRecordService smsSendRecordService;
    @Autowired
    private SmsMarketingService smsMarketingService;
    @Autowired
    private MobileService mobileService;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.info("定时发送短信营销......");
        XxlJobLogger.log("定时发送短信营销......");
        Map<Integer, SmsMarketing> maps = smsMarketingService.getCacheSmsMarketing();
        try {
            // 批量发送第2天的短信
            List<SmsSendRecord> sms2Lst = smsSendRecordService.get2SendSmsRecord();
            List<SmsSendRecord> sms2RecordLst = Lists.newArrayList();
            for (SmsSendRecord sendRecord : sms2Lst) {
                int originalType = convertOriginalType(SmsMarketingEmus.SmsTypeEnum.TWODAY.getCode(), sendRecord.getOrderType());
                SmsMarketing smsMarketing = maps.get(originalType);
                String smsContent = smsMarketing.getSmsContent();
                String msgMobile = sendRecord.getMobile();
                if (smsSendRecordService.checkLaterSmsSend(msgMobile, smsContent)) {
                    //短信开口关闭 2019年06月19日
                    boolean flag=true;
                    //boolean flag = mobileService.sendContentByEmay(msgMobile, smsContent, Constant.MARKETING);
                    // 短信发送成功加入记录表
                    if(flag) {
                        sendRecord.setSendCount(SmsMarketingEmus.SmsSendEnum.TWO.getCode());
                        sms2RecordLst.add(sendRecord);
                    }
                }
            }
            // 刷新短信发送记录
            smsSendRecordService.batchSaveSmsSendRecord(sms2RecordLst);

            // 批量发送第5天的短信
            List<SmsSendRecord> sms5Lst = smsSendRecordService.get5SendSmsRecord();
            List<SmsSendRecord> sms5RecordLst = Lists.newArrayList();
            for (SmsSendRecord sendRecord : sms5Lst) {
                int originalType = convertOriginalType(SmsMarketingEmus.SmsTypeEnum.FOURDAY.getCode(), sendRecord.getOrderType());
                SmsMarketing smsMarketing = maps.get(originalType);
                if(null != smsMarketing) {
                    String smsContent = smsMarketing.getSmsContent();
                    String msgMobile = sendRecord.getMobile();
                    if (smsSendRecordService.checkLaterSmsSend(msgMobile, smsContent)) {
                        //短信开口关闭 2019年06月19日
                        boolean flag=true;
                        //boolean flag = mobileService.sendContentByEmay(msgMobile, smsContent, Constant.MARKETING);
                        if(flag) {
                            sendRecord.setSendCount(SmsMarketingEmus.SmsSendEnum.THREE.getCode());
                            sms5RecordLst.add(sendRecord);
                        }
                    }
                }
            }
            // 刷新短信发送记录
            smsSendRecordService.batchSaveSmsSendRecord(sms5RecordLst);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送隔天及7天短信失败:" + e.getMessage(), e);
            XxlJobLogger.log("发送隔天及7天短信失败:" + e.getMessage(), e);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }

    public static int convertOriginalType(int smsType, int orderType) {
        if (smsType == 0 || orderType == 0) {
            return 0;
        }
        int originalType = 0;
        String strType = String.valueOf(smsType).concat(String.valueOf(orderType));
        if (strType.equals("11")) {
            originalType = 1;
        } else if (strType.equals("13")) {
            originalType = 2;
        } else if (strType.equals("12")) {
            originalType = 3;
        } else if (strType.equals("21")) {
            originalType = 4;
        } else if (strType.equals("23")) {
            originalType = 5;
        } else if (strType.equals("22")) {
            originalType = 6;
        } else if (strType.equals("31")) {
            originalType = 7;
        } else if (strType.equals("33")) {
            originalType = 8;
        } else if (strType.equals("32")) {
            originalType = 9;
        }
        return originalType;
    }
}
