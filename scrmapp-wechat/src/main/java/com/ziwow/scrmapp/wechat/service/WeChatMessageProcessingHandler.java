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
import com.thoughtworks.xstream.XStream;
import com.ziwow.scrmapp.common.persistence.entity.Channel;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.tools.oss.CallCenterOssUtil;
import com.ziwow.scrmapp.tools.utils.CommonUtil;
import com.ziwow.scrmapp.tools.utils.Sha1Util;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XStreamAdaptor;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.tools.weixin.decode.AesException;
import com.ziwow.scrmapp.tools.weixin.decode.WXBizMsgCrypt;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.*;
import com.ziwow.scrmapp.wechat.vo.Articles;
import com.ziwow.scrmapp.wechat.vo.TextOutMessage;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.callCenter.CallCenterMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    @Value("${app.v}")
    private String appVersion;

    @Value("${check.water.time}")
    private String checkWaterTime;

    @Value("${miniapp.appid}")
    private String miniappAppid;

    @Value("${mendian.baseUrl}")
    private String mendianBaseUrl;

    @Value("${mine.baseUrl}")
    private String mineBaseUrl;

    @Value("${mine.basePcUrl}")
    private String mineBasePcUrl;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${open.weixin.component_appid}")
    private String component_appid;


    @Value("${order.list.url}")
    private String orderlisturl;

    @Value("${template.msg.url}")
    private String msgUrl;

    @Value("${register.url}")
    private String registerUrl;

    @Value("${callCenter.url}")
    private String callCenterUrl;

    @Value("${callCenter.limit.time}")
    private Boolean limitCallCenterWorkingTime;

    @Value("${callCenter.tenantId}")
    private String callCenterTenantId;

    @Autowired
    private OpenWeixinService openWeixinService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private RedisService redisService;

    private WechatMessageLogService wechatMessageLogService;
    @Autowired
    public void setWechatMessageLogService(WechatMessageLogService wechatMessageLogService) {
        this.wechatMessageLogService = wechatMessageLogService;
    }
    @Autowired
    private WechatRegisterService wechatRegisterService;

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
            wechatMessageLogService.saveLog(inMessage);
            if (null != inMessage) {
                if ("event".equals(inMessage.getMsgType())) {
                    if ("subscribe".equals(inMessage.getEvent())) {
                        // 关注事件
                        LOG.info("关注openId=[{}]", inMessage.getFromUserName());
                        String openId = inMessage.getFromUserName();
                        String wxToken = inMessage.getToUserName();
                        //通过openid在fans表中查询
                        WechatFans fans = wechatFansService.getWechatFansByOpenId(openId);
                        //若不为空
                        if (null != fans) {
                            //判断是否携带渠道值
                            if (!StringUtils.isEmpty(inMessage.getEventKey())) {
                                String channelId = inMessage.getEventKey().split("_")[1];
                                Channel channel = channelService.query(Long.parseLong(channelId));
                                if (null != channel && 1 != channel.getIsClose()) {
                                    fans.setChannelId(channelId);
                                }
                            }
                            if(StringUtils.isBlank(fans.getUnionId())) {
                                //通过调用微信接口获取用户详情
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
                            //修改fans表中相关数据
                            wechatFansService.updateWechatFans(fans);
                        } else {
                            //为空则初始化对象
                            WechatFans wechatFans = new WechatFans();
                            //查看是否携带渠道值,若携带则注入上创建的对象
                            if (!StringUtils.isEmpty(inMessage.getEventKey())) {
                                String channelId = inMessage.getEventKey().split("_")[1];
                                Channel channel = channelService.query(Long.parseLong(channelId));
                                if (null != channel && 1 != channel.getIsClose()) {
                                    wechatFans.setChannelId(channelId);
                                }
                            }
                            //调用微信接口获取用户详情
                            UserInfo userInfo = wechatFansService.getUserInfoByOpenId(openId);
                            String unionId = StringUtils.EMPTY;
                            //插入用户信息
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
                        dealWithSubscribe(inMessage,response);
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
                    boolean isPushToCallCenter = dealWithText(inMessage, response);//关键词回复

                    //从redis判断聊天状态
                    boolean isInChat=checkChatStatus(inMessage.getFromUserName());
                    if (!isPushToCallCenter && isInChat){//没有推送过并且处于会话中
                      pushMessageToCallCenter(inMessage);//推送消息到呼叫中心
                    }

                  WechatCustomerMsg record = new WechatCustomerMsg();
                  record.setOpenid(inMessage.getFromUserName());
                  record.setMessageSource(1);
                  record.setMessage(inMessage.getContent());
                  record.setCreateTime(new Date());
                  record.setMessageType(0);
                  record.setIsRead(isInChat?0:1);
                  record.setIsHide(isInChat?0:1);
                  customerMsgService.insertSelective(record);
                } else if ("image".equals(inMessage.getMsgType())) {
                    response.getWriter().write("");
                    response.getWriter().close();
                    boolean isInChat=checkChatStatus(inMessage.getFromUserName());


                    if (isInChat){
                        pushMediaMessageToCallCenter(inMessage);
                    }else {
                        WechatCustomerMsg record = new WechatCustomerMsg();
                        record.setOpenid(inMessage.getFromUserName());
                        record.setMessageSource(1);
                        String mediaUrl = wechatMediaService.downLoadMedia(inMessage.getMediaId());
//                        String fileName = wechatMediaService.downLoadMediaForCallCenter(inMessage.getMediaId());
//                        String mediaUrl=DOWNLOAD_URL+"?filename=" +fileName;
                        record.setMessage(mediaUrl);
                        record.setCreateTime(new Date());
                        record.setMessageType(1);
                        record.setIsRead(isInChat?0:1);
                        record.setIsHide(isInChat?0:1);
                        customerMsgService.insertSelective(record);
                    }
                }
                else if ("voice".equals(inMessage.getMsgType())) {
                    response.getWriter().write("");
                    response.getWriter().close();
                    pushMediaMessageToCallCenter(inMessage);
                }else if ("video".equals(inMessage.getMsgType())) {
                    response.getWriter().write("");
                    response.getWriter().close();
                    pushMediaMessageToCallCenter(inMessage);
                }
            }
        } catch (Exception e) {
            System.out.println("requestStr=[" + requestStr + "]");
            e.printStackTrace();
        }
    }

    /**
     * 异步推送到呼叫中心
     * @param inMessage
     */
    @Async
    private void pushMediaMessageToCallCenter(InMessage inMessage) {
        String fileName = wechatMediaService.downLoadMediaForCallCenter(inMessage.getMediaId());
        inMessage.setContent(fileName);
        pushMessageToCallCenter(inMessage);//推送消息到呼叫中心
    }

    /**
     * 推送消息到呼叫中心
     * @param inMessage
     */
    private void pushMessageToCallCenter(InMessage inMessage) {
        CallCenterMessage callCenterMessage=new CallCenterMessage(inMessage);
        callCenterMessage.setTenantId(callCenterTenantId);
        String userOpenId = inMessage.getFromUserName();
        WechatFans wechatFans = wechatFansService.getWechatFans(userOpenId);
        if (wechatFans != null) {
            callCenterMessage.setUserName(wechatFans.getWfNickName());
            WechatUser wechatUser = wechatUserService.getUserByOpenId(userOpenId);
            if (wechatUser != null) {
                callCenterMessage.setPhone(wechatUser.getMobilePhone());
                callCenterMessage.setUserStatus(String.valueOf(wechatUser.getStatus()));
            }
        }
        callCenterMessage.setChatUrl(mineBasePcUrl+"/customerMsg/callCenter/pushMessage");

        XStream xs = XStreamAdaptor.createXstream();
        xs.alias("xml", callCenterMessage.getClass());
        xs.alias("item", Articles.class);
        String xml = xs.toXML(callCenterMessage);

        LOG.info("转发至呼叫中心的数据：[{}],[{}]",callCenterUrl,xml);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(callCenterUrl);
            HttpEntity reqEntity = new StringEntity(xml,"UTF-8");
            httpPost.setEntity(reqEntity);
            httpPost.setHeader("Content-Type", "text/xml");
            final HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, "UTF-8");
            LOG.info("呼叫中心响应：[{}],[{}]",response.getStatusLine().getStatusCode(),content);
        }catch (Exception e){
            LOG.info("消息转发呼叫中心失败：[{}],[{}]",callCenterUrl,xml);
        }

    }

    private boolean checkChatStatus(String openId) {
        Object obj = redisService.get(RedisKeyConstants.getScrmappWechatCustomermsg() + openId);
        if (obj == null) {
            return false;
        }
        return (boolean) obj;
    }

    private void dealWithSubscribe(InMessage inMessage,HttpServletResponse response) {
        StringBuilder msgsb=new StringBuilder();

        if (StringUtils.isNotEmpty(inMessage.getEventKey())) {
            //获取渠道号
            String channelId = inMessage.getEventKey().split("_")[1];
            //通过渠道号获取欢迎语
            String welcomeText = channelService.selectWelcomeTextByChannelId(channelId);

            if (StringUtils.isNotBlank(welcomeText)){
                msgsb.append(welcomeText);
            }
        }else {
            msgsb.append("Hi~欢迎进入沁园水健康守护基地\n")
                .append("\n")
                .append("点击<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/pre_register?fromWechatService=1'>【会员注册】</a>")
                .append("畅享在线会员权益 尊享专属积分福利\n")
                .append("\n")
                .append("点击<a href='http://www.qinyuanmall.com/mobile/product/filterIndex.jhtml' data-miniprogram-appid='")
                .append(miniappAppid)
                .append("' data-miniprogram-path='pages/home'>【要购买•微信商城】</a>\n")
                .append("全场积分抵现享不停，积分多积多优惠\n")
                .append("\n")
                .append("点击<a href='")
                .append(mineBaseUrl)
                .append("/scrmapp/consumer/product/index'>【找售后•一键服务】</a>\n")
                .append("24小时在线预约滤芯、安装、保养、维修等售后服务\n")
                .append("\n")
                .append("2019，沁园和您一起更净一步！");
        }
        replyMessage(inMessage, response, msgsb);
    }

    private void replyMessage (InMessage inMessage, HttpServletResponse response,
        StringBuilder msgsb) {
        try (PrintWriter writer = response.getWriter()){
            TextOutMessage out = new TextOutMessage();
            out.setToUserName(inMessage.getFromUserName());
            out.setFromUserName(inMessage.getToUserName());
            out.setContent(msgsb.toString());
            out.setCreateTime(new Date().getTime());
            XStream xs = XStreamAdaptor.createXstream();
            xs.alias("xml", out.getClass());
            xs.alias("item", Articles.class);
            String xml = xs.toXML(out);
            String encryptXML = getEncryptMessage(xml);
            LOG.info("------------------xml明文:" + xml);
            writer.write(encryptXML);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private enum CallCenterKyWord {
        buy("购买");
        private String text;

        CallCenterKyWord(String text) {
            this.text = text;
        }
    }

    private boolean dealWithText(final InMessage inMessage, HttpServletResponse response) throws ParseException {


        String content = inMessage.getContent();
        if (StringUtil.isBlank(content)){
            return false;
        }
        StringBuilder msgsb=new StringBuilder();

        //链接后面的无效参数是为了避免微信前端点击粘连
        if (content.contains("购买")){
          msgsb.append("您好,小沁在此为您服务,建议您通过官方渠道选购您需要的产品,谢谢！\n")
              .append("\n")
              .append("购买机器,请点击")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=classify'>【全部商品】</a>\n")
              .append("\n")
              .append("购机参考,请点击")
              .append("<a href='")
              .append(mendianBaseUrl)
              .append("/crm/wechat/customized/viewCustomer'>【定制我的方案】</a>\n")
              .append("\n")
              .append("购买滤芯,请点击" )
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
              .append("\n")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("滤芯")){
          msgsb.append("您好,小沁在此为您服务,建议您通过官方渠道选购您需要的滤芯,谢谢！\n")
              .append("\n")
              .append("未购滤芯：\n")
              .append("购买之前,请先")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=bind_product'>【绑定产品】</a>")
              .append("\n")
              .append("\n")
              .append("购买滤芯,请点击")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
              .append("\n")
              .append("\n")
              .append("已购滤芯：\n")
              .append("更换滤芯,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index'>【预约滤芯】</a>\n")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("预约")){
          msgsb.append("您好,小沁在此为您服务！\n")
              .append("\n")
              .append("机器安装,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?1'>【预约安装】</a>")
              .append("\n")
              .append("\n")
              .append("机器维修,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?2'>【预约维修】</a>")
              .append("\n")
              .append("\n")
              .append("机器清洗,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?3'>【预约清洗】</a>")
              .append("\n")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("安装")){
          msgsb.append("您好,小沁在此为您服务！\n")
              .append("\n")
              .append("机器安装,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index'>【预约安装】</a>")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("更换")){
          msgsb.append("您好,小沁在此为您服务！\n")
              .append("\n")
              .append("已购滤芯：\n")
              .append("更换滤芯,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index'>【预约滤芯】</a>")
              .append("\n")
              .append("\n")
              .append("未购滤芯：\n")
              .append("购买之前,请先")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=bind_product'>【绑定产品】</a>")
              .append("\n")
              .append("\n")
              .append("购买滤芯,请点击")
              .append("<a data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'  href='http://www.qinyuan.cn?1'>【购买滤芯】</a>")
              .append("\n")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("维修")){
          msgsb.append("您好,小沁在此为您服务！\n")
              .append("\n")
              .append("机器维修,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index'>【预约维修】</a>")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("保养")){
          msgsb.append("您好,小沁在此为您服务！\n")
              .append("\n")
              .append("机器清洗,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index'>【预约清洗】</a>")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }else if (content.contains("投诉")){
          msgsb.append("您好,非常抱歉给您带来的不便！\n您可以直接输入投诉问题,我们会尽快给您受理的哦\n全国服务热线：400 111 1222\n在线工作时间：8:00AM-20:00PM");
        }else if (content.contains("人工客服")){
            final boolean inWorkTime=CallCenterOssUtil.checkIsInCallCenterWorkingTime(Calendar.getInstance());
            boolean isPushToCallCenter=false;
            if (!limitCallCenterWorkingTime || inWorkTime) {
                msgsb.append("正在为您转接人工客服,请耐心等待！");
                redisService.set(RedisKeyConstants.getScrmappWechatCustomermsg() + inMessage.getFromUserName(), true, 1200L);
                //调用呼叫中心转人工
                LOG.info("调用呼叫中心转人工接口");
                inMessage.setContent("转人工");
                pushMessageToCallCenter(inMessage);//推送消息到呼叫中心
                isPushToCallCenter=true;
            } else {
                msgsb.append("您好，非常抱歉给您带来不便，目前并非客服的工作时间，工作时间为：8:00AM-20:00PM");
            }
            replyMessage(inMessage, response, msgsb);
            return isPushToCallCenter;
        }else if (content.contains("攻略")||content.contains("延保卡")){
            return false;
        }else if (content.equals("appV")){
            msgsb.append("version:"+appVersion);
        } else if("除菌去味一步到位".contains(content)||"除菌去味一喷到位".contains(content)||"卫宝".contains(content)){
            WechatRegister register = new WechatRegister();
            register.setOpenId(inMessage.getFromUserName());
            register.setContent(inMessage.getContent());
            //根据openid查询手机号
            WechatUser wechatUser = wechatUserService
                .getUserByOpenId(inMessage.getFromUserName());
            if(null!=wechatUser){
                register.setPhone(wechatUser.getMobilePhone());
                wechatRegisterService.savePullNewRegisterByEngineer(register);
            }
            return  false;

        } else if (content.contains("水质检测")) {
            //初始化时间
            String fomatData = checkWaterTime;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //解析date类
            Date planDate = sdf.parse(fomatData);
            Date nowDate = new Date();

            //对比时间
            int result = nowDate.compareTo(planDate);
            //若result<0说明未达到10月7日
            if (result < 0) {
                msgsb.append("恭喜您已成功报名！\n")
                        .append("请持续关注我们,\n")
                        .append("实时查看您的状态！\n")
                        .append("后续我们会在公布中奖名单后\n")
                        .append("主动联系您！\n");
            } else {
                //根据openid查询
                WechatUser wechatUser = wechatUserService.getUserByOpenId(inMessage.getFromUserName());
                //初始化判断变量,如果未查询到该用户则直接返回未中奖
                boolean isLucky = false;
                if (wechatUser != null){
                    //查看是否在中奖名单中
                    isLucky = wechatUserService.findUserLuckyByPhone(wechatUser.getMobilePhone());
                }

                if (isLucky) {
                    msgsb.append("恭喜您！\n")
                            .append("您已获得免费检测机会！\n")
                            .append("我们的工作人员将会主动联系您！");
                } else {
                    msgsb.append("很抱歉！\n")
                            .append("您未获得免费检测机会！\n")
                            .append("请持续关注其他福利活动！");
                }
            }
        }else {

            boolean isInChat=checkChatStatus(inMessage.getFromUserName());
            if (isInChat){
                redisService.set(RedisKeyConstants.getScrmappWechatCustomermsg()+inMessage.getFromUserName(),true,1200L);
                return false;
            }
            msgsb.append("您好,小沁在此为您服务,沁园与你一起,健康每一天！\n")
              .append("\n")
              .append("商城购买：\n")
              .append("购买机器,请点击")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=classify'>【全部商品】</a>")
              .append("\n")
              .append("\n")
              .append("购买滤芯,请点击")
              .append("<a href='http://www.qinyuan.cn' data-miniprogram-appid='")
              .append(miniappAppid)
              .append("' data-miniprogram-path='pages/home?goto=buyfilter_element_filter'>【购买滤芯】</a>")
              .append("\n")
              .append("\n")
              .append("售后服务：\n")
              .append("机器安装,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?1'>【预约安装】</a>")
              .append("\n")
              .append("\n")
              .append("机器清洗,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?2'>【预约清洗】</a>")
              .append("\n")
              .append("\n")
              .append("机器维修,请点击")
              .append("<a href='")
              .append(mineBaseUrl)
              .append("/scrmapp/consumer/product/index?3'>【预约维修】</a>")
              .append("\n")
              .append("\n")
              .append("预约查询,请点击")
//              .append("<a href='")
//              .append(orderlisturl)
//              .append("'>【工单查询】</a>")
              .append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
              .append(appid)
              .append("&redirect_uri=")
              .append(URLEncoder.encode(orderlisturl))
              .append("&response_type=code&scope=snsapi_base&component_appid=")
              .append(component_appid)
              .append("#wechat_redirect'>【工单查询】</a>")
              .append("\n")
              .append("\n")
              .append("其他咨询,请输入文字\"人工客服\"\n");
        }

        replyMessage(inMessage, response, msgsb);
        return false;
    }


    private String getEncryptMessage(String replyMsg) {
        OpenWeixin ow = openWeixinService.getOpenWeixin();
        String encodingAesKey = ow.getComponent_key();
        String token = ow.getComponent_token();
        String appid = ow.getComponent_appid();
        String encryptMessage = null;
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appid);
            encryptMessage = pc.encryptMsg(replyMsg, Sha1Util.getTimeStamp(), CommonUtil.CreateNoncestr());
        } catch (AesException e) {
            e.printStackTrace();
        }
        return encryptMessage;
    }

}