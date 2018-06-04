/**   
* @Title: AuthInfoVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-11-30 下午4:01:34
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AuthInfoVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-30 下午4:01:34
 * 
 */
public class AuthInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private boolean is_new_auth;
      
      private List<AgentVo> agent;

      private List<Object> department;
      
	/**
	 * @return the department
	 */
	public List<Object> getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(List<Object> department) {
		this.department = department;
	}

	/**
	 * @return the is_new_auth
	 */
	public boolean isIs_new_auth() {
		return is_new_auth;
	}

	/**
	 * @param is_new_auth the is_new_auth to set
	 */
	public void setIs_new_auth(boolean is_new_auth) {
		this.is_new_auth = is_new_auth;
	}

	/**
	 * @return the agent
	 */
	public List<AgentVo> getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(List<AgentVo> agent) {
		this.agent = agent;
	}

	
	
}
