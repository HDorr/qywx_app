/**   
 * @Title: OpenWeixinController.java
 * @Package com.ziwow.marketing.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-11-2 上午10:03:47
 * @version V1.0   
 */
package com.ziwow.scrmapp.wechat.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;

/**
 * @ClassName: OpenWeixinController
 * @author hogen
 * @date 2016-11-2 上午10:03:47
 * 
 */
@Controller
@RequestMapping("/openweixin")
public class WechatOpenController {
	Logger LOG = LoggerFactory.getLogger(WechatOpenController.class);
	@Autowired
	private OpenWeixinService openWeixinService;

	/**
	 * 获取第三方授权信息
	 * 
	 * @Title: getOpenWeixinAuthInfo
	 * @param @return 设定文件
	 * @return Result 返回类型
	 * @version 1.0
	 * @author Hogen.hu
	 */
	@RequestMapping(value = "/getOpenWeixinAuthInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getOpenWeixinAuthInfo(@RequestParam("authAppId") String authAppId) {
		LOG.info("查看是否已经授权过，传入参数authAppId=[{}]", authAppId);
		Result result = new BaseResult();
		OpenAuthorizationWeixin openAuthorizationWeixin = openWeixinService.getAuthorizerWeixinByAuthorizerAppid(authAppId);
		if (openAuthorizationWeixin == null) {
			result.setReturnCode(Constant.FAIL);
			result.setReturnMsg("未第三方授权");
		} else {
			result.setData(openAuthorizationWeixin);
			result.setReturnCode(Constant.SUCCESS);
			result.setReturnMsg("已经授权第三方");
		}
		return result;
	}
		
	/**
	 * 获取第三方授权引导页url
	 * @return
	 */
	@RequestMapping(value = "/getOpenWeixinAuthPage", method = RequestMethod.GET)
	@ResponseBody
	public Result getOpenWeixinAuthPage() {
		String authPageUrl = openWeixinService.getAuthLoginPageUrl();
		Result result = new BaseResult();
		if(StringUtils.isNotEmpty(authPageUrl)) {
			result.setData(authPageUrl);
			result.setReturnCode(Constant.SUCCESS);
		} else {
			result.setReturnMsg("获取第三方授权引导页地址失败!");
			result.setReturnCode(Constant.FAIL);
		}
		return result;
	}

	/**
	 * 第三方授权回调保存
	 * 
	 * @Title: callBackSaveOpenAuthorizationWeixin
	 * @param @param authorization_code
	 * @param @param channelId
	 * @param @return 设定文件
	 * @return Result 返回类型
	 * @version 1.0
	 * @author Hogen.hu
	 */
	@RequestMapping(value = "/callBackSaveOpenAuthorizationWeixin", method = { RequestMethod.POST })
	@ResponseBody
	public Result callBackSaveOpenAuthorizationWeixin(@RequestParam("authCode") String authCode) {
		LOG.info("第三方授权回调保存,传入参数,authCode=[{}]", authCode);
		Result result = new BaseResult();
		OpenAuthorizationWeixin oaw = new OpenAuthorizationWeixin();
		oaw.setAuthorization_code(authCode);
		try {
			openWeixinService.refreshAuthorizerWeixin(oaw);
			result.setReturnCode(Constant.SUCCESS);
			result.setReturnMsg("保存成功");
		} catch (Exception e) {
			result.setReturnCode(Constant.FAIL);
			result.setReturnMsg("保存失败");
		}
		return result;
	}

	/**
	 * 取消授权
	 * 
	 * @param authAppId
	 * @return
	 */
	@RequestMapping(value = "/cancelAuth", method = { RequestMethod.POST })
	@ResponseBody
	public Result cancelAuth(@RequestParam("authAppId") String authAppId) {
		LOG.info("取消三方授权,传入参数,authAppId=[{}]", authAppId);
		Result result = new BaseResult();
		openWeixinService.deleteAuthorizerWeixinByAuthorizeAppid(authAppId);
		result.setReturnCode(Constant.SUCCESS);
		result.setReturnMsg("取消授权成功!");
		return result;
	}
}