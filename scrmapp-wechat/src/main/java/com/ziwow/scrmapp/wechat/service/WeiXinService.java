/**
 * @Title: WeiXinWervice.java
 * @Package com.ziwow.marketing.weixin.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen
 * @date 2016-8-4 上午11:09:15
 * @version V1.0
 */
package com.ziwow.scrmapp.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.wechat.params.wechat.QrCodeParam;
import com.ziwow.scrmapp.wechat.vo.AccessToken;
import com.ziwow.scrmapp.wechat.vo.OauthUser;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatJSSdkSignVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 微信对接接口
 *
 * @author hogen
 * @ClassName: WeiXinService
 * @date 2016-8-4 上午11:09:15
 */
public interface WeiXinService {

    /**
     * 获取微信accessToken
     *
     * @param @param  appid
     * @param @param  secret
     * @param @return 设定文件
     * @Title: getAccessToken
     * @version 1.0
     * @author Hogen.hu
     */
    public String getAccessToken(String appid, String secret);


    /**
     * 拉取关注用户信息
     *
     * @param @param  accessToken
     * @param @param  openid
     * @param @return 设定文件
     * @return UserInfo    返回类型
     * @Title: getUserInfo
     * @version 1.0
     * @author Hogen.hu
     */
    public UserInfo getUserInfo(String accessToken, String openid) throws Exception;



    /**
     * 获取授权类型为snsapi_userinfo用户信息
     *
     * @param @param  accessToken
     * @param @param  openid
     * @param @return 设定文件
     * @return UserInfo    返回类型
     * @Title: getUserInfo
     * @version 1.0
     * @author Hogen.hu
     */
    UserInfo getWebUserInfo(String accessToken, String openid) throws Exception;

    /**
     * jssdk
     *
     * @param @param  accessToken
     * @param @return
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @Title: getTicket
     * @version 1.0
     * @author Hogen.hu
     */
    public String getTicket(String accessToken, String type) throws Exception;

    /**
     * 发送客服消息
     *
     * @param @param  accessToken
     * @param @param  jsonBody
     * @param @return 设定文件
     * @return JSONObject    返回类型
     * @Title: customSend
     * @version 1.0
     * @author Hogen.hu
     */
    public JSONObject customSend(String accessToken, String jsonBody);

    public String sendText(String accessToken, String openId, String text) throws Exception;

    public WechatJSSdkSignVO getJSApiSign(String url, String appId, String secret);

    QyhUser getOAuthQyhUserInfo(String code, HttpServletRequest request, HttpServletResponse response);

    String getQRTicket(Long channelId, String appId, String secret);

    /**
     * 获取二维码
     * @param qrCodeParam {@link QrCodeParam}
     * @param appId {@link String}
     * @param secret {@link String}
     * @return {@link Map}
     */
    Map<String,Object> getQrCode(QrCodeParam qrCodeParam, String appId, String secret);
}
