package com.ziwow.scrmapp.wechat.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziwow.scrmapp.common.enums.Guarantee;

import java.util.Date;
import java.util.List;

/**
 * 延保卡
 * @author songkaiqi
 * @since 2019/06/12/下午5:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EwCard {


    /**
     * 主键
     */
    private Long id;

    /**
     * fansId
     */
    @JsonIgnore
    private Long fansId;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 使用状态  true 已使用   false 未使用
     */
    @JsonIgnore
    private Boolean useStatus;

    /**
     * 延保期限
     */
    private int validTime;

    /**
     * 购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchDate;

    /**
     * 保修期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date repairTerm;


    /**
     * 产品信息
     */
    private String productBarCodeTwenty;


    /**
     * 延保状态
     */
    private Guarantee guarantee;


    /**
     * 对应延保卡的信息
     * @return
     */
    private List<EwCardItems> ewCardItems;



    public String getGuaMessage(){
        return guarantee.getMessage();
    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    public String getProductBarCodeTwenty() {
        return productBarCodeTwenty;
    }

    public void setProductBarCodeTwenty(String productBarCodeTwenty) {
        this.productBarCodeTwenty = productBarCodeTwenty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFansId() {
        return fansId;
    }

    public void setFansId(Long fansId) {
        this.fansId = fansId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Boolean useStatus) {
        this.useStatus = useStatus;
    }

    public Date getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(Date purchDate) {
        this.purchDate = purchDate;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public Date getRepairTerm() {
        return this.repairTerm;
    }

    public void setRepairTerm(Date repairTerm) {
        this.repairTerm = repairTerm;
    }

    public List<EwCardItems> getEwCardItems() {
        return ewCardItems;
    }

    public void setEwCardItems(List<EwCardItems> ewCardItems) {
        this.ewCardItems = ewCardItems;
    }
}
