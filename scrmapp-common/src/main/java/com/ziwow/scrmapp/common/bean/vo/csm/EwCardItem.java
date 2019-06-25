package com.ziwow.scrmapp.common.bean.vo.csm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 延保卡详情
 * @author songkaiqi
 * @since 2019/06/09/下午2:38
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EwCardItem {


    /**
     * 对应机型
     */
    @JsonProperty("item_name")
    private String itemName;


    /**
     * 对应编码
     */
    @JsonProperty("item_code")
    private String itemCode;


    /**
     * 延保期限
     */
    @JsonProperty("valid_time")
    private int validTime;


    /**
     * 获取拆分后的结果
     * @return
     */
    public String[] getItemNames(){
        return this.itemName.split(",");
    }

    /**
     * 获取拆分后的结果
     * @return
     */
    public String[] getItemCodes(){
        return this.itemCode.split(",");
    }



    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }
}
