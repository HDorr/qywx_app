package com.ziwow.scrmapp.wechat.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import com.ziwow.scrmapp.wechat.constants.WXPayConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MyConfig implements WXPayConfig,ApplicationContextAware {

    @Value("${wechat.pay.appid}")
    private String appId;  //微信公众账号或开放平台APP的唯一标识
    @Value("${wechat.pay.mchid}")
    private String mchId ;   //微信支付商户号
    @Value("${wechat.pay.key}")
    private String key ;
    @Value("${wechat.pay.certpath}")
    private String certPath;




    private byte[] certData;

    public MyConfig() {
//
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
        InputStream certStream  = WeixinPayUtil.class.getClassLoader().getResourceAsStream(certPath);
        try {
            this.certData = new byte[certStream.available()];
            certStream.read(this.certData);
            certStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
