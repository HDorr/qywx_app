package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author songkaiqi
 * @since 2019/07/22/下午5:11
 */
@Component
@JobHandler("RegisterActivityReminderMsgTask")
public class RegisterActivityReminderMsgTask extends IJobHandler {


    private final Logger logger = LoggerFactory.getLogger(TemplateMsgScheduledTask.class);
    private WechatTemplateService wechatTemplateService;
    private WechatFansService wechatFansService;
    @Autowired
    public void setWechatFansService(WechatFansService wechatFansService) {
        this.wechatFansService = wechatFansService;
    }

    @Autowired
    public void setWechatTemplateService(WechatTemplateService wechatTemplateService) {
        this.wechatTemplateService = wechatTemplateService;
    }
    private WechatUserService wechatUserService;
    @Autowired
    public void setWechatUserService(WechatUserService wechatUserService) {
        this.wechatUserService = wechatUserService;
    }


    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.info("H5活动模板消息提醒定时任务开始......");
        XxlJobLogger.log("H5活动模板消息提醒定时任务开始......");
        long begin = System.currentTimeMillis();
        List<WechatUser> activityUser = wechatUserService.getUserByRegisterSrc(1);
        logger.info("获取H5注册的用户，数量:{}",activityUser.size());
        XxlJobLogger.log("获取H5注册的用户，数量:{}",activityUser.size());
        for (WechatUser wechatUser : activityUser) {
            try{
                WechatFans fans = wechatFansService.getWechatFansById(wechatUser.getWfId());
                String[] params={"2019年5月18日","沁园净水器保养礼包","截止2019年6月18日"};
                wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),"awardNotifyTemplate");
                logger.info("发送通知成功,user:{}",wechatUser.getMobilePhone());
                XxlJobLogger.log("发送通知成功,user:{}",wechatUser.getMobilePhone());
            }catch (Exception e){
                logger.error("定向人群发送通知失败:", e);
                XxlJobLogger.log("定向人群发送通知失败:", e);
                return ReturnT.FAIL;
            }

        }
        long end = System.currentTimeMillis();
        logger.info("H5活动模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
        XxlJobLogger.log("H5活动模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
        return ReturnT.SUCCESS;
    }

}
