package com.ziwow.scrmapp.wechat.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ziwow.scrmapp.common.enums.EwCardStatus;
import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;
import com.ziwow.scrmapp.common.enums.Guarantee;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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


    /**
     * 延保卡状态
     */
    private EwCardStatus cardStatus;


    /**
     * 改延保卡绑定的产品是否有安装单
     */
    private Boolean installList;

    /**
     * 延保卡类型
     */
    private EwCardTypeEnum type;


    public EwCardTypeEnum getType() {
        return type;
    }

    public void setType(EwCardTypeEnum type) {
        this.type = type;
    }

    public Boolean getInstallList() {
        return installList;
    }

    public void setInstallList(Boolean installList) {
        this.installList = installList;
    }

    public EwCardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(EwCardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EwCard)) return false;

        EwCard ewCard = (EwCard) o;

        return new EqualsBuilder()
                .append(fansId, ewCard.fansId)
                .append(cardNo, ewCard.cardNo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fansId)
                .append(cardNo)
                .toHashCode();
    }
}
