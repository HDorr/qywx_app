package com.ziwow.scrmapp.wechat.enums;

/**
 * 个人资料中的教育程度
 * 
 * @包名 com.ziwow.scrmapp.wechat.enums
 * @文件名 CommunicateChannel.java
 * @作者 john.chen
 * @创建日期 2017-3-7
 * @版本 V 1.0
 */
public enum CommunicateChannel {
	EMAIL(1, "邮件"), 
	MESSAGE(2, "短信"),
	TELEPHONE(3, "电话"),
	WECHAT(4, "微信");
	
	private final int code;
	private final String name;
	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	CommunicateChannel(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getNameByCode(int code) {
		for (CommunicateChannel cc : CommunicateChannel.values()) {
			if (code == cc.getCode()) {
				return cc.getName();
			}
		}
		return null;
	}
}