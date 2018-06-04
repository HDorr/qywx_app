package com.ziwow.scrmapp.common.bean.vo;

/**
 * Created by xiaohei on 2017/4/18.
 */
public class QyhUserVo {

    private String name;
    private String imageUrl;
    private String mobile;
    private String position;
    private Double attitude;
    private Double profession;
    private Double integrity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getAttitude() {
        return attitude;
    }

    public void setAttitude(Double attitude) {
        this.attitude = attitude;
    }

    public Double getProfession() {
        return profession;
    }

    public void setProfession(Double profession) {
        this.profession = profession;
    }

    public Double getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Double integrity) {
        this.integrity = integrity;
    }
}
