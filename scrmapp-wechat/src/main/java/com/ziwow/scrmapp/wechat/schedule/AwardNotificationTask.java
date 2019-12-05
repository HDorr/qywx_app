package com.ziwow.scrmapp.wechat.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.ziwow.scrmapp.wechat.controller.EwCardController;
import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author jitre
 * @since 2019/08/16/下午5:28
 */
@Component
@JobHandler("awardNotificationTask")
public class AwardNotificationTask extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(EwCardController.class);

    @Autowired
    private WechatFansService wechatFansService;

    @Autowired
    private WechatUserService wechatUserService;

    private WechatTemplateService wechatTemplateService;
    @Autowired
    public void setWechatTemplateService(WechatTemplateService wechatTemplateService) {
        this.wechatTemplateService = wechatTemplateService;
    }

    @Value("${MGMNotificationUrl}")
    private String notificationUrl;

    @Override
    public ReturnT<String> execute(String s) {
        logger.info("中奖通知开始......");
        long begin = System.currentTimeMillis();
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch1();
        logger.info("获取通知用户，数量:{}",fansList.size());
        for (TempWechatFans temp : fansList) {
            try{
                WechatUser user = wechatUserService
                        .getUserByMobilePhone(temp.getMobile());
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"2019年8月19日","免费净水器体检","2019年8月20日"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),notificationUrl, Arrays.asList(params),
                            "MGMNotification1Template",false,StringUtils.EMPTY, "");
                    logger.info("中奖通知-发送通知成功,user:{}",temp.getMobile());
                }else{
                    logger.info("中奖通知-用户不存在,user:{}",temp.getMobile());
                }
            }catch (Exception e){
                logger.error("中奖通知-发送通知失败:", e);
            }

        }
        long end = System.currentTimeMillis();
        logger.info("中奖通知-通知结束，共耗时：[" + (end - begin) / 1000 + "]秒");
        return ReturnT.SUCCESS;
    }
}
