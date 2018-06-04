
package com.ziwow.scrmapp.tools.rocketmq;

public class TopicManager {
	public static final String REGISTER_API_TOPIC = "api-register-gift";
	public static final String BRANDGOODS_OFFLINE_TOPIC = "brandgoods-offline";
	private static final String TOPIC_ORDER_MSG = "topic_orderMsg";
	
	public static String getRegisterApiTopic(){
		return REGISTER_API_TOPIC;
	}
	
	public static String getBrandGoodsOfflineTopic(){
		return BRANDGOODS_OFFLINE_TOPIC;
	}

	public static String getOrderMsgTopic() {
		return TOPIC_ORDER_MSG;
	}
}