/**   
* @Title: TSuiteService.java
* @Package com.ziwow.qyhapp.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-3 下午3:07:10
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhSuite;

/**
 * @ClassName: TSuiteService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午3:07:10
 * 
 */
public interface QyhSuiteService extends GenericService<QyhSuite, String>{

	public void insertOrUpdateSuite(QyhSuite suite);
}
