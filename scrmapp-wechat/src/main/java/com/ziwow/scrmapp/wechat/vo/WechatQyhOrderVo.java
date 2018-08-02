package com.ziwow.scrmapp.wechat.vo;

import java.util.List;

/**
 * @Auther: yiyongchang
 * @Date: 18-7-29 下午3:36
 * @Description: 企业号预约单推送数据
 */
public class WechatQyhOrderVo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String orderCode; //受理单号 唯一
    private Integer orderType; //预约类型 1.安装 2.维修 3.保养
    private String orderTime; //预约时间
    private Integer orderStatus;// 订单状态 1.处理中(用户下单) 2.已取消 3.重新处理中 4.已接单(师傅已接单) 5.服务完成(服务工程师完工提交) 6.已结束(已评价)
    private String userName; //用户名
    private String userPhone; //用户手机
    private String province;//省
    private String city;//市
    private String district;//区
    private String address;//详细地址
    private String description; //安装描述
    private List<WechatQyhProductVo> productList; //产品列表

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WechatQyhProductVo> getProductList() {
        return productList;
    }

    public void setProductList(List<WechatQyhProductVo> productList) {
        this.productList = productList;
    }
}
