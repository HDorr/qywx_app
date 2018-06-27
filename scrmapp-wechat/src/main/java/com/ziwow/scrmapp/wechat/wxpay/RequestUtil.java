package com.ziwow.scrmapp.wechat.wxpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 微信支付工具类
 */
public class RequestUtil {
	private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);
	
	/**
	 * 处理xml请求信息
	 * @param request
	 * @return
	 */
	public static String getXmlRequest(HttpServletRequest request){
		java.io.BufferedReader bis = null;
		String result = "";
		try{
			bis = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = bis.readLine()) != null){
				result += line;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(bis != null){
				try{
					bis.close();
				}catch(IOException e){
					log.error(e.getMessage());
				}
			}
		}
		return result;
	}

}
