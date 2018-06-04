package com.ziwow.scrmapp.wechat.persistence.entity;

import java.io.Serializable;

public class WechatArea implements Serializable{
	private static final long serialVersionUID = 8651160549097808590L;
	private String areaId;
	private String areaName;
	private String cityId;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
}