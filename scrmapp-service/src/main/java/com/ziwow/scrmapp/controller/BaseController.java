package com.ziwow.scrmapp.controller;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.ziwow.scrmapp.core.util.Config;

@Component
public abstract class BaseController {

	/**
	 * 接口数据返回
	 * 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	protected Map<String, Object> rtnParam(Integer errorCode, Object data) {
		// 正常的业务逻辑
		if (errorCode == 0) {
			return ImmutableMap.of("errorCode", errorCode, "data",(data == null) ? new Object() : data);
		} else {
			return ImmutableMap.of("errorCode", errorCode, "msg",(Object) Config.props.getProperty(String.valueOf(errorCode)));
		}
	}
}