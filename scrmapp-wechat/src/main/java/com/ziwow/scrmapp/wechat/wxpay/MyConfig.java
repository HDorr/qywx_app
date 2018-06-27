package com.ziwow.scrmapp.wechat.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import com.ziwow.scrmapp.wechat.constants.WXPayConstant;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class MyConfig implements WXPayConfig{

    private String appId = "wx180143def83cffac";  //微信公众账号或开放平台APP的唯一标识
    private String mchId  = "1248223901";   //微信支付商户号
    private String key  = "Xquark1234wohaoqingyuanshc223411";
    private String certPath  = "/usr/local/pay/apiclient_cert.p12";



    private byte[] certData;

    public MyConfig() throws Exception {
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    //获取 API 密钥
    public String getKey() {
        return key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
