package com.ziwow.scrmapp.wechat.vo.callCenter;

import com.sun.istack.NotNull;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XStreamCDATA;
import com.ziwow.scrmapp.tools.weixin.enums.MsgTypes;

public class CallCenterMessage {

  /**
   * <xml>
   *  <ToUserName><![CDATA[toUser]]></ToUserName>
   *  <FromUserName><![CDATA[fromUser]]></FromUserName>
   * <TenantId><![CDATA[fromUser]]></TenantId>
   *  <CreateTime>1348831860</CreateTime>
   *  <MsgType><![CDATA[text]]></MsgType>
   * <ChannelType><![CDATA[wechat]]></ChannelType>
   * <EnableVideo><![CDATA[true]]></EnbaleVideo>
   *  <Content><![CDATA[this is a test]]></Content>
   * <MsgId>1234567890123456</MsgId>
   *  <Source><![CDATA[yixin-cs]]></Source>
   *  <BizType><![CDATA[tm_wechat]]></BizType>
   * <Phone><![CDATA[18666668888]]></Phone>
   * <Email><![CDATA[18666668888@creditease.cn]]></ Email>
   * <UserName><![CDATA[宜信测试]]></UserName>
   * <UserStatus><![CDATA[2]]></UserStatus>
   * <ClientLevel><![CDATA[2]]></ClientLevel>
   * <ChatUrl><![CDATA[http://192.168.32.23:8089]]></ChatUrl>
   * <Reserve1><![CDATA[]]></Reserve1>
   * <Reserve2><![CDATA[]]></Reserve2>
   * <Reserve3><![CDATA[]]></Reserve3>
   * <Reserve4><![CDATA[]]></Reserve4>
   * <Reserve5><![CDATA[]]></Reserve5>
   * </xml>
   */
  @NotNull
  @XStreamCDATA
  String ToUserName;//开发者微信号（gh_4b37f6e85952）或WebChat客服统一名称

  @NotNull
  @XStreamCDATA
  String FromUserName;//发送方帐号（微信OpenID或WebChat用户唯一标识）

  @NotNull
  @XStreamCDATA
  String TenantId="qinyuan";//固定传qinyuan

  @NotNull
  Long CreateTime;//创建时间

  @NotNull
  @XStreamCDATA
  String MsgType;//消息类型

  @NotNull
  @XStreamCDATA
  String ChannelType="wechat";//固定传wechat

  @NotNull
  @XStreamCDATA
  String Content;//文本内容或者多媒体文件url

  @NotNull
  @XStreamCDATA
  String MsgId;//消息id,使用微信端传过来的数据

  @NotNull
  @XStreamCDATA
  String Source="serviceAccount";//来源：服务号("serviceAccount")

  @XStreamCDATA
  String BizType="egoo_xa";//业务类型：暂时没有定义

  @XStreamCDATA
  String Phone;//手机号

  @XStreamCDATA
  String Email;//邮箱

  @XStreamCDATA
  String UserName;//用户名

  @XStreamCDATA
  String UserStatus;//用户状态：暂时没有定义

  String ClientLevel;//客户等级：暂时没有定义

  @XStreamCDATA
  String ChatUrl;//网关访问客户端的服务地址

  @XStreamCDATA
  String Reserve1;

  @XStreamCDATA
  String Reserve2;

  @XStreamCDATA
  String Reserve3;

  @XStreamCDATA
  String Reserve4;

  @XStreamCDATA
  String Reserve5;


  @XStreamCDATA
  String SkillGroup="egoo_xa";


  public CallCenterMessage() {
  }

  public CallCenterMessage(InMessage inMessage) {
    this.ToUserName=inMessage.getToUserName();
    this.FromUserName=inMessage.getFromUserName();
    this.CreateTime=inMessage.getCreateTime();
    this.MsgType=inMessage.getMsgType();
    this.Content=inMessage.getContent();
    this.MsgId=inMessage.getMsgID();
  }

  public String getToUserName() {
    return ToUserName;
  }

  public void setToUserName(String toUserName) {
    ToUserName = toUserName;
  }

  public String getFromUserName() {
    return FromUserName;
  }

  public void setFromUserName(String fromUserName) {
    FromUserName = fromUserName;
  }

  public String getTenantId() {
    return TenantId;
  }

  public void setTenantId(String tenantId) {
    TenantId = tenantId;
  }

  public Long getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(Long createTime) {
    CreateTime = createTime;
  }

  public String getMsgType() {
    return MsgType;
  }

  public void setMsgType(String msgType) {
    MsgType = msgType;
  }

  public String getChannelType() {
    return ChannelType;
  }

  public void setChannelType(String channelType) {
    ChannelType = channelType;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public String getMsgId() {
    return MsgId;
  }

  public void setMsgId(String msgId) {
    MsgId = msgId;
  }

  public String getSource() {
    return Source;
  }

  public void setSource(String source) {
    Source = source;
  }

  public String getBizType() {
    return BizType;
  }

  public void setBizType(String bizType) {
    BizType = bizType;
  }

  public String getPhone() {
    return Phone;
  }

  public void setPhone(String phone) {
    Phone = phone;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getUserStatus() {
    return UserStatus;
  }

  public void setUserStatus(String userStatus) {
    UserStatus = userStatus;
  }

  public String getClientLevel() {
    return ClientLevel;
  }

  public void setClientLevel(String clientLevel) {
    ClientLevel = clientLevel;
  }

  public String getChatUrl() {
    return ChatUrl;
  }

  public void setChatUrl(String chatUrl) {
    ChatUrl = chatUrl;
  }

  public String getReserve1() {
    return Reserve1;
  }

  public void setReserve1(String reserve1) {
    Reserve1 = reserve1;
  }

  public String getReserve2() {
    return Reserve2;
  }

  public void setReserve2(String reserve2) {
    Reserve2 = reserve2;
  }

  public String getReserve3() {
    return Reserve3;
  }

  public void setReserve3(String reserve3) {
    Reserve3 = reserve3;
  }

  public String getReserve4() {
    return Reserve4;
  }

  public void setReserve4(String reserve4) {
    Reserve4 = reserve4;
  }

  public String getReserve5() {
    return Reserve5;
  }

  public void setReserve5(String reserve5) {
    Reserve5 = reserve5;
  }

  public String getSkillGroup() {
    return SkillGroup;
  }

  public void setSkillGroup(String skillGroup) {
    SkillGroup = skillGroup;
  }
}
