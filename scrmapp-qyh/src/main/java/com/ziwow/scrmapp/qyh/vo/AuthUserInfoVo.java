/**   
 * @Title: AuthUserInfo.java
 * @Package com.ziwow.qyhapp.vo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-3 下午4:27:14
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.vo;

/**
 * @ClassName: AuthUserInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:27:14
 * 
 */
public class AuthUserInfoVo {

	private String userid;
	private String mobile;
	private String email;
	private String name;
	private String avatar;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
