package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

/**
 * 产品延保信息
 * @author songkaiqi
 * @since 2020/06/15/上午11:30
 */
public class ProductEwCardInfo {

    private String cardNo;

    private String cardType;

    private String validTime;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}
