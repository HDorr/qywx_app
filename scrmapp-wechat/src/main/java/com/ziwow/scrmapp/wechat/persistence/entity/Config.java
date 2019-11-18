package com.ziwow.scrmapp.wechat.persistence.entity;

import java.util.Date;

/**
 * 配置info
 * @author songkaiqi
 * @since 2019/11/18/下午4:19
 */
public class Config {

    private Long id;

    private String key;

    /**
     * 配置内容
     */
    private String content;

    /**
     * 是否具有时效性
     */
    private Boolean aginging;

    /**
     * 活动起始时间
     */
    private Date startDate;

    /**
     * 活动结束时间
     */
    private Date endDate;

    /**
     * 描述
     */
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAginging() {
        return aginging;
    }

    public void setAginging(Boolean aginging) {
        this.aginging = aginging;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
