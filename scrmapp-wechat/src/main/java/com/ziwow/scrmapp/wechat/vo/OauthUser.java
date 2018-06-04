/**
 * Project Name:api-service
 * File Name:OauthUser.java
 * Package Name:com.ziwow.service.scrm.weixin.bean
 * Date:2014-10-18下午3:51:52
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.vo;

import java.io.Serializable;

/**
 * ClassName: OauthUser <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-18 下午3:51:52 <br/>
 * 
 * @author Daniel.Wang
 * @version
 * @since JDK 1.6
 */
public class OauthUser implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String unionid;
	private String[] privilege;

	/**
	 * openid.
	 * 
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * openid.
	 * 
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * nickname.
	 * 
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * nickname.
	 * 
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * sex.
	 * 
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * sex.
	 * 
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * province.
	 * 
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * province.
	 * 
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * city.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * city.
	 * 
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * country.
	 * 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * country.
	 * 
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * headimgurl.
	 * 
	 * @return the headimgurl
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * headimgurl.
	 * 
	 * @param headimgurl
	 *            the headimgurl to set
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/**
	 * privilege.
	 * 
	 * @return the privilege
	 */
	public String[] getPrivilege() {
		return privilege;
	}

	/**
	 * privilege.
	 * 
	 * @param privilege
	 *            the privilege to set
	 */
	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
