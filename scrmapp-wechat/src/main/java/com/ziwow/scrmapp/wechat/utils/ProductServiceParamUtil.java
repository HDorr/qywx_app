package com.ziwow.scrmapp.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziwow.scrmapp.common.persistence.entity.Product;
import com.ziwow.scrmapp.common.persistence.entity.ProductFilter;
import com.ziwow.scrmapp.tools.utils.StringUtil;
import com.ziwow.scrmapp.wechat.constants.WXPayConstant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceParamUtil {

    public static List<Product> addServiceParam(List<Product> list, String content) {
        JSONObject jsonObject = JSON.parseObject(content);
        //String moreInfo = jo.getString("moreInfo");
        //System.out.println(moreInfo);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        //System.out.println(data);  //[{"modelName":"[\"MK-UF-12\"]","serviceStatus":"5"}]
        List<Product> newList = new ArrayList<Product>();



        for (Product p : list) {
            Long id = p.getId();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jo = (JSONObject) jsonArray.get(i);
                if (String.valueOf(id).equals( jo.get("id"))) {
                    String serviceFee = (String) jo.get("serviceFeePrice");
                    if (serviceFee == null) {
                        p.setServiceFee("");
                    } else {
                        p.setServiceFee(serviceFee);
                    }
                    String serviceStatus = (String) jo.get("serviceStatus");
//                    String serviceStatus = serviceStatusCompare(tem);
                    String serviceStatusStr = (String) jo.get("serviceStatusStr");
                    String serviceFeeId = (String) jo.get("serviceFeeId");
                    String filterModel = (String) jo.get("modelName");
                    p.setServiceStatus(serviceStatus);
                    p.setServiceStatusStr(serviceStatusStr);
                    p.setServiceFeeId(serviceFeeId);
                    if (StringUtil.isNotBlank(filterModel)){
                        p.setServiceFeeName(filterModel+"服务费");
                    }
                    newList.add(p);
                    jsonArray.remove(i);
                }
            }
        }

        return newList;
    }

    /**
     * 0：已购买滤芯未购买服务
     * 1：已购买滤芯和服务
     * 5：没有滤芯或不能购买滤芯
     */
    public static String serviceStatusCompare(String serviceStatus) {
        if (serviceStatus.equals("0")) {
            return WXPayConstant.filterServiceUnpay;
        } else if (serviceStatus.equals("1")) {
            return WXPayConstant.filterServicePayed;
        } else {
            return WXPayConstant.filterServiceNone;
        }
    }

}
