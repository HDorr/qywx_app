/**   
* @Title: AddressBookVo.java
* @Package com.ziwow.qyhapp.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-6 下午4:07:19
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.util.List;

/**
 * @ClassName: AddressBookVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-6 下午4:07:19
 * 
 */
public class AddressBookVo {

	private int errcode;
	private String errmsg;
	private int next_seq;
	private int next_offset;
	private int is_last;
	List<AddreddBookDataVo> data;
	/**
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}
	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}
	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	/**
	 * @return the next_seq
	 */
	public int getNext_seq() {
		return next_seq;
	}
	/**
	 * @param next_seq the next_seq to set
	 */
	public void setNext_seq(int next_seq) {
		this.next_seq = next_seq;
	}
	/**
	 * @return the next_offset
	 */
	public int getNext_offset() {
		return next_offset;
	}
	/**
	 * @param next_offset the next_offset to set
	 */
	public void setNext_offset(int next_offset) {
		this.next_offset = next_offset;
	}
	/**
	 * @return the is_last
	 */
	public int getIs_last() {
		return is_last;
	}
	/**
	 * @param is_last the is_last to set
	 */
	public void setIs_last(int is_last) {
		this.is_last = is_last;
	}
	/**
	 * @return the data
	 */
	public List<AddreddBookDataVo> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<AddreddBookDataVo> data) {
		this.data = data;
	}
	
	
	
}
