/**
 * Project Name:api-service
 * File Name:AppTokenValidate.java
 * Package Name:com.ziwow.service.scrm.validate
 * Date:2014-7-18下午5:43:11
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.service.WechatValidateService;

/**
 * 
* @包名   com.ziwow.scrmapp.wechat.validate   
* @文件名 ParamValidate.java   
* @作者   john.chen   
* @创建日期 2017-2-21   
* @版本 V 1.0
 */
@Service
public class WechatValidateServiceImpl implements WechatValidateService {
	@Resource
	WechatUserService wechatUserService;
	@Resource
	HttpSession session;
	public boolean validateOpenId(String openId, String accessToken) {
		WechatUser wechatUser = wechatUserService.getUserByOpenId(openId);
		if (wechatUser != null) {
			session.setAttribute(WeChatConstants.USER, wechatUser);
		} else {
			session.setAttribute(WeChatConstants.USER, null);
		}
		return (wechatUser == null ? false : true);
	}
}