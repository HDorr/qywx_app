package com.ziwow.scrmapp.common.response;

import com.ziwow.scrmapp.common.enums.GlobalErrorCode;

/**
 * 接口通用返回数据
 *
 * @author: yyc
 * @date: 19-3-31 上午1:47
 */
public class ResponseObject<T> {

  /** 返回数据 */
  private T data;

  /** 业务状态码 */
  private int code;

  /** 成功或失败信息 */
  private String moreInfo;

  public ResponseObject() {}

  public ResponseObject(T data, int code, String moreInfo) {
    this.data = data;
    this.code = code;
    this.moreInfo = moreInfo;
  }

  /**
   * 成功响应
   *
   * @param data 响应数据
   * @param <T> 响应数据范型类型
   * @return 带响应数据的返回对象
   */
  public static <T> ResponseObject<T> success(T data) {
    return new ResponseObject<T>(
        data, GlobalErrorCode.SUCCESS.getCode(), GlobalErrorCode.SUCCESS.getMessage());
  }

  /**
   * 业务错误, 系统错误时通过exceptionHandler统一返回rest错误码
   *
   * @param <T> 自动类型推导参数
   * @param code 错误码
   * @param msg 错误信息
   * @return 响应结果
   */
  public static <T> ResponseObject<T> error(int code, String msg) {
    return new ResponseObject<T>(null, code, msg);
  }

  /**
   * 业务错误, 系统错误时通过exceptionHandler统一返回rest错误码
   *
   * @param <T> 自动类型推导参数
   * @param msg 错误信息
   * @return 响应结果
   */
  public static <T> ResponseObject<T> error(String msg) {
    return error(GlobalErrorCode.INTERNAL_ERROR.getCode(), msg);
  }

  public T getData() {
    return data;
  }

  public int getCode() {
    return code;
  }

  public String getMoreInfo() {
    return moreInfo;
  }

  @Override
  public String toString() {
    return "ResponseObject{"
        + "data="
        + data
        + ", code="
        + code
        + ", moreInfo='"
        + moreInfo
        + '\''
        + '}';
  }
}
