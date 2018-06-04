/**   
* @Title: TDepartmentService.java
* @Package com.ziwow.qyhapp.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2016-12-7 上午10:28:37
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import java.util.List;

import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhDepartment;
import com.ziwow.scrmapp.qyh.vo.WxSaaSCallbackInfo;

/**
 * @ClassName: TDepartmentService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-7 上午10:28:37
 * 
 */
public interface QyhDepartmentService extends GenericService<QyhDepartment, Long> {
	public void batchInsertDepartment(List<QyhDepartment> list);
	public void batchUpdateDepartment(List<QyhDepartment> list);
	public void batchDeleteDepartment(List<Integer> departmentId,String corpId);
	void saveDepartment(WxSaaSCallbackInfo wxSaaSCallbackInfo);
	void updateDepartment(WxSaaSCallbackInfo wxSaaSCallbackInfo);
	void deleteDepartment(Long id);
	public void contactSync(String suiteId,  String authCorpid);
	public QyhDepartment getDepartmentByName(String deptName, String corpId);
}
