package com.ziwow.scrmapp.common.bean.pojo;

import java.io.Serializable;

public class AppraiseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderCode;//受理单号
    private String profession;//专业技能 1-5
    private String attitude;//服务态度 1-5
    private String content;//评价内容 (非必须)
    private String is_repair;//如果是维修单，是否维修好(维修单特有字段) 1是,0否
    private String is_order;//是否准时预约 1是,0否

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_repair() {
        return is_repair;
    }

    public void setIs_repair(String is_repair) {
        this.is_repair = is_repair;
    }

    public String getIs_order() {
        return is_order;
    }

    public void setIs_order(String is_order) {
        this.is_order = is_order;
    }
}
