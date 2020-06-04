package com.ziwow.scrmapp.wechat.schedule.iot.dto;

import java.util.Date;

/**
 * 更换滤芯提醒所需值
 * @author songkaiqi
 * @since 2020/06/02/上午11:02
 */
public class IotFilterReminder {

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户UnionId
     */
    private String unionId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品型号
     */
    private String modelName;

    /**
     * 安装时间
     */
    private Date installDate;

    /**
     * 购买时间
     */
    private Date buyDate;

    /**
     * 滤芯型号
     */
    private String filterType;

    /**
     * 滤芯名称
     */
    private String filterName;

    /**
     * 产品条码信息
     */
    private String sncode;

    /**
     * 滤芯到期时间
     */
    private Date overdueDate;

    public Date getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
