/**
 * 第三方平台消息类型
 * @author Daniel.wang
 */
package com.ziwow.scrmapp.tools.weixin.enums;

/**
 * 消息类型
 *
 */
public enum InfoTypes {
	COMPONENT_VERIFY_TICKET("component_verify_ticket"), 
	UNAUTHORIZED("unauthorized"), 
	AUTHORIZED("authorized"),
	UPDATEAUTHORIZED("updateauthorized");
	private String type;
	
	InfoTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
