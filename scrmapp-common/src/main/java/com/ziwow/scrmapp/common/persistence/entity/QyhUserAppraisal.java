package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;

public class QyhUserAppraisal {
    private Long id;

    private Long orderId;

    private BigDecimal attitude;

    private BigDecimal profession;

    private BigDecimal integrity;

    private BigDecimal recommend;

    private String content;

    private String userId;

    private String qyhUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAttitude() {
        return attitude;
    }

    public void setAttitude(BigDecimal attitude) {
        this.attitude = attitude;
    }

    public BigDecimal getProfession() {
        return profession;
    }

    public void setProfession(BigDecimal profession) {
        this.profession = profession;
    }

    public BigDecimal getIntegrity() {
        return integrity;
    }

    public void setIntegrity(BigDecimal integrity) {
        this.integrity = integrity;
    }

    public BigDecimal getRecommend() {
        return recommend;
    }

    public void setRecommend(BigDecimal recommend) {
        this.recommend = recommend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQyhUserId() {
        return qyhUserId;
    }

    public void setQyhUserId(String qyhUserId) {
        this.qyhUserId = qyhUserId;
    }
}