package com.ziwow.scrmapp.qyh.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QyhNotice implements Serializable {
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
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型  与t_qyh_notice_type相关联
     */
    private Integer noticeType;

    /**
     * 是否保密 0:关闭(不保密)  1:开启(保密)
     */
    private Integer isSecrecy;

    /**
     * 是否发布  0:未发布(草稿)   1:已发布 2:提交预览
     */
    private Integer isRelease;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 摘要
     */
    private String noticeAbstract;

    /**
     * 封面图片URL
     */
    private String coverImgUrl;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告作者
     */
    private String noticeAuthor;

    /**
     * 公告发布人
     */
    private String noticePublisher;

    /**
     * 阅读权限 1:全公司  2:指定对象
     */
    private Integer noticeReadAuth;

    /**
     * 是否可评论  0:否  1:是
     */
    private Integer isComment;

    /**
     * 点赞功能是否开启  0:否  1:是
     */
    private Integer isFabulous;

    /**
     * 预览功能是否开启  0:否   1:是
     */
    private Integer isPreview;

    /**
     * 预览说明
     */
    private String previewExplain;

    /**
     * 预览人员
     */
    private String previewPersonnel;

    /**
     * 发送量
     */
    private Integer sendCount;

    /**
     * 是否推送 0:否   1:是
     */
    private Integer isPush;

    /**
     * 阅读权限(部门列表)
     */
    private String departments;

    /**
     * 阅读权限(人员列表)
     */
    private String userids;

    /**
     * 是否删除  0:未删除  1:已删除
     */
    private Integer noticeStatus;

    /**
     * 公告链接
     */
    private String noticeUrl;

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
     * @return 公告标题
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * @param noticeTitle 
	 *            公告标题
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * @return 公告类型  与t_qyh_notice_type相关联
     */
    public Integer getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType 
	 *            公告类型  与t_qyh_notice_type相关联
     */
    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * @return 是否保密 0:关闭(不保密)  1:开启(保密)
     */
    public Integer getIsSecrecy() {
        return isSecrecy;
    }

    /**
     * @param isSecrecy 
	 *            是否保密 0:关闭(不保密)  1:开启(保密)
     */
    public void setIsSecrecy(Integer isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    /**
     * @return 是否发布  0:未发布(草稿)   1:已发布 2:提交预览
     */
    public Integer getIsRelease() {
        return isRelease;
    }

    /**
     * @param isRelease 
	 *            是否发布  0:未发布(草稿)   1:已发布 2:提交预览
     */
    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 
	 *            更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * @return 摘要
     */
    public String getNoticeAbstract() {
        return noticeAbstract;
    }

    /**
     * @param noticeAbstract 
	 *            摘要
     */
    public void setNoticeAbstract(String noticeAbstract) {
        this.noticeAbstract = noticeAbstract;
    }

    /**
     * @return 封面图片URL
     */
    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    /**
     * @param coverImgUrl 
	 *            封面图片URL
     */
    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    /**
     * @return 公告内容
     */
    public String getNoticeContent() {
        return noticeContent;
    }

    /**
     * @param noticeContent 
	 *            公告内容
     */
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /**
     * @return 公告作者
     */
    public String getNoticeAuthor() {
        return noticeAuthor;
    }

    /**
     * @param noticeAuthor 
	 *            公告作者
     */
    public void setNoticeAuthor(String noticeAuthor) {
        this.noticeAuthor = noticeAuthor;
    }

    /**
     * @return 公告发布人
     */
    public String getNoticePublisher() {
        return noticePublisher;
    }

    /**
     * @param noticePublisher 
	 *            公告发布人
     */
    public void setNoticePublisher(String noticePublisher) {
        this.noticePublisher = noticePublisher;
    }

    /**
     * @return 阅读权限 1:全公司  2:指定对象
     */
    public Integer getNoticeReadAuth() {
        return noticeReadAuth;
    }

    /**
     * @param noticeReadAuth 
	 *            阅读权限 1:全公司  2:指定对象
     */
    public void setNoticeReadAuth(Integer noticeReadAuth) {
        this.noticeReadAuth = noticeReadAuth;
    }

    /**
     * @return 是否可评论  0:否  1:是
     */
    public Integer getIsComment() {
        return isComment;
    }

    /**
     * @param isComment 
	 *            是否可评论  0:否  1:是
     */
    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    /**
     * @return 点赞功能是否开启  0:否  1:是
     */
    public Integer getIsFabulous() {
        return isFabulous;
    }

    /**
     * @param isFabulous 
	 *            点赞功能是否开启  0:否  1:是
     */
    public void setIsFabulous(Integer isFabulous) {
        this.isFabulous = isFabulous;
    }

    /**
     * @return 预览功能是否开启  0:否   1:是
     */
    public Integer getIsPreview() {
        return isPreview;
    }

    /**
     * @param isPreview 
	 *            预览功能是否开启  0:否   1:是
     */
    public void setIsPreview(Integer isPreview) {
        this.isPreview = isPreview;
    }

    /**
     * @return 预览说明
     */
    public String getPreviewExplain() {
        return previewExplain;
    }

    /**
     * @param previewExplain 
	 *            预览说明
     */
    public void setPreviewExplain(String previewExplain) {
        this.previewExplain = previewExplain;
    }

    /**
     * @return 预览人员
     */
    public String getPreviewPersonnel() {
        return previewPersonnel;
    }

    /**
     * @param previewPersonnel 
	 *            预览人员
     */
    public void setPreviewPersonnel(String previewPersonnel) {
        this.previewPersonnel = previewPersonnel;
    }

    /**
     * @return 发送量
     */
    public Integer getSendCount() {
        return sendCount;
    }

    /**
     * @param sendCount 
	 *            发送量
     */
    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    /**
     * @return 是否推送 0:否   1:是
     */
    public Integer getIsPush() {
        return isPush;
    }

    /**
     * @param isPush 
	 *            是否推送 0:否   1:是
     */
    public void setIsPush(Integer isPush) {
        this.isPush = isPush;
    }

    /**
     * @return 阅读权限(部门列表)
     */
    public String getDepartments() {
        return departments;
    }

    /**
     * @param departments 
	 *            阅读权限(部门列表)
     */
    public void setDepartments(String departments) {
        this.departments = departments;
    }

    /**
     * @return 阅读权限(人员列表)
     */
    public String getUserids() {
        return userids;
    }

    /**
     * @param userids 
	 *            阅读权限(人员列表)
     */
    public void setUserids(String userids) {
        this.userids = userids;
    }

    /**
     * @return 是否删除  0:未删除  1:已删除
     */
    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    /**
     * @param noticeStatus 
	 *            是否删除  0:未删除  1:已删除
     */
    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    /**
     * @return 公告链接
     */
    public String getNoticeUrl() {
        return noticeUrl;
    }

    /**
     * @param noticeUrl 
	 *            公告链接
     */
    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }
}