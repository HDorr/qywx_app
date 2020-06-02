package com.ziwow.scrmapp.common.iot;

/**
 * iot滤芯基本信息
 * @author songkaiqi
 * @since 2020/06/02/上午11:46
 */
public class IotFilterInfo {

    /**
     * 设备编码
     */
    private String sncode;

    /**
     * 滤芯级别
     */
    private String filterLevel;

    /**
     * 滤芯u9
     */
    private String erpCode;

    /**
     * 滤芯名称
     */
    private String name;

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public String getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(String filterLevel) {
        this.filterLevel = filterLevel;
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
