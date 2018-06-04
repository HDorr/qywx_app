package com.ziwow.scrmapp.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class ChuangLanMsgSender {
	private static final String uri = "https://sms.253.com/msg/send";
	private static final String rd = "0";	// 是否需要状态报告，需要1，不需要0
	private static final String ex = null;	// 扩展码
	
	//亿美短信接口
	public static String baseUrl = "http://hprpt2.eucp.b2m.cn:8080/sdkproxy/";
	public static String code = "";//附加号（最长10位，可置空）。
	public static long seqId = System.currentTimeMillis();//长整型值企业内部必须保持唯一，获取状态报告使用
	public static String smspriority = "1";//短信优先级1-5
	
	/**
	 * 
	 * @param un 账号
	 * @param pw 密码
	 * @param mobiles 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @param rd 是否需要状态报告，需要1，不需要0
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	public static String batchSend(String un, String pw, String mobiles, String msg) throws Exception {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(uri, false);
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("un", un),
					new NameValuePair("pw", pw), 
					new NameValuePair("phone", mobiles),
					new NameValuePair("rd", rd), 
					new NameValuePair("msg", msg),
					new NameValuePair("ex", ex), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
	}
	
	public static String batchSendByEmay(String userName, String passWord, String mobiles, String message) throws Exception {
		String param = "";
		String url = baseUrl + "sendsms.action";
		try {
			message = URLEncoder.encode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		param = "cdkey=" + userName + "&password=" + passWord + "&phone=" + mobiles + "&message=" + message + "&addserial=" + code + "&seqid=" + seqId+ "&smspriority=1";
		url = baseUrl + "sendsms.action";
		
		return SDKHttpClient.sendSMS(url, param);

	}
}
