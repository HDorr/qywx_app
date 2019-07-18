package com.ziwow.scrmapp.common.enums;

/**
 * 延保卡状态
 * @author songkaiqi
 * @since 2019/07/18/上午10:47
 */
public enum EwCardStatus {

    /** 待审核 */
    TO_BE_AUDITED("待审核",0),
    /** 已生效 */
    ENTERED_INTO_FORCE("已生效",1),
    /** 审核失败 */
    AUDIT_FAILURE("审核失败",2)

    ;
    private String message;

    private Integer code;

    EwCardStatus(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }}
