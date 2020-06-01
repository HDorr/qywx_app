package com.ziwow.scrmapp.common.iot;

import java.util.Date;

/**
 * iot滤芯信息
 */
public class IotFilterInfo {

    private Long id;

    /**
     * 关联设别编号
     */
    private String sncode;

    /**
     * 关联设备id
     */
    private Long equipmentInfoId;

    /**
     * 滤芯级数
     */
    private String filterLevel;

    /**
     * 滤芯剩余百分比
     */
    private Integer filterPercent;

    /**
     * 滤芯剩余使用天数
     */
    private Integer filterLife;

    /**
     * 过期时间
     */
    private Date overdueDate;

    private Boolean archive;

    private Date updatedAt;

    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    public String getSncode() {
        return sncode;
    }

    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    public Long getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Long equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public String getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(String filterLevel) {
        this.filterLevel = filterLevel;
    }

    public Integer getFilterPercent() {
        return filterPercent;
    }

    public void setFilterPercent(Integer filterPercent) {
        this.filterPercent = filterPercent;
    }

    public Integer getFilterLife() {
        return filterLife;
    }

    public void setFilterLife(Integer filterLife) {
        this.filterLife = filterLife;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
