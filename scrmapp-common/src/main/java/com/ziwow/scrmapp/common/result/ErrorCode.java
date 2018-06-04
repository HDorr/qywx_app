package com.ziwow.scrmapp.common.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ClassName: UserState <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-15 上午11:34:06 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class ErrorCode {
	public static Map<Integer, String> ERROR_MAP =  new HashMap<Integer,String>();
	
	public static final int SUCCESS = 1; //成功
	public static final int DATA_INVALID	=	2; //数据格式不正确
	public static final int PARAMETER_INVALID = 3; //参数格式不正确
	public static final int FALIURE = 4; // 失败
	public static final int MSG_TRUE = 5; // 是
	public static final int MSF_FALSE = 6; // 否
	public static final int OUT_OF_DATE = 7; // 活动已过期
	public static final int SYS_ERROR = 8;
	
	static {
		ERROR_MAP.put(SUCCESS, "成功");
		ERROR_MAP.put(DATA_INVALID, "数据格式不正确");
		ERROR_MAP.put(PARAMETER_INVALID, "参数格式不正确");
		ERROR_MAP.put(FALIURE, "失败");
        ERROR_MAP.put(SYS_ERROR,"系统异常");
	}
}