package com.ziwow.scrmapp.common.advice;

import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.exception.ThirdException;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.ConnectException;


/**
 * 全局异常捕获
 * @author songkaiqi
 * @since 2019/06/11/下午3:01
 */
@ControllerAdvice
public class ApiControllerExceptionHandler {


    /**
     * 请求第三方超时异常处理
     * @return
     */
    @ExceptionHandler({ThirdException.class, ConnectException.class})
    @ResponseBody
    public Result handleTimeOutException(ThirdException e){
        Result result = new BaseResult();
        result.setReturnMsg("请求超时，请稍后再试");
        result.setReturnCode(Constant.FAIL);
        return result;
    }



}
