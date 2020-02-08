package com.ziwow.scrmapp.wechat.persistence.entity;

import com.ziwow.scrmapp.common.enums.TemplateIdentityType;

/**
 * 通知模板用户数据群体
 * @author jitre
 * @since 2020年02月07日
 */
public class WechatTemplateUserGroup {

    private Long id;

    /**
     * 通知模板id
     */
    private String templateId;
    /***
     * 人群名称
     */
    private String name;
    /***
     * 模板标题
     */
    private String title;
    /**
     * 模板备注
     */
    private String remark;
    /***
     * 模板跳转路径
     */
    private String url;
    /***
     * 模板是否跳转小程序
     */
    private Boolean toMini;
    /***
     * 模板是否采用中心参数
     */
    private  Boolean centerParam;

    /**
     * 模板参数的个数
     */
    private  Integer paramCount;


    private String param1;
    private String param2;
    private String param3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getToMini() {
        return toMini;
    }

    public void setToMini(Boolean toMini) {
        this.toMini = toMini;
    }

    public Boolean getCenterParam() {
        return centerParam;
    }

    public void setCenterParam(Boolean centerParam) {
        this.centerParam = centerParam;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "WechatTemplateUserGroup{" +
            "id=" + id +
            ", templateId='" + templateId + '\'' +
            ", name='" + name + '\'' +
            ", title='" + title + '\'' +
            ", remark='" + remark + '\'' +
            ", url='" + url + '\'' +
            ", toMini=" + toMini +
            ", centerParam=" + centerParam +
            ", paramCount=" + paramCount +
            ", prarm1='" + param1 + '\'' +
            ", prarm2='" + param2 + '\'' +
            ", prarm3='" + param3 + '\'' +
            '}';
    }
}
