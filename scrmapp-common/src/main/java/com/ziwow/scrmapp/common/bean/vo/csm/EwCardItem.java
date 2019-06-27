package com.ziwow.scrmapp.common.bean.vo.csm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 延保卡详情
 * @author songkaiqi
 * @since 2019/06/09/下午2:38
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 对应编码
     */
    @JsonProperty("item_code")
    private String itemCode;


    /**
     * 延保期限
     */
    @JsonProperty("valid_time")
    private int validTime;


    /**
     * 获取拆分后的结果
     * @return
     */
    public String[] getItemNames(){
        return this.itemName.split(",");
    }

    /**
     * 获取拆分后的结果
     * @return
     */
    public String[] getItemCodes(){
        return this.itemCode.split(",");
    }



    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public Date getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(Date purchDate) {
        this.purchDate = purchDate;
    }
}
