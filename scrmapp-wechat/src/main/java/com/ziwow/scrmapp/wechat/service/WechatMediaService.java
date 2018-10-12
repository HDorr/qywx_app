/**   
* @Title: WechatMediaService.java
* @Package com.ziwow.scrmapp.wechat.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-4-25 上午10:01:17
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.service;

/**
 * @ClassName: WechatMediaService
 * @Description: 上传下载多媒体文件
 * @author hogen
 * @date 2017-4-25 上午10:01:17
 * 
 */
public interface WechatMediaService {

	public String downLoadMedia(String media_id);

  public String downLoadMediaForCallCenter(String mediaId);
}
