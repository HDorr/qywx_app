/**   
* @Title: DepartmentVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-6 下午4:12:00
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

/**
 * @ClassName: DepartmentVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-6 下午4:12:00
 * 
 */
public class DepartmentVo {

	 private int id;
     private String name;
     private int parentid;
     private int order;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the parentid
	 */
	public int getParentid() {
		return parentid;
	}
	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
     
     
     
}
