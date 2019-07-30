/**   
* @Title: TAuthAgentService.java
* @Package com.ziwow.qyhapp.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-3 下午4:59:40
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthAgent;

/**
 * @ClassName: TAuthAgentService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:59:40
 * 
 */
public interface QyhAuthAgentService extends GenericService<QyhAuthAgent, Long>{

	public boolean checkAuthAgenExist(String appid,String authcorpid);
	
	public void changeAuth(String suiteId,String authCorpId);


}
