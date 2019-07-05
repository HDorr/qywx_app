package com.ziwow.scrmapp.wechat.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ziwow.scrmapp.common.enums.Guarantee;
import com.ziwow.scrmapp.wechat.persistence.entity.EwCard;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 *  保修卡详情信息
 * @author songkaiqi
 * @since 2019/06/14/上午8:53
 */
public class EwCardDetails {

    /**
     * 商品图片
     */
    private String productImg;

    /**
     * 商品名称
     */
    private String productName;


    /**
     * 产品条码
     */
    private String barCode;

    /**
     * 产品型号
     */
    private String itemName;

    /**
     * 质保状态信息
     */
    private Guarantee guarantee;

    /**
     * 保修服务截止时间
     */
    private Date rairTerm;


    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 延保起始日期
     */
    private Date startTime;

    /**
     * 购买时间
     */
    private Date buyTime;

    /**
     * 服务记录
     */
    private List<ServiceRecord> serviceRecords;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 正常保修截止日期
     */
    @JsonFormat(pattern = "yyyy/M/d",timezone="GMT+8")
    private Date normal;


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGuarantee() {
        return guarantee.getMessage();
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }


    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public List<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(List<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
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

    public Date getNormal() {
        return normal;
    }

    public void setNormal(Date normal) {
        this.normal = normal;
    }
}
