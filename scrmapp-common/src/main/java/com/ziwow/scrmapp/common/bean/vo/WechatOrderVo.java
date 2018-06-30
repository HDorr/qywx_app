package com.ziwow.scrmapp.common.bean.vo;

import com.ziwow.scrmapp.common.persistence.entity.WechatOrders;

public class WechatOrderVo extends WechatOrders {
    private Integer is_attitude;//服务态度
    private Integer is_specialty;//专业度
    private Integer is_integrity;//诚信情况
    private Integer is_recommend;//推荐意愿

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
}
