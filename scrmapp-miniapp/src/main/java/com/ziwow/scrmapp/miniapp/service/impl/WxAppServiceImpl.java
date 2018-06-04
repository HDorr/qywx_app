/**   
 * @Title: WxAppServiceImpl.java
 * @Package com.ziwow.wxapp.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2017-1-11 下午3:38:41
 * @version V1.0   
 */
package com.ziwow.scrmapp.miniapp.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.miniapp.constants.QyhConstant;
import com.ziwow.scrmapp.miniapp.constants.RedisKeyConstants;
import com.ziwow.scrmapp.miniapp.service.WxAppService;
import com.ziwow.scrmapp.miniapp.vo.SessionKeyVo;
import com.ziwow.scrmapp.tools.utils.JsonUtils;
import com.ziwow.scrmapp.tools.utils.StringUtil;

/**
 * @ClassName: WxAppServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-11 下午3:38:41
 * 
 */
@Service
public class WxAppServiceImpl implements WxAppService {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private RedisService redisService;

	@Override
	public String getAccessToken(String appid, String secret) {
		String key = RedisKeyConstants.ACCESS_TOKEN;
		String accessToken = "";

		if (redisService.hasKey(key)) {
			accessToken = (String) redisService.get(key);
		} else {
			try {
				String jsonStr = HttpKit.get(QyhConstant.ACCESS_TOKEN_URL
						.replace("${APPID}", appid).replace("${APPSECRET}",
								secret));
				log.info("获取access_token,返回结果[{}]", jsonStr);
				if (StringUtil.isNotBlank(jsonStr)) {
					JSONObject object = net.sf.json.JSONObject.fromObject(jsonStr);
					accessToken = object.getString("access_token");
					Long expiresIn = object.getLong("expires_in");
					redisService.set(key, accessToken,expiresIn-200);
				}
			} catch (Exception e) {
				log.error("获取接口调用凭据access_token失败:", e);
			}
		}

		return accessToken;
	}

	@Override
	public SessionKeyVo getSessionKey(String appid, String secret,
			String js_code) {
		try{
			String jsonStr = HttpKit.get(QyhConstant.SESSION_KEY_URL.replace("${APPID}", appid).replace("${SECRET}", secret).replace("${JSCODE}", js_code));
			log.info("获取getSessionKey,返回结果[{}]", jsonStr);
			if(StringUtil.isNotBlank(jsonStr)){
				return JsonUtils.json2Object(jsonStr, SessionKeyVo.class);
			}
		}catch(Exception e){
			log.error("getSessionKey:", e);
		}
		return null;
	}

	@Override
	public String create3rdSession(SessionKeyVo sessionKeyVo) {
		
		String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
		log.info("create3rdSession,参数",JSON.toJSON(sessionKeyVo));
		redisService.set(thirdSessionKey, sessionKeyVo, sessionKeyVo.getExpires_in());
		return thirdSessionKey;
	}

}
