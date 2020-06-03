package com.ziwow.scrmapp.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * iot http请求工具
 * @author songkaiqi
 * @since 2020/06/02/下午4:12
 */
public class IotHttpUtils {


    private static String accessKey = "51adc2e4cd5548e1b6065b135cef75b6";

    public static Map<String,String> getParam(Integer pageSize){
        Map<String,String> map = new HashMap<String, String>(4);
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        map.put("timestamp",timeStamp);
        map.put("sign",MD5.toMD5(timeStamp.concat("&").concat(accessKey)).toUpperCase());
        map.put("pageSize",pageSize.toString());
        final Date date = DateUtils.addDays(new Date(), -1);
        map.put("updateTime", DateFormatUtils.format(date,"yyyy-MM-dd 00:00:00"));
        return map;
    }


}
