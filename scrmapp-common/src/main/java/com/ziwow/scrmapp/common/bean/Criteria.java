package com.ziwow.scrmapp.common.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 公用条件查询类
 */
public class Criteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 存放条件查询值
	 */
	private Map<String, Object> condition;

	/**
	 * 是否相异
	 */
	protected boolean distinct;

	/**
	 * 排序字段
	 */
	protected String orderByClause;

	private Integer mysqlOffset;

	private Integer mysqlLength;

	private Integer pageNum = 1;

	private Integer pageSize = 10;

	private boolean isPaging = true;

	protected Criteria(Criteria example) {
		this.orderByClause = example.orderByClause;
		this.condition = example.condition;
		this.distinct = example.distinct;
		this.mysqlLength = example.mysqlLength;
		this.mysqlOffset = example.mysqlOffset;
	}

	public Criteria() {
		condition = new HashMap<String, Object>();
	}

	public Criteria(BaseQueryBean bqlb) {
		this.pageNum = bqlb.getPageNum();
		this.pageSize = bqlb.getPageSize();
		this.orderByClause = bqlb.getOrderByClause();
		this.isPaging = bqlb.getPaged();
		condition = new HashMap<String, Object>();
	}

	public void clear() {
		condition.clear();
		orderByClause = null;
		distinct = false;
		this.mysqlOffset = null;
		this.mysqlLength = null;
		this.pageNum = 1;
		this.pageSize = 10;
	}

	/**
	 * @param condition
	 *            查询的条件名称
	 * @param value
	 *            查询的值
	 */
	public Criteria put(String condition, Object value) {
		this.condition.put(condition, value);
		return (Criteria) this;
	}

	/**
	 * @param orderByClause
	 *            排序字段
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * @param distinct
	 *            是否相异
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	/**
	 * @param mysqlOffset
	 *            指定返回记录行的偏移量<br>
	 *            mysqlOffset= 5,mysqlLength=10; // 检索记录行 6-15
	 */
	public void setMysqlOffset(Integer mysqlOffset) {
		this.mysqlOffset = mysqlOffset;
	}

	/**
	 * @param mysqlLength
	 *            指定返回记录行的最大数目<br>
	 *            mysqlOffset= 5,mysqlLength=10; // 检索记录行 6-15
	 */
	public void setMysqlLength(Integer mysqlLength) {
		this.mysqlLength = mysqlLength;
	}

	/**
	 * pageNum.
	 * 
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * pageNum.
	 * 
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * pageSize.
	 * 
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * pageSize.
	 * 
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * isPaging.
	 * 
	 * @return the isPaging
	 */
	public boolean isPaging() {
		return isPaging;
	}

	/**
	 * isPaging.
	 * 
	 * @param isPaging
	 *            the isPaging to set
	 */
	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

}