package com.ziwow.scrmapp.common.utils;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String signature = MD5.toMD5(key + timeStamp);
        params.put("timeStamp",timeStamp);
        params.put("signature",signature);
        Map result = new HashMap<String,Object>();
        String res = HttpClientUtils.postJson(url, JSONObject.fromObject(params).toString());
        if (StringUtils.isNotBlank(res)) {
            result = JSON.parseObject(res);
        }
        return result;
    }
}
