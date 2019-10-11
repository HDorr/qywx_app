package com.ziwow.scrmapp.common.result;

import com.ziwow.scrmapp.common.constants.Constant;

/**
 * 结果返回
 *
 * @author: yyc
 * @date: 19-8-31 下午3:45
 */
public class ResultHelper {

  public static <T> Result success(T data) {
    return success(data, Constant.SUCCESS_MSG);
  }

  public static <T> Result success(T data, String msg) {
    return BaseResult.build(Constant.SUCCESS, msg, data);
  }

  public static <T> Result error(T data) {
    return error(data, Constant.ERROR_MSG);
  }

  public static <T> Result error(T data, String msg) {
    return BaseResult.build(Constant.FAIL, msg, data);
  }
}
