package com.ziwow.scrmapp.common.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2018-03-30 11:52
 */
public class SmsSendRecord implements Serializable {

    private static final long serialVersionUID = 4917560471040092949L;
    Long id;
    String mobile;
    Date syncTime;
    Integer orderType;
    Date registerTime;
    Date sendTime;
    Integer sendCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }
}
