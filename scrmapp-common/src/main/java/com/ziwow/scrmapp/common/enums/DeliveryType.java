package com.ziwow.scrmapp.common.enums;

/**
 *  订单发货类型
 * @author songkaiqi
 * @since 2019/08/29/下午3:20
 */
public enum  DeliveryType {

    NORMAL("普通受理单",4),
    YDYH("原单原回受理单",21),

    ;
    private String message;

    private Integer code;

    DeliveryType(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
