/**   
* @Title: Privilege.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-3 下午4:19:52
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.util.List;

/**
 * @ClassName: Privilege
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:19:52
 * 
 */
public class PrivilegeVo {

	 private int level;
     private List<Integer> allow_party;
     private List<Object>allow_user;
     private List<Object>allow_tag;
     private List<Object>extra_party;
     private List<Object>extra_user;
     private List<Object>extra_tag;
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the allow_party
	 */
	public List<Integer> getAllow_party() {
		return allow_party;
	}
	/**
	 * @param allow_party the allow_party to set
	 */
	public void setAllow_party(List<Integer> allow_party) {
		this.allow_party = allow_party;
	}
	/**
	 * @return the allow_user
	 */
	public List<Object> getAllow_user() {
		return allow_user;
	}
	/**
	 * @param allow_user the allow_user to set
	 */
	public void setAllow_user(List<Object> allow_user) {
		this.allow_user = allow_user;
	}
	/**
	 * @return the allow_tag
	 */
	public List<Object> getAllow_tag() {
		return allow_tag;
	}
	/**
	 * @param allow_tag the allow_tag to set
	 */
	public void setAllow_tag(List<Object> allow_tag) {
		this.allow_tag = allow_tag;
	}
	/**
	 * @return the extra_party
	 */
	public List<Object> getExtra_party() {
		return extra_party;
	}
	/**
	 * @param extra_party the extra_party to set
	 */
	public void setExtra_party(List<Object> extra_party) {
		this.extra_party = extra_party;
	}
	/**
	 * @return the extra_user
	 */
	public List<Object> getExtra_user() {
		return extra_user;
	}
	/**
	 * @param extra_user the extra_user to set
	 */
	public void setExtra_user(List<Object> extra_user) {
		this.extra_user = extra_user;
	}
	/**
	 * @return the extra_tag
	 */
	public List<Object> getExtra_tag() {
		return extra_tag;
	}
	/**
	 * @param extra_tag the extra_tag to set
	 */
	public void setExtra_tag(List<Object> extra_tag) {
		this.extra_tag = extra_tag;
	}
     
     
     
}
