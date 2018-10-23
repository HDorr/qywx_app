package com.ziwow.scrmapp.common.bean.vo;

import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;

public class WechatOrderVo extends WechatOrders {
    private Integer is_attitude;//服务态度
    private Integer is_specialty;//专业度
    private Integer is_integrity;//诚信情况
    private Integer is_recommend;//推荐意愿
    private Integer is_order;//工程师是否及时与您准时
    private Integer is_repair;//故障是否已经排除

    public Integer getIs_attitude() {
        return is_attitude;
    }

    public void setIs_attitude(Integer is_attitude) {
        this.is_attitude = is_attitude;
    }

    public Integer getIs_specialty() {
        return is_specialty;
    }

    public void setIs_specialty(Integer is_specialty) {
        this.is_specialty = is_specialty;
    }

    public Integer getIs_integrity() {
        return is_integrity;
    }

    public void setIs_integrity(Integer is_integrity) {
        this.is_integrity = is_integrity;
    }

    public Integer getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(Integer is_recommend) {
        this.is_recommend = is_recommend;
    }

    public Integer getIs_order() {
        return is_order;
    }

    public void setIs_order(Integer is_order) {
        this.is_order = is_order;
    }

    public Integer getIs_repair() {
        return is_repair;
    }

    public void setIs_repair(Integer is_repair) {
        this.is_repair = is_repair;
    }
}
