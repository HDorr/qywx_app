package com.ziwow.scrmapp.common.bean.vo.csm;

import java.io.Serializable;

/**
 * 描述: 400派单产品vo
 *
 * @Author: John
 * @Create: 2017-12-20 17:07
 */
public class DispatchDotProductVo implements Serializable {
    private Integer o2o;            // 线上线下
    private String buyChannel;      // 产品购买渠道
    private String buyTime;         // 产品购买时间format（yyyy-MM-dd）
    private String itemKind;        // 净水机/饮水机/净水宝
    private String bigcName;        // 产品大类
    private String smallcName;      // 产品小类
    private String spec;  	        // 产品型号
    private int filterGradeId;      // 滤芯级别Id
    private String filterGrade;     // 滤芯级别名称
    private String itemName;        // 产品名称
    private String itemCode;        // 产品编码
    private String barCode;         // 产品条码
    private Integer fromChannel;    // 销售类型(1:家用,2:商用,3:电商,4:战略部)
    public Integer getO2o() {
        return o2o;
    }

    public void setO2o(Integer o2o) {
        this.o2o = o2o;
    }

    public String getBuyChannel() {
        return buyChannel;
    }

    public void setBuyChannel(String buyChannel) {
        this.buyChannel = buyChannel;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getBigcName() {
        return bigcName;
    }

    public void setBigcName(String bigcName) {
        this.bigcName = bigcName;
    }

    public String getSmallcName() {
        return smallcName;
    }

    public void setSmallcName(String smallcName) {
        this.smallcName = smallcName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getFilterGradeId() {
        return filterGradeId;
    }

    public void setFilterGradeId(int filterGradeId) {
        this.filterGradeId = filterGradeId;
    }

    public String getFilterGrade() {
        return filterGrade;
    }

    public void setFilterGrade(String filterGrade) {
        this.filterGrade = filterGrade;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getFromChannel() {
        return fromChannel;
    }

    public void setFromChannel(Integer fromChannel) {
        this.fromChannel = fromChannel;
    }
}
