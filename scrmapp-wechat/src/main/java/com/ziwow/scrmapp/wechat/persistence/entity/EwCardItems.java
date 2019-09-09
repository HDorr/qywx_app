package com.ziwow.scrmapp.wechat.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 延保卡类型
 * @author songkaiqi
 * @since 2019/06/24/下午6:55
 */
public class EwCardItems {

    @JsonIgnore
    private Long id;

    /**
     * 类型名称
     */
    private String itemName;

    /**
     * 类型编码
     */
    private String itemCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
