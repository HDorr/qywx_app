package com.ziwow.scrmapp.wechat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 服务记录
 * @author songkaiqi
 * @since 2019/06/14/上午8:57
 */
public class ServiceRecord {

    /**
     * 服务类型   1:安装   2:维修   3:保养
     */
    private Integer serviceType;

    /**
     * 服务信息   例如:  安装时间2018年5月28日
     */
    private String message;


    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
