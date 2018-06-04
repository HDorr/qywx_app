package com.ziwow.scrmapp.qyh.vo;

import java.io.Serializable;

/**
 * @ClassName: WxSaaSCallbackInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-29 上午9:35:02
 * 
 */
public class WxSaaSCallbackInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 第三方回调协议
	private String SuiteId;
	private String InfoType;
	private String TimeStamp;
	private String SuiteTicket;
	private String AuthCorpId;
	private String AuthCode;
	private String Seq;
	private String FromUserName;
	private String ToUserName;
	private String Encrypt;
	private String AgentID;
	private String ChangeType;
	private String WxPlugin_Status;
	private String Event;
	private String Status;
	//用户信息
	private String UserID;
	private String Name;
	private String Department;
	private String Mobile;
	private String Position;
	private String Gender;
	private String Email;
	private String Avatar;

	private String Id;
	private String ParentId;
	private String Order;

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return Seq;
	}

	/**
	 * @param seq
	 *            the seq to set
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
	 * @param suiteId
	 *            the suiteId to set
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
	 * @param infoType
	 *            the infoType to set
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
	 * @param timeStamp
	 *            the timeStamp to set
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
	 * @param suiteTicket
	 *            the suiteTicket to set
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
	 * @param authCorpId
	 *            the authCorpId to set
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
	 * @param authCode
	 *            the authCode to set
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
	 * @param toUserName
	 *            the toUserName to set
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
	 * @param encrypt
	 *            the encrypt to set
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
	 * @param agentID
	 *            the agentID to set
	 */
	public void setAgentID(String agentID) {
		AgentID = agentID;
	}

	public String getChangeType() {
		return ChangeType;
	}

	public void setChangeType(String changeType) {
		ChangeType = changeType;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId;
	}

	public String getOrder() {
		return Order;
	}

	public void setOrder(String order) {
		Order = order;
	}

	public String getWxPlugin_Status() {
		return WxPlugin_Status;
	}

	public void setWxPlugin_Status(String wxPlugin_Status) {
		WxPlugin_Status = wxPlugin_Status;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
}