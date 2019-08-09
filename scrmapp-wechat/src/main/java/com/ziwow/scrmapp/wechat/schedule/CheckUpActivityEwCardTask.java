package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.common.utils.EwCardUtil;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCardActivity;
import com.ziwow.scrmapp.wechat.service.EwCardActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检查搞活动的延保卡是否过期
 *
 * @author songkaiqi
 * @since 2019/08/09/上午10:52
 */
@Component
@JobHandler("checkUpActivityEwCardTask")
public class CheckUpActivityEwCardTask extends IJobHandler {
    private static final Logger logger = LoggerFactory.getLogger(CheckUpActivityEwCardTask.class);

    @Autowired
    private EwCardActivityService ewCardActivityService;


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        //获取已经发送，但是没有领取，未到期的延保卡号
        List<EwCardActivity> ewCardActivities = ewCardActivityService.selectCardByNoReceive();
        for (EwCardActivity ewCardActivity : ewCardActivities) {
            if (EwCardUtil.gtSevenDay(ewCardActivity.getSendTime())) {
                try {
                    ewCardActivityService.resetActivityCard(ewCardActivity.getCardNo());
                } catch (Exception e) {
                    logger.error("重置数据失败，延保卡号:{},错误信息：{}",ewCardActivity.getCardNo(),e);
                    XxlJobLogger.log("重置数据失败，延保卡号:{}",ewCardActivity.getCardNo());
                }
            }
        }
        return ReturnT.SUCCESS;
    }
}
