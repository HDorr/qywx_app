package com.ziwow.scrmapp.qyh.vo;

import java.util.List;

public class QyhApiUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;				// 成员UserID
	private String name;				// 成员名称
	private List<Integer> department;	// 成员所属部门id列表
	private String position;			// 职位信息
	private String mobile;				// 手机号码
	private Integer gender;				// 性别 0表示未定义，1表示男性，2表示女性
	private String email;				// 邮箱
	private String weixinid;			// 微信号
	private String avatar;				// 头像url
	private int status;					// 关注状态: 1=已关注，2=已冻结，4=未关注
	private int enable; 				//可用状态:1=启用、0=禁用

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getDepartment() {
		return department;
	}
	public void setDepartment(List<Integer> department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}