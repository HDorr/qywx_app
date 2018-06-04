/**   
* @Title: WxAppService.java
* @Package com.ziwow.wxapp.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-1-11 下午3:37:11
* @version V1.0   
*/
package com.ziwow.scrmapp.miniapp.service;

import com.ziwow.scrmapp.miniapp.vo.SessionKeyVo;

/**
 * @ClassName: WxAppService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-1-11 下午3:37:11
 * 
 */
public interface WxAppService {

	/**
	 * 获取access_token
	* @Title: getAccessToken
	* @param @param appid  第三方用户唯一凭证
	* @param @param secret 第三方用户唯一凭证密钥，即appsecret
	* @param @return    设定文件
	* @return String    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public String getAccessToken(String appid,String secret);
	
	/**
	 * code 换取 session_key
	* @Title: getSessionKey
	* @param @param appid 小程序唯一标识
	* @param @param secret 小程序的 app secret
	* @param @param js_code 登录时获取的 code
	* @param @return    设定文件
	* @return SessionKeyVo    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public SessionKeyVo getSessionKey(String appid,String secret,String js_code);
	
	
	/**
	 *  缓存微信openId和session_key
	* @Title: create3rdSession
	* @param @param wxOpenId      微信用户唯一标识
	* @param @param wxSessionKey  微信服务器会话密钥
	* @param @param expires       会话有效期, 以秒为单位, 例如2592000代表会话有效期为30天
	* @param @return    设定文件
	* @return String    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public String create3rdSession(SessionKeyVo sessionKeyVo);
}
