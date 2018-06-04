package com.ziwow.scrmapp.tools.thirdParty;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziwow.scrmapp.tools.utils.MD5;

public class SignUtil {
	private static final Logger LOG = LoggerFactory.getLogger(SignUtil.class);
	public static boolean checkSignature(String signature, String timeStamp, String authKey) {
		LOG.info("签名校验参数:signature=" + signature + ",timeStamp=" + timeStamp + ",authKey=" + authKey);
		if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timeStamp) || StringUtils.isEmpty(authKey)) {
			return false;
		}
		signature = signature.trim();
		timeStamp = timeStamp.trim();
		authKey = authKey.trim();
		String signStr = StringUtils.EMPTY;
		if (null != signature) {
			signStr = MD5.toMD5(authKey + timeStamp);
			LOG.info("[md5 signature=" + signStr + "]");
		}
		return StringUtils.isNotEmpty(signStr) ? signStr.equals(signature.toLowerCase()) : false;
	}
}
