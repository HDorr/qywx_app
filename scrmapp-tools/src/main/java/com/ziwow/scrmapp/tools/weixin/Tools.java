/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.ziwow.scrmapp.tools.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public final class Tools {

	public static final String inputStream2String(InputStream in) {
		if (in == null)
			return "";
		try {
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n, "UTF-8"));
			}
			return out.toString();
		} catch (Exception e) {

		} finally {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
		return "";
	}

	public static final boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		List<String> params = new ArrayList<String>();
		params.add(token);
		params.add(timestamp);
		params.add(nonce);
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		String temp = params.get(0) + params.get(1) + params.get(2);
		return DigestUtils.shaHex(temp).equals(signature);
	}
}
