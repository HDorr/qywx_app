package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhNoticeCollection implements Serializable {
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
     * 创建时间(收藏时间)
     */
    private Date createTime;

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
     * @return 创建时间(收藏时间)
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            创建时间(收藏时间)
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}