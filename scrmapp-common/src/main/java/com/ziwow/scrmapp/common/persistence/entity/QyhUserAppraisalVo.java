package com.ziwow.scrmapp.common.persistence.entity;

public class QyhUserAppraisalVo extends QyhUserAppraisal{
    private Boolean is_repair;
    private Boolean is_order;
    private Integer is_source;

    public Boolean getIs_repair() {
        return is_repair;
    }

    public void setIs_repair(Boolean is_repair) {
        this.is_repair = is_repair;
    }

    public Boolean getIs_order() {
        return is_order;
    }

    public void setIs_order(Boolean is_order) {
        this.is_order = is_order;
    }

    public Integer getIs_source() {
        return is_source;
    }

    public void setIs_source(Integer is_source) {
        this.is_source = is_source;
    }
}