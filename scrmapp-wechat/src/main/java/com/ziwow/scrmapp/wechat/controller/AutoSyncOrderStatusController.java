package com.ziwow.scrmapp.wechat.controller;
import com.ziwow.scrmapp.common.annotation.MiniAuthentication;
import com.ziwow.scrmapp.common.constants.Constant;
import com.ziwow.scrmapp.common.result.BaseResult;
import com.ziwow.scrmapp.common.result.Result;
import com.ziwow.scrmapp.wechat.constants.QYConstans;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

/**
 * @Author: created by madeyong
 * @CreateDate: 2019-09-23 14:00
 */
@RestController
public class AutoSyncOrderStatusController {

    Logger log = LoggerFactory.getLogger(AutoSyncOrderStatusController.class);

    @RequestMapping(value = "/syncOrderStatus", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @MiniAuthentication
    public Result syncOrderStatus(@RequestParam("signature") String signature,
                              @RequestParam("timeStamp") String timeStamp,
                              @RequestParam(value = "logisticsNum", required = false) String logisticsNum,
                              @RequestParam(value = "LogisticsCompanies", required = false) String LogisticsCompanies,
                              @RequestParam(value = "orderId", required = false) String orderId
                              ) {
        Map<String,Object> params = new HashMap<>();
        timeStamp = String.valueOf(new Date().getTime());
        params.put("signature",signature);
        params.put("timeStamp",timeStamp); // 校验参数
        params.put("logisticsNum",logisticsNum); // 物流号
        params.put("LogisticsCompanies",LogisticsCompanies); // 物流公司
        params.put("orderId",orderId); // 订单号
        JSONObject jsonBody =JSONObject.fromObject(params);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(QYConstans.URL); // 沁园的接口地址
        HttpEntity reqEntity = new StringEntity(jsonBody.toString(),"UTF-8");
        httpPost.setEntity(reqEntity);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        log.info("调用沁园同步订单状态接口：url = [{}]，params = [ 物流号={}，物流公司={}，订单号={}]", QYConstans.URL,
                logisticsNum,LogisticsCompanies,orderId);
        String qyResult = "";
        try {
            final HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            qyResult = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.error("调用沁园同步订单状态接口失败，错误：[{}]", e);
        }
        log.info("调用沁园同步订单状态接口成功，返回值为：[{}]", qyResult);
        Result result = new BaseResult();
        if("".equals(qyResult)){
            result.setReturnMsg("同步订单状态失败!");
            result.setReturnCode(QYConstans.FAIL);
            return result;
        }
        result.setReturnMsg("同步订单状态成功!");
        result.setReturnCode(QYConstans.SUCCESS);
        return result;
    }

    public static void main(String[] args) {
        long x = new Date().getTime();
        System.out.println(String.valueOf(x));
        System.out.println(new Date().getTime());
    }

}
