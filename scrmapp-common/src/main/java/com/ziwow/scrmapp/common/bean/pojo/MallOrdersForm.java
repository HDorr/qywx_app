package com.ziwow.scrmapp.common.bean.pojo;

import com.ziwow.scrmapp.common.bean.pojo.ext.WechatOrdersParamExt;

import java.util.List;

/**
 *  原单原回表单 （商城传服务号）
 * @author songkaiqi
 * @since 2019/08/30/下午4:18
 */
public class MallOrdersForm {

    /**
     * 微信订单号
     */
    private String orderNo;

    private String unionId;

    private List<WechatOrdersParamExt> forms;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public List<WechatOrdersParamExt> getForms() {
        return forms;
    }

    public void setForms(List<WechatOrdersParamExt> forms) {
        this.forms = forms;
    }
}
