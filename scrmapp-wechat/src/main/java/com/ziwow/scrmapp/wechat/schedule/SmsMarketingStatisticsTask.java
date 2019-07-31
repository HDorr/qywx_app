package com.ziwow.scrmapp.wechat.schedule;

import com.google.common.collect.Lists;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.bean.vo.SmsStatisticsVo;
import com.ziwow.scrmapp.common.persistence.entity.SmsStatistics;
import com.ziwow.scrmapp.tools.utils.DateUtil;
import com.ziwow.scrmapp.wechat.service.SmsSendRecordService;
import com.ziwow.scrmapp.wechat.service.SmsStatisticsService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/07/22/下午4:55
 */
@Component
@JobHandler("SmsMarketingStatisticsTask")
public class SmsMarketingStatisticsTask extends IJobHandler {

    private final Logger logger = LoggerFactory.getLogger(SmsMarketingStatisticsTask.class);

    @Autowired
    private SmsSendRecordService smsSendRecordService;
    @Autowired
    private SmsStatisticsService smsStatisticsService;
    @Autowired
    private WechatOrdersService wechatOrdersService;

    // 每天凌晨一点执行
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.info("定时统计短信营销数据......");
        XxlJobLogger.log("定时统计短信营销数据......");
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
        return ReturnT.SUCCESS;
    }
}
