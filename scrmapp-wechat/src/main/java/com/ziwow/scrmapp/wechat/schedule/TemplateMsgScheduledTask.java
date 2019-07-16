package com.ziwow.scrmapp.wechat.schedule;

import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ziwow.scrmapp.common.constants.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.vo.WechatOrderMsgVo;
import com.ziwow.scrmapp.wechat.service.FilterChangeRemindService;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.common.utils.OrderUtils;

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


    /***
     * MGM通知-第一批
     */
    @Scheduled(cron = "0 0 12 11 7 ? ")
    public void MGM1() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("MGM-1-批通知开始......");
        long begin = System.currentTimeMillis();
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch1();
        logger.info("MGM-1-获取通知用户，数量:{}",fansList.size());
        for (TempWechatFans temp : fansList) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(temp.getMobile());
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"2019.07.01","一年两次沁园净水器清洗服务","2019.07.11-2020.07.11"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification1Template",false,StringUtils.EMPTY);
                    logger.info("MGM-1-发送通知成功,user:{}",temp.getMobile());
                }else{
                    logger.info("MGM-1-用户不存在,user:{}",temp.getMobile());
                }
            }catch (Exception e){
                logger.error("MGM-1发送通知失败:", e);
            }

        }
        long end = System.currentTimeMillis();
        logger.info("MGM-1-批通知结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }



    /***
     * MGM通知-第二批
     */
    @Scheduled(cron = "0 0 12 11 7 ? ")
    public void MGM2() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("MGM-2-批通知开始......");
        long begin = System.currentTimeMillis();
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch2();
        logger.info("MGM-2-获取通知用户，数量:{}",fansList.size());
        for (TempWechatFans temp : fansList) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(temp.getMobile());
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"2019.07.11",temp.getCount()+"个联合利华大礼包","2019.07.11-2019.07.13"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification2Template",false,"在本次会员专享-邀请购买活动中，您成功邀请"+temp.getCount()+"名用户购买");
                    logger.info("MGM-2-发送通知成功,user:{}",temp.getMobile());
                }else{
                    logger.info("MGM-2-用户不存在,user:{}",temp.getMobile());
                }
            }catch (Exception e){
                logger.error("MGM-2-发送通知失败:", e);
            }

        }
        long end = System.currentTimeMillis();
        logger.info("MGM-2-批通知结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }



    /***
     * MGM通知-第三批
     */
    @Scheduled(cron = "0 0 12 15 7 ? ")
    public void MGM3() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("MGM-3-批通知开始......");
        long begin = System.currentTimeMillis();
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch3();
        logger.info("MGM-3-获取通知用户，数量:{}",fansList.size());
        for (TempWechatFans temp : fansList) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(temp.getMobile());
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={temp.getProduct(),"2019.07.15-2019.07.31"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification3Template",false,StringUtils.EMPTY);
                    logger.info("MGM-3-发送通知成功,user:{}",temp.getMobile());
                }else{
                    logger.info("MGM-3-用户不存在,user:{}",temp.getMobile());
                }
            }catch (Exception e){
                logger.error("MGM-3-发送通知失败:", e);
            }

        }
        long end = System.currentTimeMillis();
        logger.info("MGM-3-批通知结束，共耗时：[" + (end - begin) / 1000 + "]秒");
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
        for (String s : testUser) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(s);
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"2019.07.11","2"+"个联合利华大礼包","2019.07.11-2019.07.13"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification2Template",false,"在本次会员专享-邀请购买活动中，您成功邀请"+"2"+"名用户购买");
                    logger.info("MGM-2-发送通知成功,user:{}",s);
                }else{
                    logger.info("MGM-2-用户不存在,user:{}",s);
                }
            }catch (Exception e){
                logger.error("MGM-2-发送通知失败:", e);
            }
        }


        for (String s : testUser) {
            try{
                WechatUser user = wechatUserService
                    .getUserByMobilePhone(s);
                if(user!=null){
                    WechatFans fans = wechatFansService.getWechatFansById(user.getWfId());
                    String[] params={"190100-0031*1，天猫精灵*1，2年延保","2019.07.15-2019.07.31"};
                    wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),
                        "MGMNotification3Template",false,StringUtils.EMPTY);
                    logger.info("MGM-3-发送通知成功,user:{}",s);
                }else{
                    logger.info("MGM-3-用户不存在,user:{}",s);
                }
            }catch (Exception e){
                logger.error("MGM-3-发送通知失败:", e);
            }

        }



        for (TempWechatFans temp : fansList) {
        }
    }


}