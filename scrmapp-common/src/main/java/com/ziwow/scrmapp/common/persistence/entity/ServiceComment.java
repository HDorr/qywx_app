package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

/**用于用商城传输评论详情
 * @Author: created by madeyong
 * @CreateDate: 2019-11-14 17:10
 */
public class ServiceComment {
    private BigDecimal attitude; // 专业度星级
    private BigDecimal profession; // 服务态度星级
    private String content; // 评论内容
    private Date orderTime; // 工单预约时间

    public ServiceComment(BigDecimal attitude, BigDecimal profession, String content, Date orderTime){
        this.attitude = attitude;
        this.profession = profession;
        this.content = content;
        this.orderTime = orderTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
