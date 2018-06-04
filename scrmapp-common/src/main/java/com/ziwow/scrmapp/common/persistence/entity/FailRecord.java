package com.ziwow.scrmapp.common.persistence.entity;

public class FailRecord {
    private Integer id;

    private String failClass;

    private String failMethod;

    private String parameters;

    private Integer isDeal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFailClass() {
        return failClass;
    }

    public void setFailClass(String failClass) {
        this.failClass = failClass;
    }

    public String getFailMethod() {
        return failMethod;
    }

    public void setFailMethod(String failMethod) {
        this.failMethod = failMethod;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }
}