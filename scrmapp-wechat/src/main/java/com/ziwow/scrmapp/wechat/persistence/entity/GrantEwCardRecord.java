package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.EwCardTypeEnum;

/**
 * 发放延保卡记录
 * @author songkaiqi
 * @since 2019/08/12/上午10:02
 */
public class GrantEwCardRecord {

    private String id;

    /**
     * 发放的手机号
     */
    private String phone;

    /**
     * 发放的延保卡类型
     */
    private EwCardTypeEnum type;

    /**
     * 是否发放 true:已发放
     */
    private Boolean send;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EwCardTypeEnum getType() {
        return type;
    }

    public void setType(EwCardTypeEnum type) {
        this.type = type;
    }

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }
}
