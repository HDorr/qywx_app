/**
 * Project Name:coupon-service
 * File Name:BaseResult.java
 * Package Name:com.ziwow.coupon.result
 * Date:2014-11-14下午1:36:29
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.common.result;

/**
 * ClassName: BaseResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-14 下午1:36:29 <br/>
 * 
 * @author Daniel.Wang
 * @version
 * @since JDK 1.6
 */
public class BaseResult extends AbstractResult {
	public static Result sysError() {
		Result result = new BaseResult();
		result.setReturnCode(ErrorCode.SYS_ERROR);
		result.setReturnMsg(ErrorCode.ERROR_MAP.get(ErrorCode.SYS_ERROR));
		return result;
	}

	public static Result Success(Object data) {
		Result result = new BaseResult();
		result.setReturnCode(ErrorCode.SUCCESS);
		result.setData(data);
		result.setReturnMsg(ErrorCode.ERROR_MAP.get(ErrorCode.SUCCESS));
		return result;
	}

	public static Result Fail(String msg) {
		Result result = new BaseResult();
		result.setReturnCode(ErrorCode.FALIURE);
		result.setReturnMsg(msg);
		return result;
	}

	public static Result Fail(String msg, Object data) {
		Result result = new BaseResult();
		result.setReturnCode(ErrorCode.FALIURE);
		result.setData(data);
		result.setReturnMsg(msg);
		return result;
	}

	public static Result build(Integer code, String msg, Object data) {
		Result result = new BaseResult();
		result.setReturnCode(code);
		result.setReturnMsg(msg);
		result.setData(data);
		return result;
	}
}