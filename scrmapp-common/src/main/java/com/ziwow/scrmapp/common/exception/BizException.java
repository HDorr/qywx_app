package com.ziwow.scrmapp.common.exception;

import com.ziwow.scrmapp.common.enums.GlobalErrorCode;

/**
 * 业务逻辑异常
 *
 * @author: yyc
 * @date: 19-3-30 下午11:30
 */
public class BizException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final GlobalErrorCode errorCode;

  public BizException(GlobalErrorCode ec) {
    this(ec, null);
  }

  public BizException(String message) {
    this(message, null);
  }

  public BizException(GlobalErrorCode ec, Throwable cause) {
    super(ec.getMessage(), cause);
    this.errorCode = ec;
  }

  public BizException(String message, Throwable cause) {
    super(message, cause);
    this.errorCode = GlobalErrorCode.INTERNAL_ERROR;
  }

  public GlobalErrorCode getErrorCode() {
    return errorCode;
  }
}
