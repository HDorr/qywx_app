package com.ziwow.scrmapp.common.bean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 延保卡
 * @author songkaiqi
 * @since 2019/06/13/下午4:27
 */
public class EwCardParam {

    /**
     * 延保卡号
     */
    @JsonProperty("card_no")
    private String cardNo;

    /**
     * 产品条码
     */
    @JsonProperty("bar_code")
    private String barCode;

    /**
     * 用户手机号
     */
    @JsonProperty("mobile")
    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
