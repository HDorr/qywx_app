
package com.ziwow.scrmapp.tools.rocketmq;

public class TagsManager {
	public static final String BRANDGOODS_OFFLINE_TAGS = "brandgoods-offline";
	private static final String TAG_ORDER_MSG = "tag_orderMsg";
	public static String getBrandGoodsOfflineTags(){
		return BRANDGOODS_OFFLINE_TAGS;
	}
	
	public static String getOrderMsgTag() {
		return TAG_ORDER_MSG;
	}
}