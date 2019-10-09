package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.EwCardSendTypeEnum;

/** 延保卡发放类型
 * @Author: created by madeyong
 * @CreateDate: 2019-10-09 10:09
 */
public class EwCardSendType {
    private String msg; // 发送文案
    private EwCardSendTypeEnum type; // 发送类型

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EwCardSendTypeEnum getType() {
        return type;
    }

    public void setType(EwCardSendTypeEnum type) {
        this.type = type;
    }
    public EwCardSendType(String msg,EwCardSendTypeEnum type){
        this.msg = msg;
        this.type = type;
    }
}
