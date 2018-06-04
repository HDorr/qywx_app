/**   
* @Title: WechatMediaController.java
* @Package com.ziwow.scrmapp.wechat.controller
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-4-25 上午10:17:18
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.service.WechatMediaService;

/**
 * @ClassName: WechatMediaController
 * @author hogen
 * @date 2017-4-25 上午10:17:18
 * 
 */
@Controller
@RequestMapping("/weixin/madia")
public class WechatMediaController{

	private static final Logger logger = LoggerFactory.getLogger(WechatMediaController.class);
	
	@Resource
	private WechatMediaService wechatMediaService;
	
	/**
	 * 
	* @Title: downLoadWechatMedia
	* @Description: 通过media_id下载图片
	* @param @param media_id
	* @param @return    设定文件
	* @return Result    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value = "/downLoadWechatMedia", method = RequestMethod.GET)
	@ResponseBody
	public Result downLoadWechatMedia(@RequestParam("media_id") String media_id) {
		logger.info("通过media_id下载图片，media_id=[{}]",media_id);
		Result result = new BaseResult();
		int code=Constant.SUCCESS;
		String msg = "操作成功";
		String res =wechatMediaService.downLoadMedia(media_id);
		if(StringUtil.isNotBlank(res)){
			try{
				JSONObject jObj =JSONObject.fromObject(res);
				code = jObj.getInt("errcode");
				msg = jObj.getString("errmsg");
			}catch (Exception e) {
				result.setData(res);
			}
		}
		
		result.setReturnCode(code);
		result.setReturnMsg(msg);
		return result;
	}
}
