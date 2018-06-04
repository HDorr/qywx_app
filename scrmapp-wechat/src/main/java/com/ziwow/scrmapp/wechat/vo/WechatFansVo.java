/**   
* @Title: WechatFansVo.java
* @Package com.ziwow.scrmapp.wechat.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-2-24 上午11:02:09
* @version V1.0   
*/
package com.ziwow.scrmapp.wechat.vo;


/**
 * @ClassName: WechatFansVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-2-24 上午11:02:09
 * 
 */
public class WechatFansVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private Integer code;//0:未关注,1:非会员,2:会员
	private String token;
	// 是会员时
	private String userId;
	private String headimgurl;
	private String nickName;
	private Integer gender;
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @return the headimgurl
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}
	/**
	 * @param headimgurl the headimgurl to set
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
}