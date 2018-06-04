package com.ziwow.scrmapp.common.page;

import java.util.List;

/**
 * 
 * ClassName: PageInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-23 下午5:35:20 <br/>
 * 
 * @author Daniel.Wang
 * @version
 * @since JDK 1.6
 */
public class PageInfo<T> {
	private int pageNum;
	private int pageSize;
	private int startRow;
	private int endRow;
	private long total;
	private int pages;
	private List<T> list;

	public PageInfo(List list) {
		if (list instanceof Page) {
			Page page = (Page) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.startRow = page.getStartRow();
			this.endRow = page.getEndRow();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
