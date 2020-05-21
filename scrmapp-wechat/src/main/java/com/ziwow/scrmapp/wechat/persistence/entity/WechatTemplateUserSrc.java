package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.TemplateIdentityType;

/**
 * 通知模板用户数据
 * @author jitre
 * @since 2020年02月07日
 */
public class WechatTemplateUserSrc {

    private Long id;

    /**
     * 用户标志
     */
    private String identity;

    /***
     * 人群包名称
     */
    private Long groupId;

    /**
     * 标志类型，手机号或oepnid
     */
    private TemplateIdentityType identityType;

    /**
     * 模板参数的个数
     */
    private  Integer paramCount;

    /**
     * 是否发送过
     */
    private Boolean flag;

    private String param1;
    private String param2;
    private String param3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public TemplateIdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(TemplateIdentityType identityType) {
        this.identityType = identityType;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }


    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
