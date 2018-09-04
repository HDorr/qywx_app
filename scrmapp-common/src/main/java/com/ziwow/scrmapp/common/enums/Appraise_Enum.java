package com.ziwow.scrmapp.common.enums;

/**
 * @Auther: yiyongchang
 * @Date: 18-9-3 下午1:50
 * @Description: 评价显示类型
 */
public enum Appraise_Enum {

    INSTALL_APPRAISE(1, "安装服务评价"),
    REPAIR_APPRAISE(2, "维修服务评价"),
    CLEAN_APPRAISE(3, "清洗保养评价"),
    FILTER_APPRAISE(4, "滤芯保养评价");

    private int code;
    private String codeName;

    Appraise_Enum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getNameByCode(int code) {
        Appraise_Enum[] appraise_enums = Appraise_Enum.values();
        for (Appraise_Enum a : appraise_enums) {
            if (a.getCode() == code) {
                return a.getCodeName();
            }
        }
        return "";
    }

}
