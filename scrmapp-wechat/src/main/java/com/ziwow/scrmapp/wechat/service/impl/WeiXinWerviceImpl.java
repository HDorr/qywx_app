/**
 * @Title: WeiXinWerviceImpl.java
 * @Package com.ziwow.marketing.weixin.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen
 * @date 2016-8-4 上午11:09:52
 * @version V1.0
 */
package com.ziwow.scrmapp.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.persistence.mapper.QyhUserMapper;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.RedisKeyConstants;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;
import com.ziwow.scrmapp.wechat.service.WeiXinService;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatJSSdkSignVO;
import com.ziwow.scrmapp.wechat.weixin.WechatJSSdkSign;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hogen
 * @ClassName: WeiXinWerviceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2016-8-4 上午11:09:52
 */
@Service
public class WeiXinWerviceImpl implements WeiXinService {
    Logger LOG = LoggerFactory.getLogger(WeiXinWerviceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private OpenWeixinService openWeixinService;
    @Value("${open.weixin.component_appid}")
    private String component_appid;

    @Override
    public synchronized String getAccessToken(String appid, String secret) {
        String jsonStr = StringUtils.EMPTY;
        String key = RedisKeyConstants.getWechatAccesstokenKey() + ":" + appid;
        String accessToken = (String) redisService.get(key);
        if (StringUtils.isEmpty(accessToken)) {
            try {
                OpenAuthorizationWeixin authorizationWeixin = openWeixinService.getAuthorizerWeixinByAuthorizerAppid(appid);
                if (authorizationWeixin == null) {
                    jsonStr = HttpKit.get(WeChatConstants.BASE_ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
                    LOG.info("获取access_token,返回结果[{}]", jsonStr);
                    if (StringUtil.isNotBlank(jsonStr)) {
                        net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(jsonStr);
                        accessToken = object.getString("access_token");
                        // 将accessToken缓存1.5小时
                        redisService.setKeyExpire(key, accessToken, 5400L, TimeUnit.SECONDS);
                    }
                } else {
                    accessToken = openWeixinService.getAuthorizerOauthAccessToken(component_appid, appid);
                }
            } catch (Exception e) {
                LOG.error("获取接口调用凭据access_token失败:", e);
            }
        }
        return accessToken;
    }

    @Override
    public UserInfo getUserInfo(String accessToken, String openid) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        params.put("lang", "zh_CN");
        String jsonStr = HttpKit.get(WeChatConstants.USER_INFO_URL, params);
        if (StringUtils.isNotEmpty(jsonStr)) {
            LOG.info("getUserInfo jsonStr:" + jsonStr);
            JSONObject obj = JSONObject.parseObject(jsonStr);
            if (obj.get("errcode") != null) {
                throw new Exception(obj.getString("errmsg"));
            }
            UserInfo user = JSONObject.toJavaObject(obj, UserInfo.class);
            return user;
        }
        return null;
    }

    @Override
    public String getTicket(String accessToken, String type) throws Exception {
        String jsonStr = HttpKit.get(WeChatConstants.TICKET_URL.concat("?access_token=") + accessToken + "&type=" + type);
        if (StringUtils.isNotEmpty(jsonStr)) {
            JSONObject obj = JSONObject.parseObject(jsonStr);
            if (!obj.get("errmsg").equals("ok")) {
                throw new Exception(obj.getString("errmsg"));
            }
            return obj.getString("ticket");
        }
        return null;
    }

    @Override
    public JSONObject customSend(String accessToken, String jsonBody) {
        String reslut;
        try {
            reslut = HttpKit.post(WeChatConstants.CUSTOM_SEND.concat(accessToken), jsonBody);
            if (StringUtils.isNotEmpty(reslut)) {
                return JSONObject.parseObject(reslut);
            }
        } catch (Exception e) {
            LOG.error("发送客服消息失败[{}]", e.getMessage());
        }
        return null;
    }

    public String getQRTicket(Long channelId, String appId, String secret) {
        String accessToken = this.getAccessToken(appId, secret);

        JSONObject scene_id = new JSONObject();
        scene_id.put("scene_id", channelId);
        JSONObject scene = new JSONObject();
        scene.put("scene", scene_id);

        JSONObject object = new JSONObject();
        object.put("action_name", "QR_LIMIT_SCENE");
        object.put("action_info", scene);

        try {
            String reslut = HttpKit.post(WeChatConstants.QR_TICKET_URL.concat(accessToken), object.toJSONString());
            if (StringUtils.isNotEmpty(reslut)) {
                return JSONObject.parseObject(reslut).getString("ticket");
            }
        } catch (Exception e) {
            LOG.error("发送客服消息失败[{}]", e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 发送文本客服消息
     *
     * @param openId
     * @param text
     * @throws Exception
     */
    @Override
    public String sendText(String accessToken, String openId, String text) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("content", text);
        json.put("touser", openId);
        json.put("msgtype", "text");
        json.put("text", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送客服消息
     *
     * @param accessToken
     * @param message
     * @return
     * @throws Exception
     */
    private String sendMsg(String accessToken, Map<String, Object> message) throws Exception {
        String reslut = HttpKit.post(WeChatConstants.CUSTOM_SEND.concat(accessToken), JSONObject.toJSONString(message));
        return reslut;
    }

    @Override
    public WechatJSSdkSignVO getJSApiSign(String url, String appId, String secret) {
        String accessToken = this.getAccessToken(appId, secret);
        LOG.info("jsapi获取accessToken=[{}]", accessToken);
        try {
            String jssdkTicket = this.getTicket(accessToken, "jsapi");
            LOG.info("jsapi获取jssdkTicket=[{}]", jssdkTicket);
            WechatJSSdkSignVO signVO = this.getSigin(url, appId, jssdkTicket);
            LOG.info("jsapi获取签名对象vo=[{}]", JSON.toJSONString(signVO));
            return signVO;
        } catch (Exception e) {
            LOG.error("获取jsapi sign失败[{}]", e);
        }
        return null;
    }

    private String getOAuthQyhUserInfo(String code) {
        try {
            String userId = "";
            String jsonStr = HttpKit.get(GET_USERINFO_URL.replace("ACCESS_TOKEN", getQyhAccessToken()).replace("CODE", code));
            if (StringUtils.isNotEmpty(jsonStr)) {
                JSONObject obj = JSONObject.parseObject(jsonStr);
                if (obj.get("errcode") != null) {
                    throw new Exception(obj.getString("errmsg"));
                }
                userId = obj.getString("UserId");
                if (StringUtil.isNotBlank(userId)) {
                    return userId;
                }

            }
        } catch (Exception e) {
            LOG.error("企业号员工授权失败:", e);
        }
        return null;
    }

    private String getQyhAccessToken() {
        String accessToken = StringUtils.EMPTY;
        String tokenUrl = String.format(ASSESS_TOKEN_URL, corpId, qyhCorpSecret);
        String KEY = QYH_CORP_ACCESS_TOKEN + corpId;
        if (!redisService.hasKey(KEY)) {
            try {
                String result = HttpKit.get(tokenUrl, null);
                if (StringUtil.isNotBlank(result)) {
                    net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(result);
                    accessToken = jsonObj.getString("access_token");
                    if (StringUtil.isNotBlank(accessToken)) {
                        redisService.set(KEY, accessToken, 7000L);
                    }
                }
            } catch (Exception e) {
                LOG.error("获取企业号唯一票据AccessToken失败:", e);
            }
        } else {
            accessToken = (String) redisService.get(KEY);
        }
        return accessToken;
    }

    @Override
    public QyhUser getOAuthQyhUserInfo(String code, HttpServletRequest request, HttpServletResponse response) {
        //先获取客户端的cookie
        String userId = "";
        try {
            String cookie_token = CookieUtil.readCookie(request, response, COOKIE_PATH);
            if (StringUtil.isNotBlank(cookie_token)) {
                userId = new String(Base64.decode(cookie_token));
                LOG.info("解密userId[{}]", userId);
                return qyhUserMapper.getQyhUserByUserIdAndCorpId(userId, corpId);
            } else {
                //授权
                userId = this.getOAuthQyhUserInfo(code);
                LOG.info("获取员工的授权信息:[{}]", userId);
                if (StringUtil.isNotBlank(userId)) {
                    QyhUser qyhUser = qyhUserMapper.getQyhUserByUserIdAndCorpId(userId, corpId);
                    if (qyhUser != null) {
                        //写入cookie
                        CookieUtil.writeCookie(request, response, COOKIE_PATH, Base64.encode(userId.getBytes()), Integer.MAX_VALUE);
                        return qyhUser;
                    }

                }
            }
        } catch (Exception e) {
            LOG.info("获取授权用户信息失败:", e);
        }
        return null;
    }

    private WechatJSSdkSignVO getSigin(String url, String appId, String ticket) {
        WechatJSSdkSignVO signVO = new WechatJSSdkSignVO();
        Map<String, String> signMap = WechatJSSdkSign.getTicketSign(ticket, url);
        String nonceStr = signMap.get("nonceStr").toString();
        String timestamp = signMap.get("timestamp").toString();
        String signature = signMap.get("signature").toString();
        signVO.setAppId(appId);
        signVO.setNonceStr(nonceStr);
        signVO.setSignature(signature);
        signVO.setTimestamp(timestamp);
        return signVO;
    }

    public static final String COOKIE_PATH = "SCRMAPP_TOKEN_QYH_USERID";

    //根据code获取成员信息
    public static final String GET_USERINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
    public static final String ASSESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    public final static String QYH_CORP_ACCESS_TOKEN = "string:scrmapp:qyhcorp:accesstoken:";   // 企业号的全局唯一票据AccessToken


    @Value("${qyh.open.corpid}")
    private String corpId;
    @Value("${qyh.open.encodingaeskey}")
    private String sEncodingAESKey;
    @Value("${qyh.corp.secret}")
    private String qyhCorpSecret;
    @Autowired
    private QyhUserMapper qyhUserMapper;
}
