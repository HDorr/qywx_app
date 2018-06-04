package com.ziwow.scrmapp.tools.weixin.enums;
/**
 * 
 * ClassName: OpenWeixinFunType <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016-5-16 上午10:49:19 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public enum OpenWeixinFunType {
    MSG(1, "消息管理权限"), 
    USER(2, "用户管理权限"),  
    ACCOUNT(3, "帐号服务权限"),
    OAUTH(4, "网页服务权限"),
    OLINESTORE(5, "微信小店权限"),
    CHAT(6, "微信多客服权限"),
    NOTIFY(7, "群发与通知权限"),
    CART(8, "微信卡券权限"),
    SCAN(9, "微信扫一扫权限"),
    WIFI(10, "微信连WIFI权限"),
    MATERIAL(11, "素材管理权限"),
    SHAKE(12, "微信摇周边权限"),
    OFFLINESTORE(13, "微信门店权限"),
    PAY(14, "微信支付权限"),
    MENU(15, "自定义菜单权限");
    
	private final int code;
	private final String name;

	OpenWeixinFunType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static OpenWeixinFunType getByCode(int code) {
		for (OpenWeixinFunType signType : OpenWeixinFunType.values()) {
			if (signType.getCode() == code) {
				return signType;
			}
		}
		return null;
	}
}