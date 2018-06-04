package com.ziwow.scrmapp.common.utils;

public class OrderUtils {
    public static Integer csmOrderStatusConvert(int status) {

        // stattus 1:已受理  3:已派工  5:已接单  7:已拒绝  9:已完工  11:待回访  13:已结案  2:已确认  6:已退单  14:已关闭
        // orderStatus 1.处理中  2.已取消   3.已派工   4.已接单   5.服务完成   6.已结束
        Integer orderStatus = null;

        if ((status == 1) || (status == 2) || (status == 3)) {
            orderStatus = 1;
        } else if ((status == 6) || (status == 7) || (status == 14)) {
            orderStatus = 2;
        }  else if (status == 5) {
            orderStatus = 4;
        } else if ((status == 11) || (status == 13)) {
            orderStatus = 5;
        } else if (status == 9) {
            orderStatus = 6;
        }

        return orderStatus;
    }

    public static String getServiceTypeName(int type) {
        String serviceType = "";

        if (type == 1) {
            serviceType = "安装";
        } else if (type == 2) {
            serviceType = "维修";
        } else if (type == 3) {
            serviceType = "保养";
        }

        return serviceType;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
