package com.ziwow.scrmapp.common.enums;

/**
 * 系统相关异常枚举
 *
 * @author: yyc
 * @date: 19-3-30 下午4:59
 */
public enum GlobalErrorCode {
  SUCCESS(1, "成功"),
  INTERNAL_ERROR(-1, "系统繁忙,请稍后再试");

  GlobalErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  /** 错误码 */
  private int code;

  /** 错误信息 */
  private String message;

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
