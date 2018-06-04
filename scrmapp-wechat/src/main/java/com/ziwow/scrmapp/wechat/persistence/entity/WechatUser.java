package com.ziwow.scrmapp.wechat.persistence.entity;

import java.util.Date;


public class WechatUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String userId;
    private String userName;
    private String nickName;
    private String password;
    private String seed;
    private Integer gender;			// 1:男 2:女
    private String mobilePhone;
    private Integer maritalStatus;	// 1:是  2:否
    private String email;
    private Date birthday;
    private Integer educationLevel;
    private Integer occupation;
    private Integer monthlyIncome;
    private String communicateChannel;
    private String interestedType;
    private String provinceId;
    private String cityId;
    private String areaId;
    private Integer status;
    private Long wfId;
    private Date createDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getWfId() {
		return wfId;
	}
	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public Integer getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(Integer monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getCommunicateChannel() {
		return communicateChannel;
	}
	public void setCommunicateChannel(String communicateChannel) {
		this.communicateChannel = communicateChannel;
	}
	public String getInterestedType() {
		return interestedType;
	}
	public void setInterestedType(String interestedType) {
		this.interestedType = interestedType;
	}
}