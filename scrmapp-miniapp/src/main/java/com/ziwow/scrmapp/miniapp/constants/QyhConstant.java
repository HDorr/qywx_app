package com.ziwow.scrmapp.miniapp.constants;

/**
 * 
 * @ClassName: WeixinConstants
 * @author hogen
 * @date 2016-8-4 上午11:11:15
 * 
 */
public class QyhConstant {
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}";
	public static final String SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=${APPID}&secret=${SECRET}&js_code=${JSCODE}&grant_type=authorization_code";
}