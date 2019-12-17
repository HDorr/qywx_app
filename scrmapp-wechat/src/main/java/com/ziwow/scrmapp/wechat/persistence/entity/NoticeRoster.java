package com.ziwow.scrmapp.wechat.persistence.entity;

import java.util.Date;

/**
 * 通知名单信息
 * @author songkaiqi
 * @since 2019/11/21/下午5:45
 */
public class NoticeRoster {

    private Long id;

    /**
     * 发放手机号
     */
    private String phone;

    /**
     * 发放时间
     */
    private Date sendTime;

    /**
     * 是否发放过
     */
    private Boolean send;

    /**
     * 是否处理过
     */
    private Boolean handle;

    /**
     * 处理时间
     */
    private Date handleTime;

    private Date createdAt;

    private Date updatedAt;

    /**
     * 分类类型
     */
    private String properType;

    /**
     * 产品型号
     */
    private String productCode;

    /**
     * 购买时间
     */
    private Date buyTime;

    private Boolean archive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }

    public Boolean getHandle() {
        return handle;
    }

    public void setHandle(Boolean handle) {
        this.handle = handle;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProperType() {
        return properType;
    }

    public void setProperType(String properType) {
        this.properType = properType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
}
