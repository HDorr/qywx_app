package com.ziwow.scrmapp.wechat.vo;

import java.util.Date;

/**
 * @author songkaiqi
 * @since 2019/07/11/下午7:44
 */
public class EwCards {

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 延保起始日期
     */
    private Date startTime;

    /**
     * 保修服务截止时间
     */
    private Date rairTerm;

    /**
     * 卡片属性 （年卡 ，二年卡）
     */
    private String cardAttributes;

    public String getCardAttributes() {
        return cardAttributes;
    }

    public void setCardAttributes(String cardAttributes) {
        this.cardAttributes = cardAttributes;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getRairTerm() {
        return rairTerm;
    }

    public void setRairTerm(Date rairTerm) {
        this.rairTerm = rairTerm;
    }
}
