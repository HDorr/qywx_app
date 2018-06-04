package com.ziwow.scrmapp.wechat.vo;

import com.ziwow.scrmapp.common.persistence.entity.Filter;

import java.util.List;

/**
 * Created by xiaohei on 2017/4/5.
 */
public class ProductVo {
    private Long id;

    private String itemKind;

    private String typeName;

    private String smallcName;

    private String modelName;

    private Integer levelId;

    private String levelName;

    private String productName;

    private String productCode;

    private String productBarCode;

    private String productImage;

    private Integer saleType;

    private String saleTypeName;

    private String channelName;

    private String shoppingOrder;

    private Integer filterRemind;

    private String buyTime;

    private List<Filter> filterList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSmallcName() {
        return smallcName;
    }

    public void setSmallcName(String smallcName) {
        this.smallcName = smallcName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

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

    public String getProductBarCode() {
        return productBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public String getSaleTypeName() {
        return saleTypeName;
    }

    public void setSaleTypeName(String saleTypeName) {
        this.saleTypeName = saleTypeName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(String shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }

    public Integer getFilterRemind() {
        return filterRemind;
    }

    public void setFilterRemind(Integer filterRemind) {
        this.filterRemind = filterRemind;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }
}
