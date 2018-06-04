package com.ziwow.scrmapp.common.bean;

import java.io.Serializable;

public class BaseQueryBean implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageNum = 1; // 当前页数
	private Integer pageSize = 10; // 每页条数
	private Boolean paged = true; // 是否分页
	private String orderByClause; // 排序

	/**
	 * Creates a new instance of BaseQueryListBean.
	 * 
	 */

	public BaseQueryBean() {
	}

	/**
	 * Creates a new instance of BaseQueryListBean.
	 * 
	 * @param pageNum
	 * @param pageSize
	 */
	public BaseQueryBean(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	/**
	 * Creates a new instance of BaseQueryListBean.
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param orderByClause
	 */
	public BaseQueryBean(Integer pageNum, Integer pageSize, String orderByClause) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.orderByClause = orderByClause;
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
	 * paged.
	 * 
	 * @return the paged
	 */
	public Boolean getPaged() {
		return paged;
	}

	/**
	 * paged.
	 * 
	 * @param paged
	 *            the paged to set
	 */
	public void setPaged(Boolean paged) {
		this.paged = paged;
	}

	/**
	 * orderByClause.
	 * 
	 * @return the orderByClause
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * orderByClause.
	 * 
	 * @param orderByClause
	 *            the orderByClause to set
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

}
