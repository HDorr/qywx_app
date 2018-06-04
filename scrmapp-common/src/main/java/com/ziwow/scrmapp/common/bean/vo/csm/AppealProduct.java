package com.ziwow.scrmapp.common.bean.vo.csm;

import java.io.Serializable;

/**
 * 描述:
 *
 * @Author: John
 * @Create: 2017-12-22 16:28
 */
public class AppealProduct implements Serializable {
    private static final long serialVersionUID = -7879062894080766684L;
    private String netSaleNo;        // 购买单号
    private String spec;            // 产品型号
    private String item_code;       // 产品编码
    private String purchDate;        // 购买日期

    public String getNetSaleNo() {
        return netSaleNo;
    }

    public void setNetSaleNo(String netSaleNo) {
        this.netSaleNo = netSaleNo;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getPurchDate() {
        return purchDate;
    }

    public void setPurchDate(String purchDate) {
        this.purchDate = purchDate;
    }
}
