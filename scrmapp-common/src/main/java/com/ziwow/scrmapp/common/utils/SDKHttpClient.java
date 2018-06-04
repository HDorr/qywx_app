package com.ziwow.scrmapp.common.utils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class SDKHttpClient {
	static Logger logger = Logger.getLogger(SDKHttpClient.class);

	// 下发
	public static String sendSMS(String url, String param) {
		String ret = "";
		url = url + "?" + param;
		System.out.println("【SDKHttpClient】发送MT到SDK->" + url);
		String responseString = HttpClientUtil.getInstance().doGetRequest(url);
		responseString = responseString.trim();
		if (null != responseString && !"".equals(responseString)) {
			ret = xmlMt(responseString);
		}
		return ret;
	}

	// 下发Post
	public static String sendSMSByPost(String url, String param) {
		String ret = "";
		url = url + "?" + param;
		System.out.println("【SDKHttpClient】发送MT到SDK By Post->" + url);
		String responseString = HttpClientUtil.getInstance().doPostRequest(url);
		responseString = responseString.trim();
		if (null != responseString && !"".equals(responseString)) {
			ret = xmlMt(responseString);
		}
		return ret;
	}

	// 解析下发response
	public static String xmlMt(String response) {
		String result = "0";
		Document document = null;
		try {
			document = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			e.printStackTrace();
			result = "-250";
		}
		Element root = document.getRootElement();
		result = root.elementText("error");
		if (null == result || "".equals(result)) {
			result = "-250";
		}
		return result;
	}
}
