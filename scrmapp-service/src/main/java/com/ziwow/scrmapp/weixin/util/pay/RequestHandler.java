package com.ziwow.scrmapp.weixin.util.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.ziwow.scrmapp.core.util.MD5Util;

/**
 * ClassName: NewRequestHanlder <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-12-4 下午5:06:43 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class RequestHandler {
	private String partnerkey;
	private String charset;

	/**
	 * 初始化函数。
	 */
	public void init(String partner_key) {
		this.partnerkey = partner_key;
	}

	public void init() {
	}
	
	//设置密钥
	public void setKey(String key) {
		this.partnerkey = key;
	}
	
	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	@SuppressWarnings("rawtypes")
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		System.out.println("md5 sb:" + sb+"---key="+this.getKey());
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
				.toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}
	
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	public String getKey() {
		return partnerkey;
	}
}