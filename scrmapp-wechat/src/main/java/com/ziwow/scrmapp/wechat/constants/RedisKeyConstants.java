package com.ziwow.scrmapp.wechat.constants;

public class RedisKeyConstants {
	/**
	 * key 命名规则 应用名:Key类型:工程名:(第三方应用名:)模块名:功能名:(用途来源:) Key类型：string,
	 * sets,list,map,sortedsets
	 * 
	 * 如果是通用模块就用common来代替
	 * 
	 * 定义变量后加get方法，防止引用错别人的KEY
	 */
	private final static String OPEN_WEXIN_COMPONENT_ACCESSTOKEN_PREFIX = "string:open:weixin:component:accesstoken"; // 第三方公众平台的accesstoken前缀
	private final static String OPEN_WEXIN_AUTHORIZER_ACCESSTOKEN_PREFIX = "string:open:weixin:authorizer:accesstoken"; // 被授权公众平台的accesstoken前缀
	private final static String SCRMAPP_USER_REGISTER_SENDMSG = "string.scrmapp.user.sendmsg."; // 用户注册时发送验证码
	private final static String SCRMAPP_WEIXIN_MSG_TEMPLATEID = "string.scrmapp.weixin.msg.templateid."; // 获取templateid
	private final static String SCRMAPP_WECHAT_ACCESSTOKEN="string:scrmapp:wechat:accesstoken";// 公众号的基础accesstoken
	
	public static String getOpenWxComponentAccesstokenPrefixKey() {
		return OPEN_WEXIN_COMPONENT_ACCESSTOKEN_PREFIX;
	}

	public static String getOpenWxAuthorizerAccesstokenPrefixKey() {
		return OPEN_WEXIN_AUTHORIZER_ACCESSTOKEN_PREFIX;
	}
	
	public static String getUserRegisgterKey() {
        return SCRMAPP_USER_REGISTER_SENDMSG;
    }
	
	public static String getTemplateIdKey() {
        return SCRMAPP_WEIXIN_MSG_TEMPLATEID;
    }
	
	public static String getWechatAccesstokenKey() {
		return SCRMAPP_WECHAT_ACCESSTOKEN;
	}
}