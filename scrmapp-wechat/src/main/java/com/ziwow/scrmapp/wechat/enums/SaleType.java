package com.ziwow.scrmapp.wechat.enums;

/**
 * Created by xiaohei on 2017/4/12.
 */
public enum SaleType {

    HOME(1, "家用"),
    TRADE(2, "商用"),
    RETAILERS(3, "电商"),
    STRATEGY(4, "战略部");

    private int id;
    private String name;


    SaleType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    /**
     * 通过id获得name
     *
     * @param id
     * @return
     */
    public static String getNameById(int id) {
        for (SaleType saleType : SaleType.values()) {
            if (id == saleType.getId()) {
                return saleType.getName();
            }
        }
        return null;
    }
}
