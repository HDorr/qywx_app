package com.ziwow.scrmapp.wechat.persistence.entity.ext;

import com.ziwow.scrmapp.wechat.persistence.entity.WechatUserAddress;

public class WechatUserAddressExt extends WechatUserAddress {
	
	//获取用户地址列表时,需要展示用户头像信息
	private String headimgurl;

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	

}
