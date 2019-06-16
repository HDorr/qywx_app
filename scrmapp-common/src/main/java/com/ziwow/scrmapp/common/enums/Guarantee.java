package com.ziwow.scrmapp.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 保修状态
 * @author songkaiqi
 * @since 2019/06/13/下午6:06
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Guarantee {
    NORMAL_GUARANTEE("正常质保中"),
    EXTEND_GUARANTEE("延保质保中"),
    GUARANTEE_END("质保到期")
    ;
    private String message;

    Guarantee(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
