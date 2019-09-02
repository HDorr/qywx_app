package com.ziwow.scrmapp.common.pagehelper;

/**
 * 分页参数
 *
 * @author: yyc
 * @date: 19-9-2 下午5:15
 */
public class Page {

  private long page = 1;

  private long size = 10;

  public Page() {}

  public Page(long page, long size) {
    if (page < 1 || size < 1) {
      throw new IllegalArgumentException("分页参数错误");
    }
    this.page = page;
    this.size = size;
  }

  public long getOffset() {
    return (page - 1) * size;
  }

  public long getLimit() {
    return page * size;
  }

  public long getPage() {
    return page;
  }

  public void setPage(long page) {
    if (page < 0) {
      throw new IllegalArgumentException("分页page错误");
    }
    this.page = page;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    if (size < 0) {
      throw new IllegalArgumentException("分页size错误");
    }
    this.size = size;
  }
}
