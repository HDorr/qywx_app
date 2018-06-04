package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;

public class QyhNoticeType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类编号
     */
    private Integer assortmentId;

    /**
     * 分类名称
     */
    private String assortmentName;

    /**
     * 分类URL
     */
    private String assortmentUrl;

    /**
     * 分类父级编号
     */
    private Integer assortmentParentId;

    /**
     * 分类等级  1: 一级分类 ,2:二级分类
     */
    private Integer assortmentLevel;

    /**
     * @return 分类编号
     */
    public Integer getAssortmentId() {
        return assortmentId;
    }

    /**
     * @param assortmentId 
	 *            分类编号
     */
    public void setAssortmentId(Integer assortmentId) {
        this.assortmentId = assortmentId;
    }

    /**
     * @return 分类名称
     */
    public String getAssortmentName() {
        return assortmentName;
    }

    /**
     * @param assortmentName 
	 *            分类名称
     */
    public void setAssortmentName(String assortmentName) {
        this.assortmentName = assortmentName;
    }

    /**
     * @return 分类URL
     */
    public String getAssortmentUrl() {
        return assortmentUrl;
    }

    /**
     * @param assortmentUrl 
	 *            分类URL
     */
    public void setAssortmentUrl(String assortmentUrl) {
        this.assortmentUrl = assortmentUrl;
    }

    /**
     * @return 分类父级编号
     */
    public Integer getAssortmentParentId() {
        return assortmentParentId;
    }

    /**
     * @param assortmentParentId 
	 *            分类父级编号
     */
    public void setAssortmentParentId(Integer assortmentParentId) {
        this.assortmentParentId = assortmentParentId;
    }

    /**
     * @return 分类等级  1: 一级分类 ,2:二级分类
     */
    public Integer getAssortmentLevel() {
        return assortmentLevel;
    }

    /**
     * @param assortmentLevel 
	 *            分类等级  1: 一级分类 ,2:二级分类
     */
    public void setAssortmentLevel(Integer assortmentLevel) {
        this.assortmentLevel = assortmentLevel;
    }
}