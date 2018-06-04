package com.ziwow.scrmapp.wechat.enums;

/**
 * 个人资料中的月收入
* @包名   com.ziwow.scrmapp.wechat.enums   
* @文件名 MonthlyIncome.java   
* @作者   john.chen   
* @创建日期 2017-3-7   
* @版本 V 1.0
 */
public enum MonthlyIncome {
	TWOTHOUSANDBELOW(1, "2000元以下"), 
	FOURTHOUSANDBELOW(2, "2000-3999元"), 
	SIXTHOUSANDBELOW(3, "4000-5999元"), 
	EIGHTTHOUSANDBELOW(4, "6000-7999元"), 
	TENTHOUSANDBELOW(5, "8000-9999元"), 
	TWENTYTHOUSANDBELOW(6, "10000-20000元"), 
	TWENTYTHOUSANDABOVE(7, "20001元以上");

	private final int code;
	private final String name;

	MonthlyIncome(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static String getNameByCode(int code) {
		for (MonthlyIncome mi : MonthlyIncome.values()) {
			if (mi.getCode() == code) {
				return mi.getName();
			}
		}
		return null;
	}
}
