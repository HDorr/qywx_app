/**
 * Project Name:ziwow-dubbo-weixin-api
 * File Name:OpenWeixinService.java
 * Package Name:com.ziwow.weixin.api.open
 * Date:2016-4-26下午5:02:16
 * Copyright (c) 2016, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.wechat.service;

import net.sf.json.JSONObject;

import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenWeixin;

/**
 * ClassName: OpenWeixinService <br/>
 * Function: 第三方授权平台功能. <br/>
 * date: 2016-4-26 下午5:02:16 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public interface OpenWeixinService {
    /**
     * 
     * getOpenWeixin:(获取第三方授权平台的基本信息). <br/>
     *
     * @author daniel.wang
     * @param component_appid
     * @return
     * @since JDK 1.6
     */
    public OpenWeixin getOpenWeixin(String component_appid);     
    /**
     * 
     * refreshOpenWeixin:(更新openWexin信息). <br/>
     *
     * @author daniel.wang
     * @param wx
     * @since JDK 1.6
     */
    public void refreshOpenWeixin(OpenWeixin wx);
    /**
     * 
     * getComponentAccessTokenByAppid:(获取第三方平台component_access_token). <br/>
     *
     * @author daniel.wang
     * @param component_appid
     * @return
     * @since JDK 1.6
     */
    public String getComponentAccessTokenByAppid(String component_appid);
    
    /**
     * 
     * getPreAuthcodeByAppid:(获取授权平台预授权码pre_auth_code). <br/>
     *
     * @author daniel.wang
     * @param component_appid
     * @return
     * @since JDK 1.6
     */
    public String getPreAuthcodeByAppid(String component_appid);
    public String getAuthLoginPageUrl();
    public OpenAuthorizationWeixin refreshAuthorizerWeixin(OpenAuthorizationWeixin openAuthorizationWeixin);
    
    /**
     * 
     * refreshAuthorizerWeixinApi:(使用授权码换取公众号的接口调用凭据和授权信息). <br/>
     *
     * @author daniel.wang
     * @param component_appid
     * @param authorization_code
     * @since JDK 1.6
     */
    public OpenAuthorizationWeixin getAuthorizerWeixinApi(String component_appid,String authorization_code);
    /**
     * 
     * getAuthorizerOauthAccessToken:(获取（刷新）授权公众号的接口调用凭据（令牌）). <br/>
     *
     * @author daniel.wang
     * @param component_appid
     * @param authorizer_appid
     * @return
     * @since JDK 1.6
     */
    public String getAuthorizerOauthAccessToken(String component_appid,String authorizer_appid);
    public OpenAuthorizationWeixin getAuthorizerWeixinByAuthorizerAppid(String appid);
    /**
     * 
     * getAuthorizerWeixinByAuthorizerUserName:(根据gh_开头的username查找授权公众号的信息). <br/>
     *
     * @author daniel.wang
     * @param username
     * @return
     * @since JDK 1.6
     */
    public OpenAuthorizationWeixin getAuthorizerWeixinByAuthorizerUserName(String username);
    public JSONObject getAuthorizerWeixinOpenId(String appid, String code);
    public OpenWeixin getOpenWeixin();    
    public void deleteAuthorizerWeixinByAuthorizeAppid(String authorizerAppid);
    public String getUnoauthFunc(String channelId);
}