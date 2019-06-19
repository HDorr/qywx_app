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

//    @JsonProperty("product_bar_code")
//    private String productBarCode;



    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


}
