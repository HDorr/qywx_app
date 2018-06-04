/**   
* @Title: QyhNoticeVo.java
* @Package com.ziwow.scrmapp.qyh.vo
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-30 上午10:45:36
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: QyhNoticeVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-30 上午10:45:36
 * 
 */
public class QyhNoticeVo implements Serializable{

	 /**
	 * 
	 */
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
     * 是否保密 0:关闭(不保密)  1:开启(保密)
     */
    private Integer isSecrecy;
    
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 公告内容
     */
    private String noticeContent;
    
    /**
     * 是否可评论  0:否  1:是
     */
    private Integer isComment;

    /**
     * 点赞功能是否开启  0:否  1:是
     */
    private Integer isFabulous;
    
    /**
     * 保密水印背景图
     */
    private String watermarkImg;
    
    /**
     * 浏览量
     */
    private Integer viewCount;
    
    /**
     * 是否收藏
     */
    private boolean isCollection;
    
    /**
     * 未读人数
     */
    private Integer unReadCount;
    
    /**
     * 用户ID
     */
    private String userId;

    private String url;
    
    
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the noticeId
	 */
	public String getNoticeId() {
		return noticeId;
	}

	/**
	 * @param noticeId the noticeId to set
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * @return the noticeTitle
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * @param noticeTitle the noticeTitle to set
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * @return the isSecrecy
	 */
	public Integer getIsSecrecy() {
		return isSecrecy;
	}

	/**
	 * @param isSecrecy the isSecrecy to set
	 */
	public void setIsSecrecy(Integer isSecrecy) {
		this.isSecrecy = isSecrecy;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the noticeContent
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * @param noticeContent the noticeContent to set
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * @return the isComment
	 */
	public Integer getIsComment() {
		return isComment;
	}

	/**
	 * @param isComment the isComment to set
	 */
	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	/**
	 * @return the isFabulous
	 */
	public Integer getIsFabulous() {
		return isFabulous;
	}

	/**
	 * @param isFabulous the isFabulous to set
	 */
	public void setIsFabulous(Integer isFabulous) {
		this.isFabulous = isFabulous;
	}

	/**
	 * @return the watermarkImg
	 */
	public String getWatermarkImg() {
		return watermarkImg;
	}

	/**
	 * @param watermarkImg the watermarkImg to set
	 */
	public void setWatermarkImg(String watermarkImg) {
		this.watermarkImg = watermarkImg;
	}

	/**
	 * @return the viewCount
	 */
	public Integer getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @return the isCollection
	 */
	public boolean isCollection() {
		return isCollection;
	}

	/**
	 * @param isCollection the isCollection to set
	 */
	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

	/**
	 * @return the unReadCount
	 */
	public Integer getUnReadCount() {
		return unReadCount;
	}

	/**
	 * @param unReadCount the unReadCount to set
	 */
	public void setUnReadCount(Integer unReadCount) {
		this.unReadCount = unReadCount;
	}
    
    
    
}
