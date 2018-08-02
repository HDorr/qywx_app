package com.ziwow.scrmapp.wechat.vo;

import java.io.Serializable;

/**
 * @Auther: yiyongchang
 * @Date: 18-7-29 下午3:37
 * @Description: 企业号门店预约单产品
 */
public class WechatQyhProductVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productName; //产品名称
    private String productCode; //产品编码
    private String modelName; //产品型号名称
    private String typeName; //产品类别(大类)
    private String itemKind; //产品来源组织
    private String productImage; //产品展示图片

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
