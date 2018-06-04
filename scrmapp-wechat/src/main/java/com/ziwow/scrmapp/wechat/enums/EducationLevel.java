package com.ziwow.scrmapp.wechat.enums;

/**
 * 个人资料中的教育程度
 * 
 * @包名 com.ziwow.scrmapp.wechat.enums
 * @文件名 EducationLevel.java
 * @作者 john.chen
 * @创建日期 2017-3-7
 * @版本 V 1.0
 */
public enum EducationLevel {
	JUNIORHIGHSCHOOL(1, "初中及以下"), 
	SENIORSCHOOL(2, "高中"), 
	UNIVERSITY(3, "大学"), 
	MASTER(4, "硕士"), 
	DOCTOR(5, "博士及以上");

	private final int code;
	private final String name;

	EducationLevel(int code, String name) {
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
		for (EducationLevel el : EducationLevel.values()) {
			if (el.getCode() == code) {
				return el.getName();
			}
		}
		return null;
	}
}
