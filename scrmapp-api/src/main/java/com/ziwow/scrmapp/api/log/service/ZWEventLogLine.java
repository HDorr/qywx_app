package com.ziwow.scrmapp.api.log.service;

import com.ziwow.scrmapp.common.constants.Constant;

public class ZWEventLogLine implements LogLine {
	private StringBuffer sb;
	
	private String ip;
	private String device;

	public ZWEventLogLine() {
		sb = new StringBuffer();
	}

	public void append(String item) {
		if (sb.length() == 0) {
			sb.append(item);
		} else {
			sb.append(Constant.LOG_SPLIT + item);
		}
	}

	public StringBuffer getMessage() {
		sb.append(ip).append(Constant.LOG_SPLIT);
		sb.append(device);
		return sb;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
}