package com.ziwow.scrmapp.qyh.enums;

public enum PositionType {
	ENGINEER(1, "服务工程师"), MESSENGER(2, "信息员"), RETURNINGAGENT(3, "回访员"), SETTLEMENTCLERK(4, "结算专员"), WAREHOUSEKEEPER(5, "仓管员");

	private final int code;
	private final String name;

	PositionType(int code, String name) {
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
		for (PositionType pt : PositionType.values()) {
			if (pt.getCode() == code) {
				return pt.getName();
			}
		}
		return null;
	}
}