package com.ziwow.scrmapp.wechat.vo;

import java.util.Date;

/**
 * 根据卡号查询延保卡vo
 * @author songkaiqi
 * @since 2019/06/16/下午2:08
 */
public class EwCardInfo {

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 延保期限
     */
    private int validTime;

    /**
     * 对应机型
     */
    private String itemName;

    /**
     * 延保起始时间
     */
    private Date startDate;

    /**
     * 延保结束时间
     */
    private Date endDate;





    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
