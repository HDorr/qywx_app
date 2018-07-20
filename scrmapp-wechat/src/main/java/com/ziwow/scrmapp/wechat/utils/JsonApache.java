package com.ziwow.scrmapp.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.tools.utils.MD5;
import com.ziwow.scrmapp.wechat.constants.WXPayConstant;
import com.ziwow.scrmapp.wechat.service.ProductService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class JsonApache {
    private static final Logger log = LoggerFactory.getLogger(JsonApache.class);

    public static String postModelNames(String unionId, List<Product> list, String url) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject [] arr = new JSONObject[list.size()];
            for (int i=0; i<list.size(); i++) {
                Product p = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("id", String.valueOf(p.getId()));
                jo.put("modelName", p.getModelName());
                jo.put("productCode",p.getProductCode());
                arr[i] = jo;
            }
            jsonObject.put("unionId", unionId);
            jsonObject.put("data", arr);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String jsonstr = jsonObject.toJSONString();
            log.info("postModelNames方法JSON字符串：" + jsonstr);
            StringEntity se = new StringEntity(jsonstr, "UTF-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            // 输出调用结果
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("postModelNames方法服务器返回JSON字符串：" + result);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Boolean verifyPayByModelName(String unionId, List<Product> list, String url) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject [] arr = new JSONObject[list.size()];
            for (int i=0; i<list.size(); i++) {
                Product p = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("id", String.valueOf(p.getId()));
                jo.put("modelName", p.getModelName());
                arr[i] = jo;
            }
            jsonObject.put("unionId", unionId);
            jsonObject.put("data", arr);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String jsonstr = jsonObject.toJSONString();
            log.info("verifyPayByModelName方法JSON字符串：" + jsonstr);
            StringEntity se = new StringEntity(jsonstr, "UTF-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            // 输出调用结果
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("verifyPayByModelName方法服务器返回JSON字符串：" + result);
                JSONObject jsonResult = JSON.parseObject(result);
                JSONArray jsonArray = jsonResult.getJSONArray("data");
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject j = (JSONObject) jsonArray.get(i);
                    String serviceStatus = (String) j.get("serviceStatus");
                    if (serviceStatus.equals(String.valueOf(WXPayConstant.payStatusOne))) {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return false;
    }


    public static String notifyBackPlatform(String unionId, List<Product> productList, List<ProductFilter> productFilterList, String url) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("unionId", unionId);
            //保持data
            JSONObject [] arr = new JSONObject [productFilterList.size()];
            for (int i=0; i<productFilterList.size(); i++) {
                ProductFilter pf = productFilterList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("id", pf.getId());
                String modelName = pf.getModelName();
                jo.put("modelName", modelName);
                int temFee = pf.getServiceFee();
                BigDecimal bg = new BigDecimal(String.valueOf(temFee));
                String serviceFee = bg.setScale(2, BigDecimal.ROUND_CEILING).divide(new BigDecimal(100)).toString();
                jo.put("serviceFee", serviceFee);
                String serviceFeeId = pf.getServiceFeeId();
                jo.put("serviceFeeID",serviceFeeId);

                arr[i] = jo;
            }
            jsonObject.put("data", arr);
            long timestamp = System.currentTimeMillis();
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("signture", MD5.toMD5(Constant.AUTH_KEY + timestamp));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String jsonstr = jsonObject.toJSONString();
            log.info("notifyBackPlatform方法JSON字符串：" + jsonstr);
            StringEntity se = new StringEntity(jsonstr, "UTF-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            // 输出调用结果
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("notifyBackPlatform方法服务器返回JSON字符串：" + result);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
