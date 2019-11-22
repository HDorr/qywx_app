package com.ziwow.scrmapp.common.bean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziwow.scrmapp.common.bean.vo.csm.DispatchDotProductVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

/**
 * 描述: 400派单给网点
 *
 * @Author: John
 * @Create: 2017-12-20 15:44
 */
public class DispatchDotParam implements Serializable {
    private static final long serialVersionUID = -1825087525524580226L;
    private String timeStamp;
    private String signture;
    private String acceptNumber;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String street;
    private String contacts;
    private String mobile;
    private String description;
    private String deptName;        // 预约描述
    private Integer dispatchType;
    private Integer ordersType;     // 预约类型（1.安装 2.维修 3.保养）
    private String ordersTime;      // 预约时间format（yyyy-MM-dd 12:00-14:00）
    // 产品相关
    private List<DispatchDotProductVo> products;

    /**
     * 商城订单号
     */
    @JsonProperty("net_sale_no")
    private String netSaleNo;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignture() {
        return signture;
    }

    public void setSignture(String signture) {
        this.signture = signture;
    }

    public String getAcceptNumber() {
        return acceptNumber;
    }

    public void setAcceptNumber(String acceptNumber) {
        this.acceptNumber = acceptNumber;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(Integer dispatchType) {
        this.dispatchType = dispatchType;
    }

    public List<DispatchDotProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<DispatchDotProductVo> products) {
        this.products = products;
    }

    public Integer getOrdersType() {
        return ordersType;
    }

    public void setOrdersType(Integer ordersType) {
        this.ordersType = ordersType;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getNetSaleNo() {
        return netSaleNo;
    }

    public void setNetSaleNo(String netSaleNo) {
        this.netSaleNo = netSaleNo;
    }
}