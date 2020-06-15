package com.ziwow.scrmapp.wechat.vo;

import java.util.Date;
import java.util.List;

/**
 * 产品延保信息
 * @author songkaiqi
 * @since 2020/06/12/下午5:06
 */
public class ProductEwInfo {

    /**
     * 产品条码
     */
    private String barcode;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品型号
     */
    private String modelName;

    /**
     * 延保起始时间
     */
    private Date ewBeginDate;

    /**
     * 延保结束时间
     */
    private Date ewEndDate;

    /**
     * 相关延保信息
     */
    private List<ProductEwCardInfo> ewCardInfos;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getEwBeginDate() {
        return ewBeginDate;
    }

    public void setEwBeginDate(Date ewBeginDate) {
        this.ewBeginDate = ewBeginDate;
    }

    public Date getEwEndDate() {
        return ewEndDate;
    }

    public void setEwEndDate(Date ewEndDate) {
        this.ewEndDate = ewEndDate;
    }

    public List<ProductEwCardInfo> getEwCardInfos() {
        return ewCardInfos;
    }

    public void setEwCardInfos(List<ProductEwCardInfo> ewCardInfos) {
        this.ewCardInfos = ewCardInfos;
    }
}
