/**   
* @Title: WxSaaSCallbackInfo.java
* @Package com.ziwow.scrmapp.weixin.util
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-11-29 上午9:35:02
* @version V1.0   
*/
package com.ziwow.scrmapp.weixin.util;

import java.io.Serializable;

/**
 * @ClassName: WxSaaSCallbackInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-29 上午9:35:02
 * 
 */
public class WxSaaSCallbackInfo implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//第三方回调协议
    private String SuiteId;
    private String InfoType;
    private String TimeStamp;
    private String SuiteTicket;
    private String AuthCorpId;
    private String AuthCode;
    private String Seq;
    
    
    private String ToUserName;
    private String Encrypt;
    private String AgentID;
    
    
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return Seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		Seq = seq;
	}
	/**
	 * @return the suiteId
	 */
	public String getSuiteId() {
		return SuiteId;
	}
	/**
	 * @param suiteId the suiteId to set
	 */
	public void setSuiteId(String suiteId) {
		SuiteId = suiteId;
	}
	/**
	 * @return the infoType
	 */
	public String getInfoType() {
		return InfoType;
	}
	/**
	 * @param infoType the infoType to set
	 */
	public void setInfoType(String infoType) {
		InfoType = infoType;
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return TimeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	/**
	 * @return the suiteTicket
	 */
	public String getSuiteTicket() {
		return SuiteTicket;
	}
	/**
	 * @param suiteTicket the suiteTicket to set
	 */
	public void setSuiteTicket(String suiteTicket) {
		SuiteTicket = suiteTicket;
	}
	/**
	 * @return the authCorpId
	 */
	public String getAuthCorpId() {
		return AuthCorpId;
	}
	/**
	 * @param authCorpId the authCorpId to set
	 */
	public void setAuthCorpId(String authCorpId) {
		AuthCorpId = authCorpId;
	}
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return AuthCode;
	}
	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		AuthCode = authCode;
	}
	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return ToUserName;
	}
	/**
	 * @param toUserName the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	/**
	 * @return the encrypt
	 */
	public String getEncrypt() {
		return Encrypt;
	}
	/**
	 * @param encrypt the encrypt to set
	 */
	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
	/**
	 * @return the agentID
	 */
	public String getAgentID() {
		return AgentID;
	}
	/**
	 * @param agentID the agentID to set
	 */
	public void setAgentID(String agentID) {
		AgentID = agentID;
	}
    
    
}
