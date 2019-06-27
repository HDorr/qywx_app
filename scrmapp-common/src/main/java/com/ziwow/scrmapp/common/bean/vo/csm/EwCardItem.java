package com.ziwow.scrmapp.common.bean.vo.csm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 延保卡详情
 * @author songkaiqi
 * @since 2019/06/09/下午2:38
 */
public class EwCardItem {

    /**
     * 卡号
     */
    @JsonProperty("card_no")
    private String cardNo;

    /**
     * 使用状态
     */
    @JsonProperty("card_stat")
    private String cardStat;

    /**
     * 购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty("purch_date")
    private Date purchDate;

    /**
     * 对应机型
     */
    @JsonProperty("item_name")
    private String itemName;

    /**
     * 延保期限
     */
    @JsonProperty("valid_time")
    private int validTime;

    /**
     * 保修期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty("repair_term")
    private Date repairTerm;




    public Date getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(Date purchDate) {
        this.purchDate = purchDate;
    }

    public Date getRepairTerm() {
        return repairTerm;
    }

    public void setRepairTerm(Date repairTerm) {
        this.repairTerm = repairTerm;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardStat() {
        return cardStat;
    }

    public void setCardStat(String cardStat) {
        this.cardStat = cardStat;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }
}
