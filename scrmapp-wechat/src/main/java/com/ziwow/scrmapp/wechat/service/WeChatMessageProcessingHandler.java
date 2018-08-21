/**
 * @Title: DefaultMessageProcessingHandlerImpl.java
 * @Package com.ziwow.marketing.weixin.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen
 * @date 2016-8-2 下午3:48:22
 * @version V1.0
 */
package com.ziwow.scrmapp.wechat.service;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.persistence.entity.Channel;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCustomerMsg;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author hogen
 * @ClassName: DefaultMessageProcessingHandlerImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-8-2 下午3:48:22
 */
@Service("weChatMessageProcessingHandler")
public class WeChatMessageProcessingHandler {
    private Logger LOG = LoggerFactory.getLogger(WeChatMessageProcessingHandler.class);
    @Resource
    private WechatFansService wechatFansService;

    @Resource
    private WechatCustomerMsgService customerMsgService;

    @Resource
    private WechatMediaService wechatMediaService;

    @Autowired
    private ChannelService channelService;

    @Value("${miniapp.appid}")
    private String miniappAppid;

    @Value("${mendian.baseUrl}")
    private String mendianBaseUrl;

    @Value("${mine.baseUrl}")
    private String mineBaseUrl;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${open.weixin.component_appid}")
    private String component_appid;


    @Value("${order.list.url}")
    private String orderlisturl;
    /**
     * 业务转发组件
     *
     * @param @param  requestStr
     * @param @param  request
     * @param @param  response
     * @param @throws ServletException
     * @param @throws IOException 设定文件
     * @return void 返回类型
     * @Title: manageMessage
     * @version 1.0
     * @author Hogen.hu
     */
    public void manageMessage(String requestStr, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InMessage inMessage = XmlUtils.xmlToObject(requestStr, InMessage.class);
            LOG.info("微信返回inMessage信息:{}", JSON.toJSONString(inMessage));
            if (null != inMessage) {
                if ("event".equals(inMessage.getMsgType())) {
                    if ("subscribe".equals(inMessage.getEvent())) {
                        // 关注事件
                        LOG.info("关注openId=[{}]", inMessage.getFromUserName());
                        String openId = inMessage.getFromUserName();
                        String wxToken = inMessage.getToUserName();
                        WechatFans fans = wechatFansService.getWechatFansByOpenId(openId);
                        if (null != fans) {
                            if (!StringUtils.isEmpty(inMessage.getEventKey())) {
                                String channelId = inMessage.getEventKey().split("_")[1];
                                Channel channel = channelService.query(Long.parseLong(channelId));
                                if (null != channel && 1 != channel.getIsClose()) {
                                    fans.setChannelId(channelId);
                                }
                            }
                            if(StringUtils.isBlank(fans.getUnionId())) {
                                UserInfo userInfo = wechatFansService.getUserInfoByOpenId(openId);
                                if(null != userInfo) {
                                    fans.setUnionId(userInfo.getUnionid());
                                    fans.setHeadImgUrl(userInfo.getHeadimgurl());
                                    fans.setWfNickName(userInfo.getNickname());
                                    fans.setCity(userInfo.getCity());
                                    fans.setCountry(userInfo.getCountry());
                                    fans.setProvince(userInfo.getProvince());
                                }
                            }
                            fans.setIsCancel(0);
                            wechatFansService.updateWechatFans(fans);
                        } else {
                            WechatFans wechatFans = new WechatFans();
                            if (!StringUtils.isEmpty(inMessage.getEventKey())) {
                                String channelId = inMessage.getEventKey().split("_")[1];
                                Channel channel = channelService.query(Long.parseLong(channelId));
                                if (null != channel && 1 != channel.getIsClose()) {
                                    wechatFans.setChannelId(channelId);
                                }
                            }
                            UserInfo userInfo = wechatFansService.getUserInfoByOpenId(openId);
                            String unionId = StringUtils.EMPTY;
                            if(null != userInfo) {
                                unionId = userInfo.getUnionid();
                                wechatFans.setUnionId(userInfo.getUnionid());
                                wechatFans.setHeadImgUrl(userInfo.getHeadimgurl());
                                wechatFans.setWfNickName(userInfo.getNickname());
                                wechatFans.setCity(userInfo.getCity());
                                wechatFans.setCountry(userInfo.getCountry());
                                wechatFans.setProvince(userInfo.getProvince());
                            }
                            WechatFans wFans = wechatFansService.getFans(unionId);
                            if(null != wFans) {
                                wFans.setOpenId(openId);
                                wFans.setWfToken(wxToken);
                                wFans.setHeadImgUrl(wechatFans.getHeadImgUrl());
                                wFans.setWfNickName(wechatFans.getWfNickName());
                                wFans.setCity(wechatFans.getCity());
                                wFans.setCountry(wechatFans.getCountry());
                                wFans.setProvince(wechatFans.getProvince());
                                wechatFansService.updWechatFansById(wFans);
                            } else {
                                wechatFans.setOpenId(openId);
                                wechatFans.setWfToken(wxToken);
                                wechatFansService.saveWechatFans(wechatFans);
                            }
                        }
                    } else if ("unsubscribe".equals(inMessage.getEvent())) {
                        // 取消关注事件
                        LOG.info("取消关注openId=[{}]", inMessage.getFromUserName());
                        String openId = inMessage.getFromUserName();
                        WechatFans wechatFans = new WechatFans();
                        wechatFans.setOpenId(openId);
                        wechatFans.setIsCancel(1);
                        wechatFansService.updateWechatFans(wechatFans);
                    } else if ("CLICK".equals(inMessage.getEvent())) {
                        // 菜单点击事件
                        LOG.info("菜单点击事件=[" + requestStr + "]");
                    }
                } else if ("text".equals(inMessage.getMsgType())) {
                    WechatCustomerMsg record = new WechatCustomerMsg();
                    record.setOpenid(inMessage.getFromUserName());
                    record.setMessageSource(1);
                    record.setMessage(inMessage.getContent());
                    record.setCreateTime(new Date());
                    record.setMessageType(0);
                    record.setIsRead(0);
                    customerMsgService.insertSelective(record);

                    dealWithText(inMessage,response);
                } else if ("image".equals(inMessage.getMsgType())) {
                    WechatCustomerMsg record = new WechatCustomerMsg();
                    record.setOpenid(inMessage.getFromUserName());
                    record.setMessageSource(1);
                    record.setMessage(wechatMediaService.downLoadMedia(inMessage.getMediaId()));
                    record.setCreateTime(new Date());
                    record.setMessageType(1);
                    record.setIsRead(0);
                    customerMsgService.insertSelective(record);
                }
            }
        } catch (Exception e) {
            System.out.println("requestStr=[" + requestStr + "]");
            e.printStackTrace();
        }
    }

    private void dealWithText(InMessage inMessage, HttpServletResponse response)
        throws IOException {

        String content = inMessage.getContent();
        if (StringUtil.isBlank(content)){
            return;
        }
        StringBuilder msgsb=new StringBuilder();
        switch (content) {
            case "购买":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务，建议您通过官方渠道，选购您需要的产品，谢谢！\n")
                    .append("\n")
                    .append("购买机器，请点击")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=classify'>【全部商品】</a>\n")
                    .append("购机参考，请点击")
                    .append("<a href='")
                    .append(mendianBaseUrl)
                    .append("/crm/qiye/customized/viewCustomer'>【定制我的方案】</a>\n")
                    .append("购买滤芯，请点击" )
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "滤芯":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务，建议您通过官方渠道选购您需要的滤芯，谢谢！\n")
                    .append("\n")
                    .append("未购滤芯：\n")
                    .append("购买之前，请先")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element'>【绑定产品】</a>")
                    .append("\n")
                    .append("购买滤芯，请点击")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
                    .append("\n")
                    .append("\n")
                    .append("已购滤芯：\n")
                    .append("更换滤芯，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约滤芯】</a>\n")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "预约":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务！\n")
                    .append("\n")
                    .append("预约机器安装，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约安装】</a>")
                    .append("\n")
                    .append("预约机器保养，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约维修】</a>")
                    .append("\n")
                    .append("预约机器清洗，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约清洗】</a>")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "安装":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务！\n")
                    .append("\n")
                    .append("机器安装，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约安装】</a>")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "更换":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务！\n")
                    .append("\n")
                    .append("已购滤芯：\n")
                    .append("更换滤芯，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约滤芯】</a>")
                    .append("\n")
                    .append("\n")
                    .append("未购滤芯：\n")
                    .append("购买之前，请先")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element'>【绑定产品】</a>")
                    .append("\n")
                    .append("购买滤芯，请点击")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
                    .append("\n")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "维修":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务！\n")
                    .append("\n")
                    .append("机器维修，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约维修】</a>")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "保养":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务！\n")
                    .append("\n")
                    .append("机器保养，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约清洗】</a>")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
            case "投诉":
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务，非常抱歉给您带来不便了！为了更快处理好您的问题，如需反馈相关的投诉问题您可以直接输入\"人工客服\"，会尽快给您受理的哦~\n");
            }
                break;
            case "人工客服":
            {
                msgsb.append("您好，欢迎您选用人工客服，请输入您要咨询的问题。我们的工作时间是每天8点-20点，您的留言我们会尽快回复，感谢您的耐心等待。您也可以拨打我们的全国统一服务热线：400 111 1222，谢谢。\n");
            }
            break;
            default:
            {
                msgsb.append("Demo：\n")
                    .append("\n")
                    .append("您好，小沁在此为您服务，沁园与你一起，健康每一天！\n")
                    .append("\n")
                    .append("商城购买：\n")
                    .append("购买机器，请点击")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=classify'>【全部商品】</a>")
                    .append("\n")
                    .append("购买滤芯，请点击")
                    .append("<a href='http://www.wx.com' data-miniprogram-appid='")
                    .append(miniappAppid)
                    .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
                    .append("\n")
                    .append("\n")
                    .append("售后服务：\n")
                    .append("机器安装，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约安装】</a>")
                    .append("\n")
                    .append("机器保养，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约清洗】</a>")
                    .append("\n")
                    .append("机器维修，请点击")
                    .append("<a href='")
                    .append(mineBaseUrl)
                    .append("/scrmapp/scrmapp/consumer/product/index'>【预约维修】</a>")
                    .append("\n")
                    .append("预约查询，请点击")
                    .append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
                    .append(appid)
                    .append("&redirect_uri=")
                    .append(URLEncoder.encode(orderlisturl))
                    .append("&response_type=code&scope=snsapi_base&state=wx44af8a387ced6117&component_appid=")
                    .append(component_appid)
                    .append("#wechat_redirect'>【工单查询】</a>")
                    .append("\n")
                    .append("\n")
                    .append("其他咨询，请输入文字\"人工客服\"\n");
            }
                break;
        }

        try (PrintWriter writer = response.getWriter()){
            Long nowtime = System.currentTimeMillis()/1000;
            StringBuilder resxmlsb =new StringBuilder("<xml><ToUserName><![CDATA[")
                .append(inMessage.getFromUserName())
                .append("]]></ToUserName><FromUserName><![CDATA[")
                .append(inMessage.getToUserName())
                .append("]]></FromUserName><CreateTime>")
                .append(nowtime)
                .append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[")
                .append(msgsb)
                .append("]]></Content></xml>");

            writer.write(resxmlsb.toString());
          LOG.info("------------------------输出:" + resxmlsb);
        }
    }

}