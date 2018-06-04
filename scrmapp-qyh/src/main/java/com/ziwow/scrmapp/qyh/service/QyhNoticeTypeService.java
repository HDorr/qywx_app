/**   
* @Title: QyhNoticeTypeService.java
* @Package com.ziwow.scrmapppc.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:29:22
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import java.util.List;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeType;

/**
 * @ClassName: QyhNoticeTypeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:29:22
 * 
 */
public interface QyhNoticeTypeService extends GenericService<QyhNoticeType, Integer>{
	
	public List<String> getQyhNoticeTypeByAssortmentParentId(Integer assortmentParentId);
}
