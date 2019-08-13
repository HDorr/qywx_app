package com.ziwow.scrmapp.common.enums;

import java.math.BigDecimal;

/**
 * 延保卡对应机器类型 例如 50g   400g
 * @author songkaiqi
 * @since 2019/08/08/下午5:10
 */
public enum  EwCardTypeEnum {

    FOUR_HUNDRED("400g",new BigDecimal(98)),

    FIFTY("50g",new BigDecimal(88)),
    ;

    EwCardTypeEnum(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    private String name;

    private BigDecimal price;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
