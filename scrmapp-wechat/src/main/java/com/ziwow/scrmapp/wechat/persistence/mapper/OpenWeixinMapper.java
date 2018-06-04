/**
 * Project Name:ziwow-dubbo-weixin-provider
 * File Name:OpenWeixinMapper.java
 * Package Name:com.ziwow.weixin.provider.persistence
 * Date:2016-4-27上午10:37:36
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.persistence.mapper;

import org.apache.ibatis.annotations.Param;

import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenWeixin;


/**
 * ClassName: OpenWeixinMapper <br/>
 * Function: 微信第三方平台mapper <br/>
 * date: 2016-4-27 上午10:37:36 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public interface OpenWeixinMapper {
	
	public OpenWeixin getOpenWeixin(@Param("component_appid")String component_appid);

	/**
	 * updateOpenWeixin:(更新OpenWeixin信息). <br/>
	 *
	 * @author daniel.wang
	 * @param wx
	 * @since JDK 1.6
	 */
	public void updateOpenWeixin(OpenWeixin wx);
	
	
	public void insertOpenAuthorizationWeixin(OpenAuthorizationWeixin wx);
	
	
	public void  updateOpenAuthorizationWeixin(OpenAuthorizationWeixin wx);
	
	public OpenAuthorizationWeixin getOpenAuthorizationWeixin(@Param("component_appid")String component_appid,@Param("authorizer_appid")String authorizer_appid);
	/**
	 *
	 * @author daniel.wang
	 * @param appid
	 * @return
	 * @since JDK 1.6
	 */
	public OpenAuthorizationWeixin getOpenAuthorizationWeixinByAppid(@Param("appid")
			String appid);

	/**
	 *
	 * @author daniel.wang
	 * @param username
	 * @return
	 * @since JDK 1.6
	 */
	public OpenAuthorizationWeixin getOpenAuthorizationWeixinByAuthorizerUserName(
			@Param("user_name") String username);

	/**
	 * deleteAuthorizerWeixinByAuthorizeAppid:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author daniel.wang
	 * @param authorizerAppid
	 * @since JDK 1.6
	 */
	public void deleteAuthorizerWeixinByAuthorizeAppid(@Param("authorizer_appid") String authorizerAppid);	
}