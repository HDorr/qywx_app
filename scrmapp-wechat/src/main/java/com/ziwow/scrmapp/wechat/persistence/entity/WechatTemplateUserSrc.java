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

    private String prarm1;
    private String prarm2;
    private String prarm3;


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

    public String getPrarm1() {
        return prarm1;
    }

    public void setPrarm1(String prarm1) {
        this.prarm1 = prarm1;
    }

    public String getPrarm2() {
        return prarm2;
    }

    public void setPrarm2(String prarm2) {
        this.prarm2 = prarm2;
    }

    public String getPrarm3() {
        return prarm3;
    }

    public void setPrarm3(String prarm3) {
        this.prarm3 = prarm3;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
