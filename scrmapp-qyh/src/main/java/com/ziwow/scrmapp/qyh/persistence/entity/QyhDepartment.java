package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;

public class QyhDepartment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父亲部门id。根部门为1
     */
    private Long parentid;

    
    /**
     * 企业号ID
     */
    private String corpid;
    
    
    /**
	 * @return the corpid
	 */
	public String getCorpid() {
		return corpid;
	}

	/**
	 * @param corpid the corpid to set
	 */
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	/**
     * 在父部门中的次序值。order值小的排序靠前。
     */
    private Long order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 父亲部门id。根部门为1
     */
    public Long getParentid() {
        return parentid;
    }

    /**
     * @param parentid 
	 *            父亲部门id。根部门为1
     */
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    /**
     * @return 在父部门中的次序值。order值小的排序靠前。
     */
    public Long getOrder() {
        return order;
    }

    /**
     * @param order 
	 *            在父部门中的次序值。order值小的排序靠前。
     */
    public void setOrder(Long order) {
        this.order = order;
    }
}