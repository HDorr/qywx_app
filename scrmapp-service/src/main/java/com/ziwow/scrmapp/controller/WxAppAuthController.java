/**   
* @Title: WxAppAuthController.java
* @Package com.ziwow.scrmapp.controller
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-12 下午2:32:48
* @version V1.0   
*/
package com.ziwow.scrmapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.aop.log.SystemLog;
import com.ziwow.scrmapp.aop.requestlimit.RequestLimit;
import com.ziwow.scrmapp.core.cache.RedisService;
import com.ziwow.scrmapp.core.constants.Constant;
import com.ziwow.scrmapp.core.util.StringUtil;
import com.ziwow.scrmapp.service.WxAppService;
import com.ziwow.scrmapp.util.result.BaseResult;
import com.ziwow.scrmapp.util.result.Result;
import com.ziwow.scrmapp.vo.SessionKeyVo;
import com.ziwow.scrmapp.weixin.util.aes.WXBizDataCrypt;

/**
 * @ClassName: WxAppAuthController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-12 下午2:32:48
 * 
 */
@RequestMapping("/scrmappAuth")
@Controller
public class WxAppAuthController extends BaseController{

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private WxAppService wxAppService;
	
	@Autowired
	private RedisService redisService;
	
	@Value("${app.weixin.appId}")
	private String appid;
	
	@Value("${app.weixin.secret}")
	private String secret;
	
	/**
	 * 根据客户端传过来的code从微信服务器获取appid和session_key，然后生成3rdkey返回给客户端，后续请求客户端传3rdkey来维护客户端登录态
	 * @param wxCode	小程序登录时获取的code
	 * @return
	 */
	@RequestLimit(count=1,time=5)
	@RequestMapping(value = "/getSession", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> createSssion(HttpServletRequest request,@RequestParam("code")String wxCode){
		SessionKeyVo sessionKeyVo =  wxAppService.getSessionKey(appid, secret, wxCode);
		if(null ==sessionKeyVo){
			return rtnParam(Constant.ERROR_CODE_40001, null);
		}
		String thirdSessionKey =wxAppService.create3rdSession(sessionKeyVo);
		log.info("生成3rdkey返回给客户端,thirdSessionKey=[{}]",thirdSessionKey);
		return rtnParam(Constant.ERROR_CODE_0, thirdSessionKey);
	}
	
	
	/**
	 * 测试
	 */
	@SystemLog(module="模块",methods="方法")
	@RequestLimit(count=2,time=5,tokenAccessRequest=true,ipRequestLimit=true)
	@RequestMapping(value = "/getSession1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> createSssion1(HttpServletRequest request,HttpServletResponse response,@RequestParam("code")String wxCode){
		System.out.println("我被执行");
		return rtnParam(Constant.ERROR_CODE_0, wxCode);
	}
	/**
	 * 获取用户openId和unionId数据(如果没绑定微信开放平台，解密数据中不包含unionId)
	 * 
	 * @Title: decodeUserInfo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param encryptedData
	 *            加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param sessionId
	 *            会话ID
	 * @return
	 * @version 1.0
	 * @author Hogen.hu
	 */
	@RequestMapping(value = "/decodeUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object decodeUserInfo(@RequestParam("encryptedData")String encryptedData,@RequestParam("iv")String iv,@RequestParam("sessionId")String sessionId){
		log.info("decodeUserInfo,传入参数,sessionId=[{}]",sessionId);
		Result result = new BaseResult();
		//从缓存中获取session_key
		SessionKeyVo sessionKeyVo  = this.getSessionKeyByCatch(sessionId);
		if(null==sessionKeyVo){
			result.setReturnCode(Constant.FAIL);
			result.setReturnMsg("重新登录");
			return result;
		}
		String sessionKey = sessionKeyVo.getSession_key();

		try {
			String deString = WXBizDataCrypt.getInstance().decrypt(encryptedData, sessionKey, iv, "UTF-8"); 
			
			if(StringUtil.isNotBlank(deString)){
				JSONObject jsonObject = JSONObject.parseObject(deString); 
				result.setReturnCode(Constant.SUCCESS);
				result.setData(jsonObject);
				return result;
			}
		} catch (Exception e){
			log.error("解密失败,[{}]",e);
		}
		result.setReturnCode(Constant.FAIL);
		result.setReturnMsg("解密失败");
		return result;
	}
	
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> error(HttpServletRequest request,@RequestParam("errorCode")Integer errorCode){
		
		return rtnParam(errorCode, null);
	}
	
	private SessionKeyVo getSessionKeyByCatch(String sessionId){
		try{
			SessionKeyVo sessionKeyVo=(SessionKeyVo) redisService.get(sessionId);
			return sessionKeyVo;
		}catch (Exception e) {
			log.error("getSessionKeyByCatch,[{}]",e);
		}
		
		return null;
	}
}
