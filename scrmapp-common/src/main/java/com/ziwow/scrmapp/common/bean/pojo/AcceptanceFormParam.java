package com.ziwow.scrmapp.common.bean.pojo;

import java.util.List;

public class AcceptanceFormParam implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 产品相关
	List<AcceptanceProductParam> products;
	// 受理单相关
	private String enduser_name;		//用户名称 
	private String mobile;				//电话
	private String tel;					//电话2
	private String enduser_address;		//安装详细地址
	private String province_name;		//省 
	private String city_name;			//市
	private String county_name;			//区县
	private int appeal_kind_id;			//受理类型 1.安装/2.维修/3.保养
	private String appeal_content;		//受理内容
	private String service_time;		//预约时间
	private String is_wxtd;

	public List<AcceptanceProductParam> getProducts() {
		return products;
	}

	public void setProducts(List<AcceptanceProductParam> products) {
		this.products = products;
	}

	public String getEnduser_name() {
		return enduser_name;
	}

	public void setEnduser_name(String enduser_name) {
		this.enduser_name = enduser_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEnduser_address() {
		return enduser_address;
	}

	public void setEnduser_address(String enduser_address) {
		this.enduser_address = enduser_address;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCounty_name() {
		return county_name;
	}

	public void setCounty_name(String county_name) {
		this.county_name = county_name;
	}

	public int getAppeal_kind_id() {
		return appeal_kind_id;
	}

	public void setAppeal_kind_id(int appeal_kind_id) {
		this.appeal_kind_id = appeal_kind_id;
	}

	public String getAppeal_content() {
		return appeal_content;
	}

	public void setAppeal_content(String appeal_content) {
		this.appeal_content = appeal_content;
	}

	public String getService_time() {
		return service_time;
	}

	public void setService_time(String service_time) {
		this.service_time = service_time;
	}

	public String getIs_wxtd() {
		return is_wxtd;
	}

	public void setIs_wxtd(String is_wxtd) {
		this.is_wxtd = is_wxtd;
	}
}
