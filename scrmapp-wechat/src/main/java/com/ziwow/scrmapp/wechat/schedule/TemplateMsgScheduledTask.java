package com.ziwow.scrmapp.wechat.schedule;

import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatTemplateService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ziwow.scrmapp.common.constants.Constant;
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
                mobileService.sendContentByEmay(mobilePhone, msgContent, Constant.CUSTOMER);
                wechatOrdersService.sendWechatOrderTemplateMsg(wechatOrderMsgVo);
            }
        } catch (Exception e) {
            logger.error("售后服务提醒通知定时发送模板消息和短信通知失败:", e);
        }
        long end = System.currentTimeMillis();
        logger.info("模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }


    /***
     * 活动来源用户发送公众号通知-第一批
     */
    @Scheduled(cron = "0 0 10 24 5 ? ")
    public void registerActivityReminderMsgType1() {
        if (!flag.equals("0")) {
            return;
        }
        logger.info("H5活动模板消息提醒定时任务开始......");
        long begin = System.currentTimeMillis();
        List<TempWechatFans> fansList = wechatFansService.loadTempWechatFansBatch1();
        logger.info("获取通知用户，数量:{}",fansList.size());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String time = sdf.format(d);
        for (TempWechatFans fans : fansList) {
          try{
              String[] params={time,"沁园净水器保养礼包","截止2019年6月18日"};
              wechatTemplateService.sendTemplate(fans.getOpenId(),"", Arrays.asList(params),"awardNotifyTemplate",
                  false);
              logger.info("发送通知成功,user:{}",fans.getMobile());
          }catch (Exception e){
              logger.error("定向人群发送通知失败:", e);
          }

        }
        long end = System.currentTimeMillis();
        logger.info("H5活动模板消息提醒定时任务结束，共耗时：[" + (end - begin) / 1000 + "]秒");
    }



    /***
     * 通知模板测试
     */
    @Scheduled(cron = "0 48 23 4 6 ? ")
    public void registerActivityReminderMsgTest() {
        /*if (!flag.equals("0")) {
            return;
        }*/
/*        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final String time = sdf.format(d);*/

        logger.info("父亲节模板测试......");
        String[] params={"未领取","2019/5/30-2019/6/30"};
        wechatTemplateService.sendTemplate("obJNHxFbyU1AjuOdomU2QsfZuTPI","pages/fathers_day?srcType=WE_CHAT_TWEET", Arrays.asList(params),"fatherDayNotifyTemplate",
            true);
    }

}