package com.ziwow.scrmapp.wechat.service;

public interface WechatValidateService {
	public boolean validateOpenId(String openId, String accessToken);
}