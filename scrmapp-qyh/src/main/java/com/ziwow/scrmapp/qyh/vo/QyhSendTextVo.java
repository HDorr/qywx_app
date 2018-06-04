/**   
 * @Title: QyhSendMsgVo.java
 * @Package com.ziwow.scrmapppc.bean.vo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2017-3-28 下午3:54:09
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.vo;

/**
 * @ClassName: QyhSendTextVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 下午3:54:09
 * 
 */
public class QyhSendTextVo {

	/**
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	 */
	private String touser;

	/**
	 * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	private String toparty;

	/**
	 * 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	private String totag;
	/**
	 * 消息类型，此时固定为：news （不支持主页型应用）
	 */
	private String msgtype;
	/**
	 * 企业应用的id，整型。可在应用的设置页面查看
	 */
	private String agentid;

	/**
	 * 消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
	 */
	private String content;

	/**
	 * 表示是否是保密消息，0表示否，1表示是，默认0
	 */
	private Integer safe;

	/**
	 * @return the touser
	 */
	public String getTouser() {
		return touser;
	}

	/**
	 * @param touser
	 *            the touser to set
	 */
	public void setTouser(String touser) {
		this.touser = touser;
	}

	/**
	 * @return the toparty
	 */
	public String getToparty() {
		return toparty;
	}

	/**
	 * @param toparty
	 *            the toparty to set
	 */
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	/**
	 * @return the totag
	 */
	public String getTotag() {
		return totag;
	}

	/**
	 * @param totag
	 *            the totag to set
	 */
	public void setTotag(String totag) {
		this.totag = totag;
	}

	/**
	 * @return the msgtype
	 */
	public String getMsgtype() {
		return msgtype;
	}

	/**
	 * @param msgtype
	 *            the msgtype to set
	 */
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	/**
	 * @return the agentid
	 */
	public String getAgentid() {
		return agentid;
	}

	/**
	 * @param agentid
	 *            the agentid to set
	 */
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the safe
	 */
	public Integer getSafe() {
		return safe;
	}

	/**
	 * @param safe
	 *            the safe to set
	 */
	public void setSafe(Integer safe) {
		this.safe = safe;
	}

}
