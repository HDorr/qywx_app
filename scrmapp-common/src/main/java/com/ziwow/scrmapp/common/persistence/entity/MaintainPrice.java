package com.ziwow.scrmapp.common.persistence.entity;

import java.math.BigDecimal;

public class MaintainPrice {
    private Long id;

    private String maintainName;

    private BigDecimal maintainPrice;

    private String productCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
    }

    public BigDecimal getMaintainPrice() {
        return maintainPrice;
    }

    public void setMaintainPrice(BigDecimal maintainPrice) {
        this.maintainPrice = maintainPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}