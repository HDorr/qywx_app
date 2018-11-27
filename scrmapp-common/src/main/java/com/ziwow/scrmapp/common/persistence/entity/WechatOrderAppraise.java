package com.ziwow.scrmapp.common.persistence.entity;

import java.io.Serializable;

/**
 * @Auther: yiyongchang
 * @Date: 18-9-4 下午4:35
 * @Description: 评价参数
 */
public class WechatOrderAppraise implements Serializable {
    private static final long serialVersionUID = 1L;
    private String attitude;//服务礼仪 1 - 5
    private String profession;//专业技能1 - 5
    private String order;//是否及时预约并准时上门 1 是，0 否
    private String repair;//是否完全排除故障 1 是 ，0 否
    private String content;//评价内容
    private String orderCode;//评价对应的订单
    private String appraiseType;//评价对应订单的类型

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAppraiseType() {
        return appraiseType;
    }

    public void setAppraiseType(String appraiseType) {
        this.appraiseType = appraiseType;
    }
}
