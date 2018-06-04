/**
 * Project Name:ziwow-dubbo-weixin-provider
 * File Name:OpenWeixinAction.java
 * Package Name:com.ziwow.weixin.provider.web.action
 * Date:2016-4-27上午10:30:53
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;

/**
 * ClassName: OpenWeixinAction <br/>
 * Function: 第三方平台微信 <br/>
 * date: 2016-4-27 上午10:30:53 <br/>
 * 
 * @author daniel.wang
 * @version
 * @since JDK 1.6
 */
public class OpenWeixinAction {
	private static final String SCRMAPP_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
	private static final String SCRMAPP_CREATE_PREAUTHCODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode";
	private static final String SCRMAPP_QUERY_AUTH_URL = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth";
	private static final String SCRMAPP_AUTHORIZER_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token";
	private static final String SCRMAPP_AUTHORIZER_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/component/access_token";
	private static final String SCRMAPP_GET_AUTHORIZER_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info";
	private static final String SCRMAPP_AUTH_LOGINPAGE_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage";
	private static final Logger LOG = LoggerFactory.getLogger(OpenWeixinAction.class);

	/**
	 * 
	 * getComponentAccessTokenByAppid:(获取第三方平台component_access_token). <br/>
	 * 
	 * @author daniel.wang
	 * @param component_appid
	 * @param component_appsecret
	 * @param component_verify_ticket
	 * @return
	 * @since JDK 1.6
	 */
	public static String getComponentAccessTokenByAppid(String component_appid, String component_appsecret,
			String component_verify_ticket) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("component_appid", component_appid);
		param.put("component_appsecret", component_appsecret);
		param.put("component_verify_ticket", component_verify_ticket);
		String result = HttpClientUtils.postJson(SCRMAPP_COMPONENT_TOKEN_URL, JSONObject.fromObject(param).toString());
		LOG.info("发送请求获取第三方平台component_access_token结果为{}", JSONObject.fromObject(result));
		if (StringUtils.isNotBlank(result)) {
			JSONObject o = JSONObject.fromObject(result);
			if (o.containsKey("component_access_token")) {
				return o.getString("component_access_token");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * getPreAuthcodeByAppid:(获取预授权码pre_auth_code). <br/>
	 * 
	 * @author daniel.wang
	 * @param component_appid
	 * @param component_access_token
	 * @return
	 * @since JDK 1.6
	 */
	public static String getPreAuthcodeByAppid(String component_appid, String component_access_token) {
		String url = getAppendComponentAccessTokenURL(SCRMAPP_CREATE_PREAUTHCODE_URL, component_access_token);
		JSONObject o = new JSONObject();
		o.put("component_appid", component_appid);
		String preResult = HttpClientUtils.postJson(url, o.toString());
		LOG.info("发送请求到URL{}获取第三方平台component_access_token结果为{}", url, JSONObject.fromObject(preResult));
		if (StringUtils.isNotBlank(preResult)) {
			JSONObject o1 = JSONObject.fromObject(preResult);
			if (o1.containsKey("pre_auth_code")) {
				return o1.getString("pre_auth_code");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * getAuthorizerWeixinApi:(使用授权码换取公众号的接口调用凭据和授权信息). <br/>
	 * 
	 * @author daniel.wang
	 * @param component_appid
	 * @param authorization_code
	 * @param component_access_token
	 * @return
	 * @since JDK 1.6
	 */
	public static JSONObject getAuthorizerWeixinApi(String component_appid, String authorization_code,
			String component_access_token) {
		String url = getAppendComponentAccessTokenURL(SCRMAPP_QUERY_AUTH_URL, component_access_token);
		JSONObject o = new JSONObject();
		o.put("component_appid", component_appid);
		o.put("authorization_code", authorization_code);
		String queryOauthResult = HttpClientUtils.postJson(url, o.toString());
		if (StringUtils.isNotBlank(queryOauthResult)) {
			JSONObject o1 = JSONObject.fromObject(queryOauthResult);
			if (o1.containsKey("authorization_info")) {
				return o1.getJSONObject("authorization_info");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * getAuthorizer_access_token:(获取（刷新）授权公众号的接口调用凭据（令牌）). <br/>
	 * 
	 * @author daniel.wang
	 * @param component_appid
	 * @param authorizer_appid
	 * @param authorizer_refresh_token
	 * @param component_access_token
	 * @return
	 * @since JDK 1.6
	 */
	public static JSONObject getAuthorizer_access_token(String component_appid, String authorizer_appid,
			String authorizer_refresh_token, String component_access_token) {
		String url = getAppendComponentAccessTokenURL(SCRMAPP_AUTHORIZER_TOKEN_URL, component_access_token);
		JSONObject o = new JSONObject();
		o.put("component_appid", component_appid);
		o.put("authorizer_appid", authorizer_appid);
		o.put("authorizer_refresh_token", authorizer_refresh_token);
		String queryOauthResult = HttpClientUtils.postJson(url, o.toString());
		if (StringUtils.isNotBlank(queryOauthResult)) {
			JSONObject o1 = JSONObject.fromObject(queryOauthResult);
			if (o1 != null) {
				return o1;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static String getAppendComponentAccessTokenURL(String URL, String component_access_token) {
		return URL.concat("?component_access_token=").concat(component_access_token);
	}

	public static JSONObject getOpenid(String appid, String code, String component_appid, String component_access_token) {
		LOG.info("getOpenid参数appid:{},code:{},component_appid:{},component_access_token:{}", appid, code, component_appid,
				component_access_token);
		String URL = SCRMAPP_AUTHORIZER_OPENID_URL.concat("?appid=").concat(appid).concat("&code=").concat(code)
				.concat("&grant_type=authorization_code").concat("&component_appid=").concat(component_appid)
				.concat("&component_access_token=").concat(component_access_token);
		try {
			String result = HttpKit.get(URL);
			if (StringUtils.isNotBlank(result)) {
				JSONObject o1 = JSONObject.fromObject(result);
				if (o1 != null) {
					return o1;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * getAuthorizerInfo:(获取授权方的公众号帐号基本信息). <br/>
	 * 
	 * @author daniel.wang
	 * @param component_appid
	 * @param authorizer_appid
	 * @param component_access_token
	 * @return
	 * @since JDK 1.6
	 */

	public static JSONObject getAuthorizerInfo(String component_appid, String authorizer_appid, String component_access_token) {
		String url = getAppendComponentAccessTokenURL(SCRMAPP_GET_AUTHORIZER_INFO, component_access_token);
		JSONObject o = new JSONObject();
		o.put("component_appid", component_appid);
		o.put("authorizer_appid", authorizer_appid);
		String queryOauthResult;
		try {
			queryOauthResult = HttpKit.post(url, JSONObject.fromObject(o).toString());
			if (StringUtils.isNotBlank(queryOauthResult)) {
				JSONObject o1 = JSONObject.fromObject(queryOauthResult);
				if (o1 != null) {
					return o1;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getAuthLoginPageUrl(String componentAppid, String preAuthCode) {
		if(StringUtils.isEmpty(componentAppid) || StringUtils.isEmpty(preAuthCode)) {
			return null;
		}
		return SCRMAPP_AUTH_LOGINPAGE_URL.concat("?component_appid=").concat(componentAppid).concat("&pre_auth_code=").concat(preAuthCode);
	}
}