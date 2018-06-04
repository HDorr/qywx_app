package com.ziwow.scrmapp.common.bean.pojo;

public class ProductParam {
    private String spec;    // 产品型号
    private String item_code;    // 产品编码
    private String barCode; // 产品条码

    public ProductParam() {
    }

    public ProductParam(String spec, String barCode) {
        this.spec = spec;
        this.barCode = barCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}