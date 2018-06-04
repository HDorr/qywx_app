package com.ziwow.scrmapp.common.persistence.entity;

import java.io.Serializable;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-03-30 11:52
 */
public class SmsMarketing implements Serializable {

    private static final long serialVersionUID = 4917560471040092949L;

    Long id;
    String smsTitle;
    String smsContent;
    Integer orderType;
    Integer smsType;
    Integer originalType;
    String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getOriginalType() {
        return originalType;
    }

    public void setOriginalType(Integer originalType) {
        this.originalType = originalType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
