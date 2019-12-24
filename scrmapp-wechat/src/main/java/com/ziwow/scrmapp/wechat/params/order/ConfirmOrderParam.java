package com.ziwow.scrmapp.wechat.params.order;

import com.ziwow.scrmapp.wechat.params.common.CenterServiceParam;

import java.util.Arrays;
import java.util.List;

/**
 * 确认提交工单时接口
 * @author songkaiqi
 * @since 2019/12/24/上午10:15
 */
public class ConfirmOrderParam extends CenterServiceParam {

    private String orderNo;

    private Integer orderType;

    /** 产品列表pro1,pro2,.. */
    private String productIds;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<String> getProductIds() {
        return Arrays.asList(productIds.split(","));
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }
}
