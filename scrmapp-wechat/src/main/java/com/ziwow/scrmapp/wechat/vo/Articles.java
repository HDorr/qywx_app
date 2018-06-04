package com.ziwow.scrmapp.wechat.vo;

import java.io.Serializable;

import com.ziwow.scrmapp.tools.weixin.XStreamCDATA;


/**
 * 多图文消息
 * 
 */
public class Articles implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamCDATA
	private String Title;
	@XStreamCDATA
	private String Description;
	@XStreamCDATA
	private String PicUrl;
	@XStreamCDATA
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
