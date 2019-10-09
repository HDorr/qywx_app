package com.ziwow.scrmapp.common.enums;

/** 延保卡发放类型
 * @Author: created by madeyong
 * @CreateDate: 2019-10-09 09:53
 */
public enum EwCardSendTypeEnum {
    SMS("普通"),

    LIVESTREMING("直播");

    private String str;

    EwCardSendTypeEnum(String str){
        this.str = str;
    }
    public String getStr() {
        return str;
    }
}
