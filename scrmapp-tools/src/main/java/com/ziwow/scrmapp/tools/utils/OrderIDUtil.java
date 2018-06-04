package com.ziwow.scrmapp.tools.utils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户生成订单号，年月日时分秒，加机器号，流水号组成
 * 
 * @author Administrator
 * 
 */
public class OrderIDUtil {
	/**
	 * 获取19位订单号，格式：年月日时分秒(14位)+ 机器号(2位) + 流水号(3位)
	 * @return	正式订单号
	 */
	public static synchronized String getOrderIdValue(){
		int hashCodeV=UUID.randomUUID().toString().hashCode();
		if(hashCodeV<0){
			hashCodeV = -hashCodeV;
		}
		return DateUtil.DateToString(new Date(), "yyMMddHHmmss")+String.format("%07d", hashCodeV);
	}
}
