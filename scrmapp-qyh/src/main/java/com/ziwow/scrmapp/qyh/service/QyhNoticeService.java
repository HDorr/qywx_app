/**   
* @Title: QyhNoticeService.java
* @Package com.ziwow.scrmapppc.service
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-28 上午11:15:24
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.service;

import com.github.pagehelper.PageInfo;
import com.ziwow.scrmapp.common.bean.GenericService;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNotice;
import com.ziwow.scrmapp.qyh.vo.QyhNoticeVo;

/**
 * @ClassName: QyhNoticeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:15:24
 * 
 */
public interface QyhNoticeService extends GenericService<QyhNotice, Long>{

	public QyhNotice getQyhNoticeByNoticeId(String noticeId);
	
	public boolean checkViewNoticeIsPermissions(QyhUser qyhUser,QyhNotice qyhNotice);
	
	public String generateWatermarkImg(String basePath,String name,String userId);
	
	public PageInfo<QyhNoticeVo> getQyhNoticeByType(Integer assortmentId,int pageNum,int pageSize,String noticeTitle);
	
	public PageInfo<QyhNoticeVo> getAllNoticeList(int pageNum,int pageSize,String noticeTitle);
	
	public PageInfo<QyhNoticeVo> getMyCollectionNotice(int pageNum,int pageSize,String noticeTitle,String userId);
	
	/**
	 * 企业号发送文本消息
	* @Title: qyhSendMsgText
	* @param @param toUser		成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	* @param @param content		消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
	* @param @return    设定文件
	* @version 1.0
	* @author Hogen.hu
	 */
	public void qyhSendMsgText(String toUser,String content);
}
