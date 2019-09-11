package com.ziwow.scrmapp.wechat.service.impl;

import com.ziwow.scrmapp.wechat.persistence.entity.TempWechatFans;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.utils.CookieUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatFans;
import com.ziwow.scrmapp.wechat.persistence.entity.WechatUser;
import com.ziwow.scrmapp.wechat.persistence.mapper.WechatFansMapper;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;
import com.ziwow.scrmapp.wechat.service.WechatAESService;
import com.ziwow.scrmapp.wechat.service.WechatFansService;
import com.ziwow.scrmapp.wechat.service.WechatUserService;
import com.ziwow.scrmapp.wechat.service.WeiXinService;
import com.ziwow.scrmapp.wechat.vo.AccessToken;
import com.ziwow.scrmapp.wechat.vo.OauthUser;
import com.ziwow.scrmapp.wechat.vo.UserInfo;
import com.ziwow.scrmapp.wechat.vo.WechatFansVo;

@Service
public class WechatFansServiceImpl implements WechatFansService {

	Logger LOG = LoggerFactory.getLogger(WechatFansServiceImpl.class);
	public static final String COOKIE_PATH = "SCRMAPP_TOKEN";
	@Value("${wechat.appid}")
	private String appId;
	@Value("${wechat.appSecret}")
	private String appSecret;
	@Resource
	private WechatAESService wechatAESService;
	@Resource
	WechatFansMapper wechatFansMapper;
	@Resource
	WeiXinService weiXinService;
	@Resource
	private WechatUserService wechatUserService;
	@Resource
	private OpenWeixinService openWeixinService;
	@Override
	public void saveWechatFans(WechatFans wechatFans) {
		wechatFansMapper.saveWechatFans(wechatFans);
	}

	@Override
	public void updateWechatFans(WechatFans wechatFans) {
		LOG.info("更新粉丝表:" + JSONObject.toJSONString(wechatFans));
		wechatFansMapper.updateWechatFans(wechatFans);
	}

	@Override
	public void updWechatFansById(WechatFans wechatFans) {
		LOG.info("根据id更新粉丝表:" + JSONObject.toJSONString(wechatFans));
		wechatFansMapper.updWechatFansById(wechatFans);
	}

	@Override
	public WechatFans getWechatFans(String openId) {
		return wechatFansMapper.getWechatFans(openId);
	}

	@Override
	public WechatFans getWechatFansByOpenId(String openId) {
		return wechatFansMapper.getWechatFansByOpenId(openId);
	}

	@Override
	public WechatFans getFans(String unionId) {
		return wechatFansMapper.getFans(unionId);
	}

	private AccessToken getTokenByCode(String code) {
		AccessToken accessToken = null;
		OpenAuthorizationWeixin oaw = openWeixinService.getAuthorizerWeixinByAuthorizerAppid(appId);
		if (oaw != null) {
			// 通过三方授权获取access_token和openId
			net.sf.json.JSONObject obj = openWeixinService.getAuthorizerWeixinOpenId(appId, code);
			accessToken = (AccessToken)net.sf.json.JSONObject.toBean(obj, AccessToken.class);
		} else {
			// 通过微信网页授权获取access_token和openId
			String url = WeChatConstants.CODE_ACCESS_TOKEN.replace("${APPID}", appId).replace("${SECRET}", appSecret).replace("${CODE}", code);
			try {
				String result = HttpKit.get(url);
				if (StringUtils.isNotEmpty(result)) {
					JSONObject obj = JSONObject.parseObject(result);
					if (obj.get("errcode") != null) {
						throw new Exception(obj.getString("errmsg"));
					}
					accessToken = JSONObject.toJavaObject(obj, AccessToken.class);
				}
			} catch (Exception e) {
				LOG.error("通过code换取网页授权access_token和openId失败:", e);
			}
		}
		return accessToken;
	}

	@Override
	public WechatFansVo getOAuthUserInfo(String code, HttpServletRequest request, HttpServletResponse response) {
		//先获取客户端的cookie
		String openId = "";
		try{
			String cookie_token = CookieUtil.readCookie(request, response, COOKIE_PATH);
			if(StringUtil.isNotBlank(cookie_token)) {
				openId = wechatAESService.Decrypt(cookie_token);
				LOG.info("解密token[{}]", openId);
				return this.isFans(openId, code, true);
			} else {
				//授权
				OauthUser oauthUser = this.getOAuthUserInfo(code);
				LOG.info("不是粉丝，需要授权获取,获取粉丝信息:[{}]",JSON.toJSON(oauthUser));
				if (null != oauthUser) {
					WechatFans fans = this.getWechatFansInfo(oauthUser);
					if(null != fans && StringUtils.isNotEmpty(fans.getOpenId())) {						
						wechatFansMapper.updateWechatFans(fans);
					}
					// 获取授权openId
					openId = oauthUser.getOpenid();
					//写入cookie
					CookieUtil.writeCookie(request, response, COOKIE_PATH, wechatAESService.Encrypt(openId), Integer.MAX_VALUE);
					return this.isFans(openId, code, false);
				}
			}
		} catch (Exception e) {
			LOG.info("获取授权用户信息失败:", e);
		}
		return null;
	}

    /**
     * 获得微信授权用户的用户信息
     *
     * @param oauthUser
     * @return
     */
	private WechatFans getWechatFansInfo(OauthUser oauthUser) {
		if (null != oauthUser) {
			WechatFans wechatFans = new WechatFans();
			wechatFans.setGender(Integer.parseInt(oauthUser.getSex()));
			wechatFans.setHeadImgUrl(oauthUser.getHeadimgurl());
			wechatFans.setOpenId(oauthUser.getOpenid());
			wechatFans.setWfNickName(oauthUser.getNickname());
			wechatFans.setCountry(oauthUser.getCountry());
			wechatFans.setProvince(oauthUser.getProvince());
			wechatFans.setCity(oauthUser.getCity());
			wechatFans.setUnionId(oauthUser.getUnionid());
			return wechatFans;
		}
		return null;
	}

	private OauthUser getOAuthUserInfo(String code) {
		OauthUser oauthUser = null;
		String openId = null;
		try {
			AccessToken accessToken = this.getTokenByCode(code);
			if (accessToken != null) {
				String access_token = weiXinService.getAccessToken(appId, appSecret);
				openId = accessToken.getOpenid();
				UserInfo userInfo = weiXinService.getUserInfo(access_token, openId);
				if(null != userInfo) {
					oauthUser = new OauthUser();
					oauthUser.setCity(userInfo.getCity());
					oauthUser.setCountry(userInfo.getCountry());
					oauthUser.setHeadimgurl(userInfo.getHeadimgurl());
					oauthUser.setNickname(userInfo.getNickname());
					oauthUser.setOpenid(userInfo.getOpenid());
					oauthUser.setProvince(userInfo.getProvince());
					oauthUser.setSex(userInfo.getSex() + "");
					oauthUser.setUnionid(userInfo.getUnionid() != null ? userInfo.getUnionid() : "");
				}
			}
		} catch (Exception e) {
			LOG.info("获取用户["+ openId +"]详细信息失败:", e);
		}
		return oauthUser;
	}

	private WechatFansVo isFans(String openId, String code, boolean cookied) {
		WechatFansVo wechatFansVo = new WechatFansVo();
		WechatFans wechatFans = this.getWechatFans(openId);
		LOG.info("查询数据库是否有这个openid的粉丝信息,[{}]", JSON.toJSON(wechatFans));
		if (null != wechatFans) {
			try {
				WechatUser wechatUser = wechatUserService.getUserByOpenId(openId);
				LOG.info("查询数据库是否有这个openid的会员信息,[{}]", JSON.toJSON(wechatUser));
				if (cookied && StringUtil.isBlank(wechatFans.getHeadImgUrl())) {
					OauthUser oauthUser = this.getOAuthUserInfo(code);
					// 更新数据库
					WechatFans fans = this.getWechatFansInfo(oauthUser);
					if(null != fans) {
						if(StringUtils.isNotEmpty(fans.getOpenId())) {							
							wechatFansMapper.updateWechatFans(fans);						
						}
						wechatFans.setHeadImgUrl(fans.getHeadImgUrl());
						wechatFans.setWfNickName(fans.getWfNickName());
						wechatFans.setGender(fans.getGender());
					}
				}
				// 判断是否会员
				if (null != wechatUser) {
					LOG.info("openId:[{}],已经是会员", openId);
					wechatFansVo.setCode(2);
					wechatFansVo.setToken(wechatAESService.Encrypt(openId));
					wechatFansVo.setUserId(wechatUser.getUserId());
				} else {
					LOG.info("openId:[{}],不是会员", openId);
					wechatFansVo.setCode(1);
					wechatFansVo.setToken(wechatAESService.Encrypt(openId));
				}
				// 设置昵称和头像
				wechatFansVo.setHeadimgurl(wechatFans.getHeadImgUrl());
				wechatFansVo.setNickName(wechatFans.getWfNickName());
				wechatFansVo.setGender(wechatFans.getGender());
			} catch (Exception e) {
				LOG.error("解密token出错:", e);
			}
		} else {
			wechatFansVo.setCode(0);
		}
		return wechatFansVo;
	}

	@Override
	public WechatFans getWechatFansById(Long pkId) {
		return wechatFansMapper.getWechatFansById(pkId);
	}

	@Override
	public UserInfo getUserInfoByOpenId(String openId) throws Exception {
		String access_token = weiXinService.getAccessToken(appId, appSecret);
		return weiXinService.getUserInfo(access_token, openId);
	}

	@Override
	public WechatFans getWechatFansByUserId(String userId) {
		return wechatFansMapper.getWechatFansByUserId(userId);
	}

	@Override
	public WechatFansVo getFansInfo(String code, HttpServletRequest request, HttpServletResponse response) {
		String openId = "";
		WechatFansVo wechatFansVo = null;
		try {
			// 先获取客户端的cookie中的openId
			String cookie_token = CookieUtil.readCookie(request, response, COOKIE_PATH);
			if (StringUtil.isNotBlank(cookie_token)) {
				openId = wechatAESService.Decrypt(cookie_token);
			} else {
				AccessToken accessToken = this.getTokenByCode(code);
				if (null != accessToken) {
					// 获取授权openId
					openId = accessToken.getOpenid();
					// 写入cookie
					CookieUtil.writeCookie(request, response, COOKIE_PATH, wechatAESService.Encrypt(openId), Integer.MAX_VALUE);
				}
			}
			
			// 判断用户会员信息
			if (StringUtils.isNotEmpty(openId)) {
				wechatFansVo = new WechatFansVo();
				WechatFans wechatFans = this.getWechatFans(openId);
				LOG.info("查询是否有openid:[{}],对应的粉丝信息:[{}]", openId, JSON.toJSON(wechatFans));
				if (null != wechatFans) {
					WechatUser wechatUser = wechatUserService.getUserByOpenId(openId);
					if (null != wechatUser) {
						LOG.info("openId:[{}],已经是会员", openId);
						wechatFansVo.setCode(2);
						wechatFansVo.setToken(wechatAESService.Encrypt(openId));
					} else {
						LOG.info("openId:[{}],不是会员", openId);
						wechatFansVo.setCode(1);
						wechatFansVo.setToken(wechatAESService.Encrypt(openId));
					}
				} else {
					wechatFansVo.setCode(0);
				}
			}
		} catch (Exception e) {
			LOG.info("通过静默认证获取用户信息失败:", e);
		}
		return wechatFansVo;
	}

  @Override
  public List<WechatFans> getWechatFansByPage(int page, int size) {
    return wechatFansMapper.getWechatFansByPage(page,size);
  }

  @Override
  public Integer countWechatFans() {
		return wechatFansMapper.countWechatFans();
  }

	@Override
	public List<WechatFans> loadWechatFansAndNotRegisterByPage(int offset, int size) {
		return wechatFansMapper.getWechatFansAndNotRegisterByPage(offset,size);
	}

	@Override
	public Integer loadWechatFansAndNotRegisterCount() {
		return wechatFansMapper.getWechatFansAndNotRegisterCount();
	}


	@Override
	public List<TempWechatFans> loadTempWechatFansBatch1() {
		return wechatFansMapper.selectTempWechatFansBtach1();
	}

  @Override
  public boolean findUserByOpenId(String openId) {
    return wechatFansMapper.findUserByOpenId(openId)>0? true:false;
  }
}