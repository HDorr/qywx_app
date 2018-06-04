package com.ziwow.scrmapp.wechat.vo;


import java.util.List;

/**
 * 仅显示修改的个人中心信息
 * Created by xiaohei on 2017/3/8
 */
public class WechatUserVo {
    private String userName;
    private String nickName;
    private Integer maritalStatus;
    private Integer gender;
    private String mobilePhone;
    private String email;
    private String birthday;
    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String areaId;
    private String areaName;
    private String[] communicateChannels;
    private String[] interestedTypes;
    private EnumVo educationLevel;
    private EnumVo occupation;
    private EnumVo monthlyIncome;
    private List<EnumVo> communicateChannelList;
    private List<EnumVo> interestedTypeList;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

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

    public String[] getCommunicateChannels() {
        return communicateChannels;
    }

    public void setCommunicateChannels(String[] communicateChannels) {
        this.communicateChannels = communicateChannels;
    }

    public String[] getInterestedTypes() {
        return interestedTypes;
    }

    public void setInterestedTypes(String[] interestedTypes) {
        this.interestedTypes = interestedTypes;
    }

    public EnumVo getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EnumVo educationLevel) {
        this.educationLevel = educationLevel;
    }

    public EnumVo getOccupation() {
        return occupation;
    }

    public void setOccupation(EnumVo occupation) {
        this.occupation = occupation;
    }

    public EnumVo getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(EnumVo monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public List<EnumVo> getCommunicateChannelList() {
        return communicateChannelList;
    }

    public void setCommunicateChannelList(List<EnumVo> communicateChannelList) {
        this.communicateChannelList = communicateChannelList;
    }


    public List<EnumVo> getInterestedTypeList() {
        return interestedTypeList;
    }

    public void setInterestedTypeList(List<EnumVo> interestedTypeList) {
        this.interestedTypeList = interestedTypeList;
    }
}
