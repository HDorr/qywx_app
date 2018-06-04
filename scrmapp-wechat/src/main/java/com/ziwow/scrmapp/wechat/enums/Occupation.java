package com.ziwow.scrmapp.wechat.enums;

/**
 * 个人资料中的教育程度
 * 
 * @包名 com.ziwow.scrmapp.wechat.enums
 * @文件名 Occupation.java
 * @作者 john.chen
 * @创建日期 2017-3-7
 * @版本 V 1.0
 */
public enum Occupation {
	MANAGEMENT(1, "管理人员"), 
	TECHNICIAN(2, "技术人员"), 
	SALESMAN(3, "销售人员"), 
	PRIVATEOWNERS(4, "私营业主"), 
	PROFESSIONAL(5, "专业人员(医生、律师、工程师等)"), 
	STUDENT(6, "学生"), 
	RETIRE(7, "退休"), 
	OTHER(8, "其他");

	private final int code;
	private final String name;

	Occupation(int code, String name) {
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
		for (Occupation occupation : Occupation.values()) {
			if (occupation.getCode() == code) {
				return occupation.getName();
			}
		}
		return null;
	}
}
