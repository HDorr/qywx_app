/**   
* @Title: QyhNoticeReadService.java
* @Package com.ziwow.scrmapppc.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:25:13
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.github.pagehelper.PageInfo;
import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeRead;

/**
 * @ClassName: QyhNoticeReadService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:25:13
 * 
 */
public interface QyhNoticeReadService extends GenericService<QyhNoticeRead, Long>{

	public QyhNoticeRead getQyhNoticeReadByNoticeIdAndUserId(String noticeId,String userId);
	
	public Integer getCountQyhNoticeRead(String noticeId,int isRead);
	
	public PageInfo<QyhUser> getUnReadeUserList(String noticeId,int pageSize,int pageNum);
	
}
