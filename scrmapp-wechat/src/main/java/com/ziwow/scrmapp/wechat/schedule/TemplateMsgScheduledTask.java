package com.ziwow.scrmapp.wechat.schedule;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.vo.WechatOrderMsgVo;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.OrderUtils;
import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Component
public class TemplateMsgScheduledTask {
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
    @Autowired
    private FilterChangeRemindService filterChangeRemindService;
    @Autowired
    private WechatOrdersService wechatOrdersService;
    @Autowired
    private MobileService mobileService;
    @Value("${dispatch.mobile}")
    private String dispatchMobile;
    @Value("${task.flag}")
    private String flag;

    // 每天10点执行
    @Scheduled(cron = "0 0 10 * * ?")
    public void filterChangeReminderMsg() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("模板消息提醒定时任务开始......");
        long begin = System.currentTimeMillis();
        // 2018-04-10 关闭滤芯短信提醒需求
       /* try {
            List<FilterChangeRemindMsgVo> list = filterChangeRemindService.getFilterChangeReminds();
            for (FilterChangeRemindMsgVo filterChangeRemindMsgVo : list) {
                logger.info("滤芯更换提醒:{}", JSON.toJSONString(filterChangeRemindMsgVo));
                int type = filterChangeRemindMsgVo.getType();
                String productName = filterChangeRemindMsgVo.getProductName();
                String productSpec = filterChangeRemindMsgVo.getProductSpec();
                String filterName = filterChangeRemindMsgVo.getFilterName();
                String mobilePhone = filterChangeRemindMsgVo.getMobilePhone();
                if (type == 1) {
                    String msgContent = "亲爱的用户，您的" + productName + "产品" + productSpec + "型号的" + filterName
                            + "滤芯还有1个月到期。届时您可进入“沁园”官方微信预约更换，如已更换，请忽略该消息。";
                    mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
                    filterChangeRemindService.sendFilterBeforeExpireReminderTemplateMsg(filterChangeRemindMsgVo);
                } else if (type == 2) {
                    String msgContent = "亲爱的用户，您的" + productName + "产品" + productSpec + "型号的" + filterName
                            + "滤芯已在1个月前到期。您可进入“沁园”官方微信预约更换，如已更换，请忽略该消息。";
                    mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
                    filterChangeRemindService.sendFilterAfterExpireReminderTemplateMsg(filterChangeRemindMsgVo);
                } else if (type == 3) {
                    String msgContent = "亲爱的用户，您的" + productName + "产品" + productSpec + "型号的" + filterName
                            + "滤芯还有3天到期。您可进入“沁园”官方微信预约更换，如已更换，请忽略该消息。";
                    mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
                    filterChangeRemindService.sendFilterChangeReminderTemplateMsg(filterChangeRemindMsgVo);
                }
            }
        } catch (Exception e) {
            logger.error("滤芯更换提醒通知定时发送模板消息和短信通知失败:", e);
        }*/
        // 售后服务提醒通知
        try {
            List<WechatOrderMsgVo> orderMsgLst = wechatOrdersService.getWechatOrderMsgVo(dispatchMobile);
            for (WechatOrderMsgVo wechatOrderMsgVo : orderMsgLst) {
                logger.info("售后服务提醒:{}", JSON.toJSONString(wechatOrderMsgVo));
                String orderTime = wechatOrderMsgVo.getOrderTime();
                String serverType = OrderUtils.getServiceTypeName(wechatOrderMsgVo.getOrderType());
                String mobilePhone = wechatOrderMsgVo.getMobilePhone();
                String msgContent = "亲爱的用户，您预约了" + serverType + "服务。工程师上门服务时间：" + orderTime + "。请保持电话畅通，届时工程师将与您联系。";
                //短信开口关闭 2019年06月19日
                //mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
                wechatOrdersService.sendWechatOrderTemplateMsg(wechatOrderMsgVo);
            }
        } catch (Exception e) {
            logger.error("售后服务提醒通知定时发送模板消息和短信通知失败:", e);
        }
        long end = System.currentTimeMillis();
        logger.info("模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }

    @Scheduled(cron = "0 0 12 24 7 ? ")
    public void notifyForFansToRegister() {
        long begin = System.currentTimeMillis();
        Integer totalCount = wechatFansService.loadWechatFansAndNotRegisterCount();
        logger.info("粉丝注册提醒通知模板开始......count:{}",totalCount);
        int number = (totalCount / 100) + 1;
        for (int i = 0; i < number; i++) {
           List<WechatFans> fans = wechatFansService.loadWechatFansAndNotRegisterByPage(i * 100, 100);
            for (WechatFans fan : fans) {
                try{
                   String[] params = {fan.getWfNickName(),"暂未注册，期待您的加入~"};
                    wechatTemplateService.sendTemplate(fan.getOpenId(),"pages/pre_register?fromWechatService=1",Arrays.asList(params),"fansAdviceTemplate",true,StringUtils.EMPTY);
                    logger.info("发送通知成功,user:{},{}",fan.getOpenId(),fan.getWfNickName());
                } catch (Exception e) {
                    logger.error("发送活动通知失败", e);
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.info("提醒未注册粉丝注册模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }
    @Scheduled(cron = "0 0 11 24 7 ? ")
    public void notifyForFansToRegisterTest() {
        long begin = System.currentTimeMillis();
        logger.info("粉丝注册提醒通知模板开始......");
        String[] testUser = {"18358733695", "13818072004", "13661632837", "13816454513","13545694989"};
        for (String s : testUser) {
            try {
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(s);
                if (user != null) {
                    WechatFans fan = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params = {fan.getWfNickName(), "暂未注册，期待您的加入～"};
                    wechatTemplateService
                        .sendTemplate(fan.getOpenId(), "pages/pre_register?fromWechatService=1",
                            Arrays.asList(params), "fansAdviceTemplate", true, StringUtils.EMPTY);
                    logger.info("发送通知成功,user:{},{}", fan.getOpenId(), fan.getWfNickName());
                }else {
                    logger.info("用户不存在,user:{}",s);
                }
            }catch (Exception e) {
                logger.error("发送活动通知失败", e);
            }
            long end = System.currentTimeMillis();
            logger.info("提醒未注册粉丝注册模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
        }
    }
    /***
     * MGM通知内测
     */
    @Scheduled(cron = "0 0 11 11 7 ? ")
    public void MGMTest() {
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch1();
        logger.info("MGM-1-获取通知用户，数量:{}",fansList.size());
         fansList = wechatFansService.loadTempWechatFansBatch2();
        for (TempWechatFans tempWechatFans : fansList) {
            logger.info(tempWechatFans.toString());
        }
        logger.info("MGM-2-获取通知用户，数量:{}",fansList.size());
         fansList = wechatFansService.loadTempWechatFansBatch3();
        for (TempWechatFans tempWechatFans : fansList) {
            logger.info(tempWechatFans.toString());
        }
        logger.info("MGM-3-获取通知用户，数量:{}",fansList.size());
        for (TempWechatFans tempWechatFans : fansList) {
            logger.info(tempWechatFans.toString());
        }
        String[] testUser={"18358733695","13818072004","13661632837","13816454513"};
        for (String s : testUser) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(s);
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"2019.07.01","一年两次沁园净水器清洗服务","2019.07.11-2020.07.11"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification1Template",false,StringUtils.EMPTY);
                    logger.info("MGM-1-发送通知成功,user:{}",s);
                }else{
                    logger.info("MGM-1-用户不存在,user:{}",s);
                }
            }catch (Exception e){
                logger.error("MGM-1发送通知失败:", e);
            }
        }
    }


}