package com.ziwow.scrmapp.common.bean.vo.csm;

/**
 * csm状态
 * @author songkaiqi
 * @since 2019/06/09/下午4:57
 */
public class Status {

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
