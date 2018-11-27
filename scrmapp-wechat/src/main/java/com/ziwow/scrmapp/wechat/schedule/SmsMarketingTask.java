package com.ziwow.scrmapp.wechat.schedule;

import com.google.common.collect.Lists;
import com.ziwow.scrmapp.common.bean.vo.SmsStatisticsVo;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.SmsMarketing;
import com.ziwow.scrmapp.common.persistence.entity.SmsSendRecord;
import com.ziwow.scrmapp.common.persistence.entity.SmsStatistics;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.enums.SmsMarketingEmus;
import com.ziwow.scrmapp.wechat.service.SmsMarketingService;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import com.ziwow.scrmapp.wechat.service.SmsStatisticsService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SmsMarketingTask {
    private final Logger logger = LoggerFactory.getLogger(SmsMarketingTask.class);

    @Value("${task.flag}")
    private String flag;
    @Autowired
    private SmsSendRecordService smsSendRecordService;
    @Autowired
    private SmsMarketingService smsMarketingService;
    @Autowired
    private MobileService mobileService;
    @Autowired
    private SmsStatisticsService smsStatisticsService;
    @Autowired
    private WechatOrdersService wechatOrdersService;
    // 每天18点执行
    @Scheduled(cron = "0 0 18 * * ?")
    public void SendMarketingMsg() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("定时发送短信营销......");
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
                    boolean flag = mobileService.sendContentByEmay(msgMobile, smsContent, Constant.MARKETING);
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
                        boolean flag = mobileService.sendContentByEmay(msgMobile, smsContent, Constant.MARKETING);
                        // 短信发送成功加入记录表
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
        }
    }


    // 每天凌晨一点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void smsMarketingStatistics() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("定时统计短信营销数据......");
        String orderDate = DateUtil.getYesteryDay();
        List<SmsStatistics> updList = Lists.newArrayList();
        List<SmsStatistics> saveList = Lists.newArrayList();
        List<Integer> typeLst = Lists.newArrayList(1, 2, 3);
        for (Integer type : typeLst) {
            List<SmsStatisticsVo> smsStatisticsVoLst = smsSendRecordService.getSmsStatistics(orderDate, type);
            if (smsStatisticsVoLst != null && !smsStatisticsVoLst.isEmpty()) {
                for(SmsStatisticsVo smsStatisticsVo : smsStatisticsVoLst) {
                    String dispatchDate = smsStatisticsVo.getDispatchDate();
                    int smsType = smsStatisticsVo.getSmsType();
                    SmsStatistics statistics = smsStatisticsService.getSmsStatisticsByDateAndType(dispatchDate, smsType);
                    Integer dispatchOrderNum = wechatOrdersService.getDispatchOrderNumByDate(dispatchDate);
                    if (null == statistics) {
                        statistics = new SmsStatistics();
                        statistics.setSyncTime(smsStatisticsVo.getDispatchDate());
                        statistics.setSyncOrderNum(dispatchOrderNum);
                        statistics.setSendSmsNum(smsStatisticsVo.getSendTolNum());
                        statistics.setInstallSmsNum(smsStatisticsVo.getInstallSendNum());
                        statistics.setRepairSmsNum(smsStatisticsVo.getRepairSendNum());
                        statistics.setMaintainSmsNum(smsStatisticsVo.getMaintainSendNum());
                        statistics.setRegisterNum(smsStatisticsVo.getRegisterNum());
                        statistics.setInstallRegisterNum(smsStatisticsVo.getInstallRegisterNum());
                        statistics.setRepairRegisterNum(smsStatisticsVo.getRepairRegisterNum());
                        statistics.setMaintainRegisterNum(smsStatisticsVo.getMaintainRegisterNum());
                        statistics.setSmsType(type);
                        saveList.add(statistics);
                    } else {
                        statistics.setSyncOrderNum(dispatchOrderNum);
                        statistics.setSendSmsNum(smsStatisticsVo.getSendTolNum());
                        statistics.setInstallSmsNum(smsStatisticsVo.getInstallSendNum());
                        statistics.setRepairSmsNum(smsStatisticsVo.getRepairSendNum());
                        statistics.setMaintainSmsNum(smsStatisticsVo.getMaintainSendNum());
                        statistics.setRegisterNum(smsStatisticsVo.getRegisterNum());
                        statistics.setInstallRegisterNum(smsStatisticsVo.getInstallRegisterNum());
                        statistics.setRepairRegisterNum(smsStatisticsVo.getRepairRegisterNum());
                        statistics.setMaintainRegisterNum(smsStatisticsVo.getMaintainRegisterNum());
                        updList.add(statistics);
                    }
                }
            }
        }
        // 统计数据批量入库
        if(!saveList.isEmpty()) {
            smsStatisticsService.batchSaveSmsStatisticsData(saveList);
        }
        // 统计数据批量修改
        if(!updList.isEmpty()) {
            smsStatisticsService.batchUpdateSmsStatisticsData(updList);
        }
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