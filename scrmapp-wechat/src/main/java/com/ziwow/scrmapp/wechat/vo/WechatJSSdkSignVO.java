package com.ziwow.scrmapp.wechat.vo;

import java.io.Serializable;

/**
 * 
* @ClassName: WechatJSSdkSignVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hogen
* @date 2016-8-18 上午10:39:32
*
 */
public class WechatJSSdkSignVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String signature;
    private String appId;
    private String timestamp;
    private String nonceStr;

    /**
     * signature.
     * 
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * signature.
     * 
     * @param signature
     *            the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * appId.
     * 
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * appId.
     * 
     * @param appId
     *            the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * timestamp.
     * 
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * timestamp.
     * 
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * nonceStr.
     * 
     * @return the nonceStr
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * nonceStr.
     * 
     * @param nonceStr
     *            the nonceStr to set
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
