package com.ziwow.scrmapp.wechat.vo;

/**
 * Created by xiaohei on 2017/3/20.
 */
public class EnumVo {

    private String code;
    private String name;

    public EnumVo() {
    }

    public EnumVo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
