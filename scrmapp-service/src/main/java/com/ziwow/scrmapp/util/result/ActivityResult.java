/**
 * Project Name:activity-service
 * File Name:ActivityResult.java
 * Package Name:com.ziwow.activity.result
 * Date:2014-12-24下午2:12:05
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.util.result;


/**
 * ClassName: ActivityResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-12-24 下午2:12:05 <br/>
 *
 * @author Eric
 * @version 
 * @since JDK 1.6
 */
public class ActivityResult extends AbstractResult{
	
	private String errorDetailCode;
	private String userMobile;
	private String detailReturnCode;
	private String userType;
	private boolean canPlay;

	/**
	 * errorDetailCode.
	 *
	 * @return  the errorDetailCode
	 */
	public String getErrorDetailCode() {
		return errorDetailCode;
	}

	/**
	 * errorDetailCode.
	 *
	 * @param   errorDetailCode    the errorDetailCode to set
	 */
	public void setErrorDetailCode(String errorDetailCode) {
		this.errorDetailCode = errorDetailCode;
	}

	/**
	 * userMobile.
	 *
	 * @return  the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * userMobile.
	 *
	 * @param   userMobile    the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * detailReturnCode.
	 *
	 * @return  the detailReturnCode
	 */
	public String getDetailReturnCode() {
		return detailReturnCode;
	}

	/**
	 * detailReturnCode.
	 *
	 * @param   detailReturnCode    the detailReturnCode to set
	 */
	public void setDetailReturnCode(String detailReturnCode) {
		this.detailReturnCode = detailReturnCode;
	}

	/**
	 * userType.
	 *
	 * @return  the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * userType.
	 *
	 * @param   userType    the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * canPlay.
	 *
	 * @return  the canPlay
	 */
	public boolean isCanPlay() {
		return canPlay;
	}

	/**
	 * canPlay.
	 *
	 * @param   canPlay    the canPlay to set
	 */
	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	
}

