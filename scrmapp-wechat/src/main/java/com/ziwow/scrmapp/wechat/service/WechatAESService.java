package com.ziwow.scrmapp.wechat.service;


/**
 * 
* @包名   com.ziwow.scrmapp.wechat.service   
* @文件名 WechatAESService.java   
* @作者   john.chen   
* @创建日期 2017-2-23   
* @版本 V 1.0
 */
public interface WechatAESService {
	public String Decrypt(String key, String iv,String encryptStr) throws Exception;
	public String Encrypt(String key, String iv,String str) throws Exception;
	public String Decrypt(String str) throws Exception;
	public String Encrypt(String str) throws Exception;
}