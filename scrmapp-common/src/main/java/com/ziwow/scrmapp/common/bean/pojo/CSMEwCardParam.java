package com.ziwow.scrmapp.common.bean.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziwow.scrmapp.common.enums.CardEnum;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 注册延保卡 请求CSM参数
 * @author songkaiqi
 * @since 2019/06/10/下午5:47
 */
public class CSMEwCardParam {
    /**
     * 延保卡号
     */
    @JsonProperty("card_no")
    private String cardNo;

    /**
     * 卡类型
     */
    @JsonProperty("card_type")
    private Integer cardType = CardEnum.EXTENDED_WARRANTY_CARD.getCode();

    /**
     * 用户姓名
     */
    @JsonProperty("enduser_name")
    private String enduserName;

    /**
     * 产品编号
     */
    @JsonProperty("item_code")
    private String itemCode;

    /**
     * 产品型号
     */
    private String spec;

    /**
     * 产品条码
     */
    private String barcode;

    /**
     * 购买时间
     */
    @JsonProperty("purch_date")
    @JsonFormat(pattern = "yyyy-M-d")
    private Date purchDate;

    /**
     * 安装时间
     */
    @JsonProperty("install_time")
    private String installTime;

    /**
     * 省
     */
    @JsonProperty("province_name")
    private String provinceName;

    /**
     * 市
     */
    @JsonProperty("city_name")
    private String cityName;

    /**
     * 区
     */
    @JsonProperty("county_name")
    private String countyName;

    /**
     * 详细地址
     */
    @JsonProperty("enduser_address")
    private String enduserAddress;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 电话
     */
    private String tel;



    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getEnduserName() {
        return enduserName;
    }

    public void setEnduserName(String enduserName) {
        this.enduserName = enduserName;
    }



    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(Date purchDate) {
        this.purchDate = purchDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getEnduserAddress() {
        return enduserAddress;
    }

    public void setEnduserAddress(String enduserAddress) {
        this.enduserAddress = enduserAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "CSMEwCardParam{" +
                "cardNo='" + cardNo + '\'' +
                ", cardType=" + cardType +
                ", enduserName='" + enduserName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", spec='" + spec + '\'' +
                ", barcode='" + barcode + '\'' +
                ", purchDate=" + purchDate +
                ", installTime='" + installTime + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyName='" + countyName + '\'' +
                ", enduserAddress='" + enduserAddress + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
