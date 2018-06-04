/**   
* @Title: QyhNoticeCollectionService.java
* @Package com.ziwow.scrmapppc.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:21:14
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeCollection;

/**
 * @ClassName: QyhNoticeCollectionService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:21:14
 * 
 */
public interface QyhNoticeCollectionService extends GenericService<QyhNoticeCollection, Long>{

	public boolean isCollectionNotice(String noticeId,String userId);
	
	public int deleteCollectionNotice(String noticeId,String userId);
}
