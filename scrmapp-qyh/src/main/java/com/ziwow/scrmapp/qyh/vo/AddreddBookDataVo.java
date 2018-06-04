/**   
* @Title: AddreddBookDataVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-6 下午4:09:51
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhDepartment;

/**
 * @ClassName: AddreddBookDataVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-6 下午4:09:51
 * 
 */
public class AddreddBookDataVo {

	private int type;
	private QyhUser user;
	private QyhDepartment department;
	private int department_id;
	private String userid;
	
	
	
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the department_id
	 */
	public int getDepartment_id() {
		return department_id;
	}
	/**
	 * @param department_id the department_id to set
	 */
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	public QyhUser getUser() {
		return user;
	}
	public void setUser(QyhUser user) {
		this.user = user;
	}
	public QyhDepartment getDepartment() {
		return department;
	}
	public void setDepartment(QyhDepartment department) {
		this.department = department;
	}
}
