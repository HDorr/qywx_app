package com.ziwow.scrmapp.common.bean.vo.iot;

/**
 * @author songkaiqi
 * @since 2020/06/02/下午5:53
 */
public class IotBaseResult {

    private String message;

    private String code;

    private Boolean success;

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
