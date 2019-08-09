package com.ziwow.scrmapp.common.enums;

/**
 * 延保卡对应机器类型 例如 50g   400g
 * @author songkaiqi
 * @since 2019/08/08/下午5:10
 */
public enum  EwCardTypeEnum {

    FOUR_HUNDRED("400g"),

    FIFTY("50g"),
    ;

    EwCardTypeEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
