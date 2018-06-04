package com.ziwow.scrmapp.wechat.enums;

/**
 * 
* @包名   com.ziwow.scrmapp.api.core.user.enums   
* @文件名 MessageType.java   
* @作者   john.chen   
* @创建日期 2017-2-14   
* @版本 V 1.0
 */
public enum MessageType {
	REGISTER(0, "注册"), MODIFY(1, "修改密码");
	private final int code;
	private final String name;

	MessageType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getNameByCode(int code) {
		for (MessageType gp : MessageType.values()) {
			if (code == gp.getCode()) {
				return gp.getName();
			}
		}
		return null;

	}

	/**
	 * code.
	 * 
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
