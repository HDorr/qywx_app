/**   
* @Title: WechatMediaServiceImpl.java
* @Package com.ziwow.scrmapp.wechat.service.impl
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-4-25 上午10:01:31
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.utils.Attachment;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.tools.oss.OSSUtil;
import com.ziwow.scrmapp.wechat.constants.WeChatConstants;
import com.ziwow.scrmapp.wechat.service.WechatMediaService;
import com.ziwow.scrmapp.wechat.service.WeiXinService;

/**
 * @ClassName: WechatMediaServiceImpl
 * @Description: 上传下载多媒体文件
 * @author hogen
 * @date 2017-4-25 上午10:01:31
 * 
 */
@Service
public class WechatMediaServiceImpl implements WechatMediaService{

	private static final Logger logger = LoggerFactory.getLogger(WechatMediaServiceImpl.class);
	
	@Resource
	private WeiXinService weiXinService;
	
	
	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.appSecret}")
	private String secret;
	
	@Override
	public String downLoadMedia(String media_id) {
		String url = WeChatConstants.WECHAT_MADIA_DOWNLOAD.replace("ACCESS_TOKEN", weiXinService.getAccessToken(appid, secret))
				.replace("MEDIA_ID", media_id);
		
		try{
			Attachment res = HttpKit.download(url);
			logger.info("下载图片，返回结果[{}]",JSON.toJSON(res));
			if(res !=null && res.getError()==null){
				String resUrl = OSSUtil.uploadFile(res.getFileStream(), res.getSuffix());
				return resUrl;
			}else{
				return res.getError();
			}
			
		}catch (Exception e) {
			logger.error("下载图片失败,[{}]",e.getMessage());
		}
		return null;
	}

}
