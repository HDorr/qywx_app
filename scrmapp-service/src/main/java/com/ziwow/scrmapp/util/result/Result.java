/**
 * Project Name:coupon-service
 * File Name:AbstractResult.java
 * Package Name:com.ziwow.coupon.result
 * Date:2014-11-14下午1:21:03
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.util.result;

/**
 * ClassName: AbstractResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-14 下午1:21:03 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public interface Result {
	public void setReturnMsg(String errMsg);
	public String getReturnMsg();
	public void setReturnCode(Integer returnCode);
	public Integer getReturnCode();
	public void setData(Object data);
	public Object getData();
}

