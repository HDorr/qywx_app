/**   
* @Title: WxUserInfoVo.java
* @Package com.ziwow.scrmapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-12 下午4:32:38
* @version V1.0   
*/
package com.ziwow.scrmapp.vo;

import java.io.Serializable;

/**
 * @ClassName: WxUserInfoVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-12 下午4:32:38
 * 
 */
public class WxUserInfoVo implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String nickName;
	 private String avatarUrl;
	 private String province;
	 private Integer gender;//性别 0：未知、1：男、2：女
	 private String language;
	 private String country;
	 private String city;
	 private String openId;
	 private String unionId;
	 private WaterMark watermark;
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}
	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the gender
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * @return the watermark
	 */
	public WaterMark getWatermark() {
		return watermark;
	}
	/**
	 * @param watermark the watermark to set
	 */
	public void setWatermark(WaterMark watermark) {
		this.watermark = watermark;
	}
	/**
	 * @return the unionId
	 */
	public String getUnionId() {
		return unionId;
	}
	/**
	 * @param unionId the unionId to set
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	 
	 
	 
	 
	 
}
