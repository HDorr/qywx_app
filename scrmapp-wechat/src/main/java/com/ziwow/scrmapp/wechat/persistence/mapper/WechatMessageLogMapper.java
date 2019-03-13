package com.ziwow.scrmapp.wechat.persistence.mapper;

import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import org.apache.ibatis.annotations.Param;


/***
 * 微信消息日志
 */
public interface WechatMessageLogMapper {

  int saveLog(@Param("openId")String openId,@Param("msgType")String msgType,@Param("event")String event,@Param("eventKey")String eventKey);
}