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
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatCustomerMsg;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}