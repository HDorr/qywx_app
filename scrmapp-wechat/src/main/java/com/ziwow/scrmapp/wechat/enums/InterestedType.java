package com.ziwow.scrmapp.wechat.enums;

/**
 * 个人资料中的感兴趣的信息类型
 * 
 * @包名 com.ziwow.scrmapp.wechat.enums
 * @文件名 InterestedType.java
 * @作者 john.chen
 * @创建日期 2017-3-7
 * @版本 V 1.0
 */
public enum InterestedType {
	RENOVATION(1, "装修"), 
	HOME(2, "居家"), 
	BEAUTYCARE(3, "美容护肤"), 
	PARENTING(4, "育儿"), 
	HEALTHY(5, "健康"), 
	FOOD(6, "美食"), 
	EMOTION(7, "情感"), 
	MOVIES(8, "影视"), 
	TREND(9, "潮流"), 
	INFORMATION(10, "资讯");

	private final int code;
	private final String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	InterestedType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getNameByCode(int code) {
		for (InterestedType it : InterestedType.values()) {
			if (code == it.getCode()) {
				return it.getName();
			}
		}
		return null;
	}
}