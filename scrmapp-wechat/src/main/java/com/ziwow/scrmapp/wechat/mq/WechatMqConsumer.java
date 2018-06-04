package com.ziwow.scrmapp.wechat.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.bean.vo.WechatUserMsgVo;
import com.ziwow.scrmapp.common.service.MobileService;
import com.ziwow.scrmapp.tools.queue.QueueListener;
import com.ziwow.scrmapp.wechat.service.WechatOrdersService;

public class WechatMqConsumer implements QueueListener<WechatUserMsgVo> {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMqConsumer.class);
	@Autowired
	private WechatOrdersService wechatOrdersService;
	@Autowired
	private MobileService mobileService;
	@Override
	public void onMessage(WechatUserMsgVo message) {
		LOGGER.info("用户侧消费者listener获取的值为:" + JSON.toJSONString(message));
		if (null != message) {
			String ordersCode = message.getOrdersCode();
			String userId = message.getUserId();
			String orderTime = message.getOrderTime();
			int type = message.getType();
			if(type == 1) {
				// 发送模板消息通知
				String orderType = message.getOrderType();
				String qyhUserName = message.getQyhUserName();
				String qyhUserPhone = message.getQyhUserPhone();
				wechatOrdersService.changeAppointmentTemplate(userId, orderType, ordersCode, orderTime, qyhUserName, qyhUserPhone);
			} else if(type == 2) {
				// 发送模板消息通知
				wechatOrdersService.sendOrderFinishTemplateMsg(ordersCode, userId, orderTime);
			}
		}
	}
}