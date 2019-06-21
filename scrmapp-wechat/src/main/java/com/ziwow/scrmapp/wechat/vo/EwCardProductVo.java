package com.ziwow.scrmapp.wechat.vo;

import java.util.Date;

/**
 * 延保卡模块 -- 选择产品
 * @author songkaiqi
 * @since 2019/06/21/下午2:49
 */
public class EwCardProductVo {

    /** 产品条码 */
    private String barCode;

    /** 产品名称 */
    private String productName;

    /** 起始时间 */
    private Date startDate;

    /** 结束时间 */
    private Date endDate;


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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
}
