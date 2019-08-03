package com.ziwow.scrmapp.common.exception;

/**
 * 第三方请求超时异常
 * @author songkaiqi
 * @since 2019/06/11/下午3:34
 */
public class ThirdException extends RuntimeException{

    public ThirdException(String message) {
        super(message);
    }

    public ThirdException(){

    }
    public ThirdException(Throwable t){
        super.initCause(t);
    }
    public ThirdException(String message,Throwable t){
        super(message);
        super.initCause(t);
    }
}
