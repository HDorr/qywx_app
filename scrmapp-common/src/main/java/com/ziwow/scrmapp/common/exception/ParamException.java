package com.ziwow.scrmapp.common.exception;

/**
 * 描述: 系统异常类
 *
 * @Author: John
 * @Create: 2017-12-20 17:53
 */
public class ParamException extends RuntimeException {
    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }


}
