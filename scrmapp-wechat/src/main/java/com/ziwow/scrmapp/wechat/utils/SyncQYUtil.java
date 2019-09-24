package com.ziwow.scrmapp.wechat.utils;

import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.wechat.controller.WechatController;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Author: created by madeyong
 * @CreateDate: 2019-09-23 18:09
 */
public class SyncQYUtil {
    Logger logger = LoggerFactory.getLogger(SyncQYUtil.class);

    public String getResult(String key, Map<String,Object> params,String method,String url){
        String timeStamp = String.valueOf(new Date().getTime());
        String signature = MD5.toMD5(timeStamp + key);
        params.put("timeStamp",timeStamp);
        params.put("signature",signature);
        JSONObject jsonBody =JSONObject.fromObject(params);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url); // 沁园的接口地址
        HttpEntity reqEntity = new StringEntity(jsonBody.toString(),"UTF-8");
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        String qyResult = "";
        try {
            final HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            qyResult = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            qyResult = "false";
        }
        return qyResult;
    }
}
