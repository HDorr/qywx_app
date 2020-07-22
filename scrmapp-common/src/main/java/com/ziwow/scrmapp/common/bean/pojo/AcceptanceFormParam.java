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

	/**
	 * 渠道区分 常规传4 ，原单原回传21
	 */
	private Integer from_type;

	//服务商名称
	private String fix_org_name;

	//服务子类型1
	private String kind_name;

	//服务子类型2
	private String kind_name2;

	/**
	 * 服务类型id1
	 */
	private String kind_id;

	/**
	 * 服务类型id2
	 */
	private String kind_id2;

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getKind_name2() {
		return kind_name2;
	}

	public void setKind_name2(String kind_name2) {
		this.kind_name2 = kind_name2;
	}

	public Integer getFrom_type() {
		return from_type;
	}

	public void setFrom_type(Integer from_type) {
		this.from_type = from_type;
	}

	public String getFix_org_name() {
		return fix_org_name;
	}

	public void setFix_org_name(String fix_org_name) {
		this.fix_org_name = fix_org_name;
	}

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

  public String getKind_id() {
    return kind_id;
  }

  public void setKind_id(String kind_id) {
    this.kind_id = kind_id;
  }

  public String getKind_id2() {
    return kind_id2;
  }

  public void setKind_id2(String kind_id2) {
    this.kind_id2 = kind_id2;
  }
}
