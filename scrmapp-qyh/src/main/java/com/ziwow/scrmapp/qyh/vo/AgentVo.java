/**   
* @Title: AgentVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-3 下午4:03:15
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.util.List;


/**
 * @ClassName: AgentVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:03:15
 * 
 */
public class AgentVo {

	 private String agentid;
     private String name;
     private String square_logo_url;
     private String round_logo_url;
     private String appid;
     private List<String> api_group;
     private PrivilegeVo privilegeVo;
     
	/**
	 * @return the privilege
	 */
	public PrivilegeVo getPrivilege() {
		return privilegeVo;
	}

	/**
	 * @param privilegeVo the privilege to set
	 */
	public void setPrivilege(PrivilegeVo privilegeVo) {
		this.privilegeVo = privilegeVo;
	}

	/**
	 * @return the api_group
	 */
	public List<String> getApi_group() {
		return api_group;
	}

	/**
	 * @param api_group the api_group to set
	 */
	public void setApi_group(List<String> api_group) {
		this.api_group = api_group;
	}

	/**
	 * @return the agentid
	 */
	public String getAgentid() {
		return agentid;
	}

	/**
	 * @param agentid the agentid to set
	 */
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the square_logo_url
	 */
	public String getSquare_logo_url() {
		return square_logo_url;
	}

	/**
	 * @param square_logo_url the square_logo_url to set
	 */
	public void setSquare_logo_url(String square_logo_url) {
		this.square_logo_url = square_logo_url;
	}

	/**
	 * @return the round_logo_url
	 */
	public String getRound_logo_url() {
		return round_logo_url;
	}

	/**
	 * @param round_logo_url the round_logo_url to set
	 */
	public void setRound_logo_url(String round_logo_url) {
		this.round_logo_url = round_logo_url;
	}

	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the privilegeVo
	 */
	public PrivilegeVo getPrivilegeVo() {
		return privilegeVo;
	}

	/**
	 * @param privilegeVo the privilegeVo to set
	 */
	public void setPrivilegeVo(PrivilegeVo privilegeVo) {
		this.privilegeVo = privilegeVo;
	}
	
	
     
}
