package com.ziwow.scrmapp.wechat.weixin;

import java.io.Serializable;

/***
 * 通知模板的小程序字段
 */
public class Miniprogram implements Serializable {

    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 5521335583589781087L;

	private String appid;
	private String pagepath;

	public Miniprogram(String appid,String pagepath){
		this.appid=appid;
		this.pagepath=pagepath;
	}

	public Miniprogram(){

	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	public void setPagepath(String pagepath){
		this.pagepath=pagepath;
	}
}
