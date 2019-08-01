package com.ziwow.scrmapp.common.bean.vo.csm;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 购买时间项
 * @author songkaiqi
 * @since 2019/07/31/下午6:42
 */
public class PurchDateItems {

    @JsonProperty("purch_date")
    private String purchDate;

    public String getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(String purchDate) {
        this.purchDate = purchDate;
    }
}
