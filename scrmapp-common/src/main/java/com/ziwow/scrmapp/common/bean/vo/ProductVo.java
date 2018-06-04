package com.ziwow.scrmapp.common.bean.vo;

public class ProductVo {
    private Long productId;
    private Long levelId;
    private String productName;
    private String typeName;
    private String smallcName;
    private String productCode;
    private String productBarCode;
    private String barCodeImage;
    private String modelName;
    private String productImage;
    private Long ordersId;
    private String itemKind;
    private int o2o;
    private int buyChannel;
    private String buyTime;
    private String buyChannelName;
    private String shoppingOrder;
    private int saleType;
    private String productBarCodeTwenty;
    
    //产品的状态---对应t_orders_pro_relations表中的status
    private int status;
    

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getBarCodeImage() {
        return barCodeImage;
    }

    public void setBarCodeImage(String barCodeImage) {
        this.barCodeImage = barCodeImage;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public int getO2o() {
        return o2o;
    }

    public void setO2o(int o2o) {
        this.o2o = o2o;
    }

    public int getBuyChannel() {
        return buyChannel;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public void setBuyChannel(int buyChannel) {
        this.buyChannel = buyChannel;
    }

    public String getBuyChannelName() {
        return buyChannelName;
    }

    public void setBuyChannelName(String buyChannelName) {
        this.buyChannelName = buyChannelName;
    }

    public String getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(String shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProductBarCodeTwenty() {
		return productBarCodeTwenty;
	}

	public void setProductBarCodeTwenty(String productBarCodeTwenty) {
		this.productBarCodeTwenty = productBarCodeTwenty;
	}
}
