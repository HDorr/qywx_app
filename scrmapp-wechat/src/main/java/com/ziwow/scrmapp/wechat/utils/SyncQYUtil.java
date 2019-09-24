package com.ziwow.scrmapp.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.ziwow.scrmapp.common.utils.HttpClientUtil;
import com.ziwow.scrmapp.tools.utils.HttpClientUtils;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.wechat.controller.WechatController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: created by madeyong
 * @CreateDate: 2019-09-23 18:09
 */
public class SyncQYUtil {
    Logger logger = LoggerFactory.getLogger(SyncQYUtil.class);

    public static Map getResult(String key, Map<String,Object> params,String method,String url){
        String timeStamp = String.valueOf(new Date().getTime());
        String signature = MD5.toMD5(key + timeStamp);
        params.put("timeStamp",timeStamp);
        params.put("signature",signature);
        Map result = new HashMap<>();
        String res = HttpClientUtils.postJson(url, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(res)) {
            result = JSON.parseObject(res);
        }
        return result;
    }
}
