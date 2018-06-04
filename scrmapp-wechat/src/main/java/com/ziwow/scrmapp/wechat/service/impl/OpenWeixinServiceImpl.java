/**
 * Project Name:ziwow-dubbo-weixin-provider
 * File Name:OpenWeixinServiceImpl.java
 * Package Name:com.ziwow.weixin.provider.impl.open
 * Date:2016-4-27上午10:23:28
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.tools.utils.EmojiFilter;
import com.ziwow.scrmapp.tools.weixin.enums.OpenWeixinFunType;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenWeixin;
import com.ziwow.scrmapp.wechat.persistence.mapper.OpenWeixinMapper;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;
import com.ziwow.scrmapp.wechat.weixin.OpenWeixinAction;

/**
 * ClassName: OpenWeixinServiceImpl <br/>
 * date: 2016-4-27 上午10:23:28 <br/>
 * 
 * @author daniel.wang
 * @version
 * @since JDK 1.6
 */
@Service("openWeixinService")
public class OpenWeixinServiceImpl implements OpenWeixinService {
	Logger LOG = LoggerFactory.getLogger(OpenWeixinServiceImpl.class);
	@Resource
	OpenWeixinMapper openWeixinMapper;

	@Resource
	RedisService redisService;

	@Value("${open.weixin.component_appid}")
	private String component_appid;

	@Value("${open.weixin.component_token}")
	private String component_token;

	@Value("${open.weixin.component_key}")
	private String component_key;

	@Value("${open.weixin.component_appsecret}")
	private String component_appsecret;

	/**
	 * 获取第三方授权平台的基本信息
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getOpenWeixin(java.lang.String)
	 */
	@Override
	public OpenWeixin getOpenWeixin(String component_appid) {
		return openWeixinMapper.getOpenWeixin(component_appid);
	}

	/**
	 * 获取第三方平台的access_token
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getComponentAccessTokenByAppid(java.lang.String)
	 */
	@Override
	public String getComponentAccessTokenByAppid(String component_appid) {
		String KEY = RedisKeyConstants.getOpenWxComponentAccesstokenPrefixKey() + ":" + component_appid;
		String accessToken = searchKey(KEY);
		if (accessToken == null) {
			LOG.info("从缓存中key[{}]中取第三方授权ComponentAccessToken为空,从微信处查询component_appid[{}]的accesstoken", KEY, component_appid);
			OpenWeixin ow = getOpenWeixin(component_appid);
			String component_appsecret = ow.getComponent_appsecret();
			String component_verify_ticket = ow.getComponent_verify_ticket();
			accessToken = OpenWeixinAction.getComponentAccessTokenByAppid(component_appid, component_appsecret,
					component_verify_ticket);
			if (StringUtils.isNotBlank(accessToken)) {
				setAccessTokenInCache(KEY, accessToken);
				OpenWeixin updateOW = new OpenWeixin();
				updateOW.setComponent_access_token(accessToken);
				updateOW.setComponent_appid(component_appid);
				refreshOpenWeixin(updateOW);
			}
			setAccessTokenInCache(KEY, accessToken);
		} else {
			LOG.info("从缓存中key{}中取第三方授权ComponentAccessToken成功返回", KEY);
		}
		return accessToken;
	}

	/**
	 * 获取第三方平台的预授权码
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getPreAuthcodeByAppid(java.lang.String)
	 */
	@Override
	public String getPreAuthcodeByAppid(String component_appid) {
		String component_access_token = getComponentAccessTokenByAppid(component_appid);
		return OpenWeixinAction.getPreAuthcodeByAppid(component_appid, component_access_token);
	}

	/**
	 * 获取授权的页面
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getPreAuthPageByAppid(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getAuthLoginPageUrl() {
		String preAuthCode = getPreAuthcodeByAppid(component_appid);
		LOG.info("根据component_appid{},获取preAuthCode{}", component_appid, preAuthCode);
		String preAuthPageURL = OpenWeixinAction.getAuthLoginPageUrl(component_appid, preAuthCode);
		LOG.info("根据component_appid{}获取authLoginPage结果{}", component_appid, preAuthPageURL);
		return preAuthPageURL;
	}

	/**
	 * 更新被授权的公众号的access_token与refresh_access_token
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#refreshAuthorizerWeixinApi(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public OpenAuthorizationWeixin getAuthorizerWeixinApi(String component_appid, String authorization_code) {

		String component_access_token = getComponentAccessTokenByAppid(component_appid);
		JSONObject o = OpenWeixinAction.getAuthorizerWeixinApi(component_appid, authorization_code, component_access_token);
		if (o != null) {
			String authorizer_appid = o.getString("authorizer_appid");
			String authorizer_access_token = o.getString("authorizer_access_token");
			String authorizer_refresh_token = o.getString("authorizer_refresh_token");
			JSONArray funArray = o.getJSONArray("func_info");
			String func_info = null;
			if (funArray != null && funArray.size() > 0) {
				func_info = funArray.toString();
			}
			OpenAuthorizationWeixin oaw = new OpenAuthorizationWeixin();
			oaw.setComponent_appid(component_appid);
			oaw.setAuthorizer_appid(authorizer_appid);
			oaw.setAuthorizer_refresh_token(authorizer_refresh_token);
			oaw.setAuthorizer_access_token(authorizer_access_token);
			oaw.setFunc_info(func_info);
			// oaw.setAuthorization_code(authorization_code);
			// 获取授权方的公众号帐号基本信息
			JSONObject authorizerInfo = OpenWeixinAction.getAuthorizerInfo(component_appid, authorizer_appid,
					component_access_token);
			LOG.info("获取component_appid[{}],authorizer_appid[{}]被授权公众号的信息为[{}]", component_appid, authorizer_appid,
					authorizerInfo);
			if (authorizerInfo != null) {
				JSONObject authorizer_info = authorizerInfo.getJSONObject("authorizer_info");
				if (authorizer_info != null) {
					LOG.info("获取component_appid[{}],authorizer_appid[{}]被授权公众号的信息为[{}]", component_appid, authorizer_appid,
							authorizerInfo);
					if (authorizer_info.containsKey("nick_name")) {
						String nickName = authorizer_info.getString("nick_name");
						if (StringUtils.isNotBlank(nickName)) {
							oaw.setNick_name(EmojiFilter.filterEmoji(nickName));
						}
					}
					if (authorizer_info.containsKey("head_img")) {
						String headImg = authorizer_info.getString("head_img");
						if (StringUtils.isNotBlank(headImg)) {
							oaw.setHead_img(authorizer_info.getString("head_img"));
						}
					}

					JSONObject service_type_info = authorizer_info.getJSONObject("service_type_info");
					JSONObject verify_type_info = authorizer_info.getJSONObject("verify_type_info");
					if (service_type_info != null) {
						oaw.setService_type_info(service_type_info.getInt("id"));
					}
					if (verify_type_info != null) {
						oaw.setVerify_type_info(verify_type_info.getInt("id"));
					}
					oaw.setUser_name(authorizer_info.getString("user_name"));
					oaw.setBusiness_info(authorizer_info.getString("business_info"));
					oaw.setAlias(authorizer_info.getString("alias"));
					oaw.setQrcode_url(authorizer_info.getString("qrcode_url"));
				}
			}
			LOG.info("更新AuthorizerWeixin的access_token与refresh_token信息为{}", JSONObject.fromObject(oaw));
			/* openWeixinMapper.updateOpenAuthorizationWeixin(oaw); */
			// 授权的时候更新authorizer_appid的accesstoken缓存
			if (StringUtils.isNotBlank(authorizer_appid)) {
				String KEY = RedisKeyConstants.getOpenWxAuthorizerAccesstokenPrefixKey() + ":" + authorizer_appid;
				setAccessTokenInCache(KEY, authorizer_access_token);
			}
			return oaw;
		}
		return null;
	}

	/**
	 * setAccessTokenInCache:(设置缓存). <br/>
	 * 
	 * @author daniel.wang
	 * @param KEY
	 * @param authorizer_access_token
	 * @since JDK 1.6
	 */
	private void setAccessTokenInCache(String KEY, String authorizer_access_token) {
		try {
			redisService.setKeyExpire(KEY, authorizer_access_token, 6000L, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取（刷新）授权公众号的接口调用凭据（令牌）
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getAuthorizerOauthAccessToken(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getAuthorizerOauthAccessToken(String component_appid, String authorizer_appid) {
		String KEY = RedisKeyConstants.getOpenWxAuthorizerAccesstokenPrefixKey() + ":" + authorizer_appid;
		String component_access_token = getComponentAccessTokenByAppid(component_appid);
		String authorizer_access_token = searchKey(KEY);
		if (authorizer_access_token == null) {
			LOG.info("从缓存中key{}中取授权authorizer_access_token为空,从微信处查询authorizer_appid{}的authorizer_access_token", KEY,
					authorizer_appid);
			OpenAuthorizationWeixin oaw = openWeixinMapper.getOpenAuthorizationWeixin(component_appid, authorizer_appid);
			String authorizer_refresh_token = oaw.getAuthorizer_refresh_token();
			if (StringUtils.isNotBlank(authorizer_refresh_token)) {
				JSONObject o = OpenWeixinAction.getAuthorizer_access_token(component_appid, authorizer_appid,
						authorizer_refresh_token, component_access_token);
				if (o != null) {
					authorizer_access_token = o.getString("authorizer_access_token");
					String authorizer_refresh_token_update = o.getString("authorizer_refresh_token");
					OpenAuthorizationWeixin oawU = new OpenAuthorizationWeixin();
					oawU.setComponent_appid(component_appid);
					oawU.setAuthorizer_appid(authorizer_appid);
					oawU.setAuthorizer_refresh_token(authorizer_refresh_token_update);
					oawU.setAuthorizer_access_token(authorizer_access_token);
					LOG.info("用原来的authorizer_refresh_token{}更新AuthorizerWeixin的access_token与refresh_token结果为{}",
							authorizer_refresh_token, JSONObject.fromObject(oawU));
					openWeixinMapper.updateOpenAuthorizationWeixin(oawU);
					setAccessTokenInCache(KEY, authorizer_access_token);
				}
			}

		} else {
			LOG.info("从缓存中key{}中取得被授权authorizer_access_token成功返回", KEY);
		}
		return authorizer_access_token;
	}

	/**
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#refreshOpenWeixin(com.ziwow.dubbo.model.weixin.open.OpenWeixin)
	 */
	@Override
	public void refreshOpenWeixin(OpenWeixin wx) {
		openWeixinMapper.updateOpenWeixin(wx);
	}

	/**
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#refreshAuthorizerWeixin(com.ziwow.dubbo.model.weixin.open.OpenAuthorizationWeixin)
	 */
	@Override
	public OpenAuthorizationWeixin refreshAuthorizerWeixin(OpenAuthorizationWeixin openAuthorizationWeixin) {
		String component_appid = openAuthorizationWeixin.getComponent_appid();
		if(StringUtils.isEmpty(component_appid)){
			component_appid = this.component_appid;
		}
		String authorization_code = openAuthorizationWeixin.getAuthorization_code();
		OpenAuthorizationWeixin oaw1 = getAuthorizerWeixinApi(component_appid, authorization_code);
		String authorizer_appid = oaw1.getAuthorizer_appid();
		if (component_appid != null && authorizer_appid != null) {
			OpenAuthorizationWeixin oaw = openWeixinMapper.getOpenAuthorizationWeixin(component_appid, authorizer_appid);
			if (oaw != null) {
				// update
				LOG.info("更新AuthorizerWeixin授权信息为[{}]", JSONObject.fromObject(oaw1));
				// 只有服务号才更新
				/*
				 * if(oaw1.getService_type_info()==2){
				 * openWeixinMapper.updateOpenAuthorizationWeixin(oaw1); }
				 */
				openWeixinMapper.updateOpenAuthorizationWeixin(oaw1);
			} else {
				// insert
				LOG.info("插入AuthorizerWeixin授权信息为{}", JSONObject.fromObject(oaw1));
				/*
				 * if(oaw1.getService_type_info()==2){
				 * openWeixinMapper.insertOpenAuthorizationWeixin(oaw1); }
				 */
				openWeixinMapper.insertOpenAuthorizationWeixin(oaw1);
			}

		}
		return oaw1;
	}

	private String searchKey(String redis_token_key) {
		// 保证20秒内更新
		try {
			Long expireTime = redisService.getExpire(redis_token_key, TimeUnit.SECONDS);
			// 大于20秒
			if (expireTime > 20) {
				return (String) redisService.get(redis_token_key);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getAuthorizerWeixinByAuthorizerAppid(java.lang.String)
	 */
	@Override
	public OpenAuthorizationWeixin getAuthorizerWeixinByAuthorizerAppid(String appid) {
		return openWeixinMapper.getOpenAuthorizationWeixinByAppid(appid);
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getAuthorizerWeixinOpenId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public JSONObject getAuthorizerWeixinOpenId(String appid, String code) {
		OpenAuthorizationWeixin oaw = getAuthorizerWeixinByAuthorizerAppid(appid);
		if (oaw != null) {
			String component_appid = oaw.getComponent_appid();
			String component_access_token = getComponentAccessTokenByAppid(component_appid);
			JSONObject o = OpenWeixinAction.getOpenid(appid, code, component_appid, component_access_token);
			LOG.info("根据被授权appid[{}],code[{}]获取第三方授权appid[{}]的信息为[{}]", appid, code, component_appid, o);
			return o;
		}
		return null;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getOpenWeixin()
	 */
	@Override
	public OpenWeixin getOpenWeixin() {
		OpenWeixin ow = new OpenWeixin();
		ow.setComponent_token(component_token);
		ow.setComponent_appid(component_appid);
		ow.setComponent_key(component_key);
		ow.setComponent_appsecret(component_appsecret);
		return ow;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getAuthorizerWeixinByAuthorizerUserName(java.lang.String)
	 */
	@Override
	public OpenAuthorizationWeixin getAuthorizerWeixinByAuthorizerUserName(String username) {
		return openWeixinMapper.getOpenAuthorizationWeixinByAuthorizerUserName(username);
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#deleteAuthorizerWeixinByAuthorizeAppid(java.lang.String)
	 */
	@Override
	public void deleteAuthorizerWeixinByAuthorizeAppid(String authorizerAppid) {
		LOG.info("删除被授权的公众号[{}]", authorizerAppid);
		openWeixinMapper.deleteAuthorizerWeixinByAuthorizeAppid(authorizerAppid);

	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.OpenWeixinService.api.open.OpenWeixinService#getUnoauthFunc(java.lang.String)
	 */
	@Override
	public String getUnoauthFunc(String authAppid) {
		OpenAuthorizationWeixin oaw = getAuthorizerWeixinByAuthorizerAppid(authAppid);
		if (oaw != null) {
			Set<Integer> allFun = new HashSet<Integer>();
			Set<Integer> hasFun = new HashSet<Integer>();
			Set<Integer> result = new HashSet<Integer>();
			for (int i = 1; i <= 15; i++) {
				allFun.add(i);
			}
			String func_info = oaw.getFunc_info();
			if (StringUtils.isNotBlank(func_info)) {
				JSONArray arry = JSONArray.fromObject(func_info);
				for (Object object : arry) {
					if (object != null) {
						JSONObject o = JSONObject.fromObject(object);
						JSONObject funObj = o.getJSONObject("funcscope_category");
						hasFun.add(funObj.getInt("id"));
					}
				}
			}
			result.addAll(allFun);
			result.removeAll(hasFun);
			StringBuffer funUnauthorize = new StringBuffer();
			if (!result.isEmpty()) {
				for (Integer i : result) {
					funUnauthorize.append(OpenWeixinFunType.getByCode(i).getName()).append(",");
				}
				String returnResult = funUnauthorize.substring(0, funUnauthorize.length() - 1).toString();
				if (StringUtils.isNotBlank(returnResult)) {
					return returnResult;
				}
			}
		}
		return null;
	}
}