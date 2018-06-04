/**   
* @Title: TAuthCorpService.java
* @Package com.ziwow.qyhapp.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-3 下午4:56:57
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthCorp;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;

/**
 * @ClassName: TAuthCorpService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:56:57
 * 
 */
public interface QyhAuthCorpService extends GenericService<QyhAuthCorp, String>{

	public QyhAuthCorp checkAuthCorpExist(String corpId,String suiteId);
	
	public void cancelAuth(String suiteId,String authCorpId);
	
	public void qyWXSaasAuthCallbackSave(PermanentCodeVo permanentCodeVo,String suiteId );
	
	public String getPermanentCodeByCorpId(String corpId,String suiteId);
}
