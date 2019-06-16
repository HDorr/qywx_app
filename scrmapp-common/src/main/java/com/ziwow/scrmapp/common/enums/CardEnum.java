package com.ziwow.scrmapp.common.enums;

/**
 * 卡片类型
 * @author songkaiqi
 * @since 2019/06/10/下午5:49
 */
public enum CardEnum {
    FILTER_PACKAGE_CARD(1,"滤芯套餐卡"),
    EXTENDED_WARRANTY_CARD(2,"延保卡"),
    CLEANING_CARD(3,"清洗卡"),
    VIP_MEMBERSHIP_CARD(4,"vip会员卡")
    ;
    CardEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    /**
     * 类型编码
     */
    private Integer code;

    /**
     * 类型名称
     */
    private String name;


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
