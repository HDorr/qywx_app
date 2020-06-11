package com.ziwow.scrmapp.wechat.persistence.entity;

/**
 * @author xiaohei
 */
public class WxFans {

    private String phone; // 手机号

    private int isCancel; // 是否取消关注，0未取消，1取消

    private String followAt; // 跟进日期

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public String getFollowAt() {
        return followAt;
    }

    public void setFollowAt(String followAt) {
        this.followAt = followAt;
    }
}
