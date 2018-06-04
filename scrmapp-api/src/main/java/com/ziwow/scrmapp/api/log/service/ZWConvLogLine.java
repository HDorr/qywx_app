package com.ziwow.scrmapp.api.log.service;

import com.ziwow.scrmapp.common.constants.Constant;

public class ZWConvLogLine implements LogLine {
	private StringBuffer sb;

	private String country;
	private String city;

	public ZWConvLogLine() {
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
		sb.append(country).append(Constant.LOG_SPLIT);
		sb.append(city);
		return sb;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}