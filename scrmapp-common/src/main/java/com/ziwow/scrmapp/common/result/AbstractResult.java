/**
 * Project Name:core-service
 * File Name:AbstractResult.java
 * Package Name:com.ziwow.coupon.core.result
 * Date:2014-11-14下午1:36:51
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.common.result;

/**
 * ClassName: AbstractResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-14 下午1:36:51 <br/>
 * 
 * @author Daniel.Wang
 * @version
 * @since JDK 1.6
 */
public abstract class AbstractResult implements Result {
	Integer returnCode = ErrorCode.SUCCESS;
	String returnMsg = null;
	Object data = null;

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#setReturnMsg(java.lang.String)
	 */
	@Override
	public void setReturnMsg(String errMsg) {
		this.returnMsg = errMsg;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#getReturnMsg()
	 */
	@Override
	public String getReturnMsg() {
		return returnMsg;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#setReturnCode(java.lang.Integer)
	 */
	@Override
	public void setReturnCode(Integer returnCode) {

		this.returnCode = returnCode;

	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#getReturnCode()
	 */
	@Override
	public Integer getReturnCode() {
		return returnCode;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#setData(java.lang.Object)
	 */
	@Override
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see com.ziwow.marketing.util.result.activity.core.result.Result#getData()
	 */
	@Override
	public Object getData() {

		return data;
	}
}