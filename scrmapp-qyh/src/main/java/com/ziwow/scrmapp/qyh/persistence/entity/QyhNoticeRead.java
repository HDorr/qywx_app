package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhNoticeRead implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 公告编号
     */
    private String noticeId;

    /**
     * 成员ID
     */
    private String userId;

    /**
     * 是否已阅读  0:否  1:是
     */
    private Integer isRead;

    /**
     * 背景图片
     */
    private String backgroundImgUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间(阅读时间)
     */
    private Date updateTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 
	 *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 公告编号
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * @param noticeId 
	 *            公告编号
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * @return 成员ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId 
	 *            成员ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return 是否已阅读  0:否  1:是
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead 
	 *            是否已阅读  0:否  1:是
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * @return 背景图片
     */
    public String getBackgroundImgUrl() {
        return backgroundImgUrl;
    }

    /**
     * @param backgroundImgUrl 
	 *            背景图片
     */
    public void setBackgroundImgUrl(String backgroundImgUrl) {
        this.backgroundImgUrl = backgroundImgUrl;
    }

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 修改时间(阅读时间)
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 
	 *            修改时间(阅读时间)
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}