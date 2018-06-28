/**   
* @Title: QyhNoticeController.java
* @Package com.ziwow.scrmapp.qyh.controller
* @Description: TODO(用一句话描述该文件做什么)
* @author hogen  
* @date 2017-3-29 下午2:14:22
* @version V1.0   
*/
package com.ziwow.scrmapp.qyh.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ziwow.scrmapp.common.constants.ErrorCodeConstants;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNotice;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeCollection;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeRead;
import com.ziwow.scrmapp.qyh.service.QyhNoticeCollectionService;
import com.ziwow.scrmapp.qyh.service.QyhNoticeReadService;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;
import com.ziwow.scrmapp.qyh.service.QyhUserService;
import com.ziwow.scrmapp.qyh.vo.QyhNoticeVo;
import com.ziwow.scrmapp.tools.utils.Base64;
import com.ziwow.scrmapp.tools.utils.StringUtil;

/**
 * @ClassName: QyhNoticeController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-29 下午2:14:22
 * 
 */
@Controller
@RequestMapping("/qyhNotice")
public class QyhNoticeController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(QyhNoticeController.class);
	
	@Resource
	private QyhUserService qyhUserService;
	
	@Resource
	private QyhNoticeService qyhNoticeService;
	
	@Resource
	private QyhNoticeReadService qyhNoticeReadService;
	
	@Resource
	private QyhNoticeCollectionService qyhNoticeCollectionService;
	
	
	/**
	 * 员工查看新闻公告接口
	* @Title: viewNotice
	* @param @param code
	* @param @param noticeId
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/viewNotice",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView viewNotice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("code")String code,@RequestParam("noticeId")String noticeId){
		logger.info("员工查看新闻公告接口,传入参数,code=[{}],noticeId=[{}]",code,noticeId);
		//先获取授权员工信息
		QyhUser qyhUser = qyhUserService.getOAuthQyhUserInfo(code, request, response);
		logger.info("企业号获取员工授权信息,[{}]",JSON.toJSON(qyhUser));
		Integer errCode = ErrorCodeConstants.CODE_0;
		Object obj = null;
		if(null !=qyhUser){
			switch (qyhUser.getStatus()) {
			case 2:
				errCode = ErrorCodeConstants.CODE_40002;
				break;
			case 4:
				errCode = ErrorCodeConstants.CODE_40003;
				break;
			default:
				//通过noticeId查询该通知信息
				QyhNotice qyhNotice = qyhNoticeService.getQyhNoticeByNoticeId(noticeId);
				if(null !=qyhNotice){
					if(qyhNotice.getNoticeStatus()==0){
						//权限校验
						boolean flag=qyhNoticeService.checkViewNoticeIsPermissions(qyhUser, qyhNotice);
						logger.info("[{}],公告id=[{}],权限为[{}]",qyhUser.getUserid(),qyhNotice.getNoticeId(),flag);
						if(flag){
							String basePath = request.getSession().getServletContext().getRealPath("");
							//拼装数据,浏览量，标题，收藏，未读人数
							QyhNoticeVo qyhNoticeVo = new QyhNoticeVo();
							qyhNoticeVo.setCreateTime(qyhNotice.getCreateTime());
							qyhNoticeVo.setUpdateTime(qyhNotice.getUpdateTime());
							qyhNoticeVo.setId(qyhNotice.getId());
							qyhNoticeVo.setIsComment(qyhNotice.getIsComment());
							qyhNoticeVo.setIsFabulous(qyhNotice.getIsFabulous());
							qyhNoticeVo.setIsSecrecy(qyhNotice.getIsSecrecy());
							qyhNoticeVo.setNoticeId(qyhNotice.getNoticeId());
							qyhNoticeVo.setNoticeContent(qyhNotice.getNoticeContent());
							qyhNoticeVo.setNoticeTitle(qyhNotice.getNoticeTitle());
							qyhNoticeVo.setUserId(Base64.encode(qyhUser.getUserid().getBytes()));
							//查询水印是否已经生成
							QyhNoticeRead qyhNoticeRead = qyhNoticeReadService.getQyhNoticeReadByNoticeIdAndUserId(qyhNotice.getNoticeId(), qyhUser.getUserid());
							if(qyhNoticeRead !=null){
								if(qyhNotice.getIsSecrecy().intValue() == 1){//保密
									if(StringUtil.isNotBlank(qyhNoticeRead.getBackgroundImgUrl())){
										qyhNoticeVo.setWatermarkImg(qyhNoticeRead.getBackgroundImgUrl());
									}else{
										//此时需要生成水印
										String url = qyhNoticeService.generateWatermarkImg(basePath,qyhUser.getName(), qyhUser.getUserid());
										qyhNoticeVo.setWatermarkImg(url);
									}
								}
								//更新已读
								if(qyhNoticeRead.getIsRead()==0){
									qyhNoticeRead.setUpdateTime(new Date());
									qyhNoticeRead.setIsRead(1);
									qyhNoticeRead.setBackgroundImgUrl(qyhNoticeVo.getWatermarkImg());
									qyhNoticeReadService.updateByPrimaryKeySelective(qyhNoticeRead);
								}
							}else{
								if(qyhNotice.getIsSecrecy().intValue() == 1){//保密
									//此时需要生成水印
									String url = qyhNoticeService.generateWatermarkImg(basePath,qyhUser.getName(), qyhUser.getUserid());
									qyhNoticeVo.setWatermarkImg(url);
								}
								//插入已读记录
								QyhNoticeRead qnr = new QyhNoticeRead();
								qnr.setBackgroundImgUrl(qyhNoticeVo.getWatermarkImg());
								qnr.setCreateTime(new Date());
								qnr.setUpdateTime(new Date());
								qnr.setIsRead(1);
								qnr.setNoticeId(qyhNotice.getNoticeId());
								qnr.setUserId(qyhUser.getUserid());
								qyhNoticeReadService.insertSelective(qnr);
							}
							logger.info("公告id=[{}],userId=[{}],生成保密图片url=[{}]",qyhNotice.getNoticeId(),qyhUser.getUserid(),qyhNoticeVo.getWatermarkImg());
							//此条消息是否发布人与浏览者同一个人
							if(qyhNotice.getNoticeAuthor().equals(qyhUser.getUserid())){
								logger.info("[{}]标题,[{}]浏览者也是发布者，需要显示未读人数",qyhNotice.getNoticeId(),qyhUser.getUserid());
								//这里需要统计未读人数
								qyhNoticeVo.setUnReadCount(qyhNoticeReadService.getCountQyhNoticeRead(qyhNotice.getNoticeId(), 0));
							}
							//浏览量
							qyhNoticeVo.setViewCount(qyhNoticeReadService.getCountQyhNoticeRead(qyhNotice.getNoticeId(), 1));
							//是否收藏
							qyhNoticeVo.setCollection(qyhNoticeCollectionService.isCollectionNotice(qyhNotice.getNoticeId(), qyhUser.getUserid()));
							obj = qyhNoticeVo;
						}else{
							errCode = ErrorCodeConstants.CODE_40005;
						}
					}else{
						errCode = ErrorCodeConstants.CODE_40004;
					}
				}else{
					errCode = ErrorCodeConstants.CODE_40006;
				}
				break;
			}
		}else{
			errCode = ErrorCodeConstants.CODE_40001;
		}
		if(errCode!=0){
			return new ModelAndView("/innerNotice/innerNotice_error",this.rtnParam(errCode, obj));
		}
		return new ModelAndView("/innerNotice/innerNotice_normal_collection",this.rtnParam(errCode, obj));
	}
	
	/**
	 * 收藏或取消收藏
	* @Title: noticeCollection
	* @param @param userId
	* @param @param noticeId
	* @param @param flag  0:收藏，1：取消收藏
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/noticeCollection",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> noticeCollection(@RequestParam("userId")String userId,@RequestParam("noticeId")String noticeId){
		Integer errCode = ErrorCodeConstants.CODE_1;
		Object obj = null;
		try {
			logger.info("收藏或取消收藏，解密前userId=[{}]",userId);
			userId = new String(Base64.decode(userId));
			logger.info("收藏或取消收藏,userId=[{}],noticeId=[{}]",userId,noticeId);
			boolean flag = qyhNoticeCollectionService.isCollectionNotice(noticeId, userId);
			if(!flag){//收藏
				QyhNoticeCollection record = new QyhNoticeCollection();
				record.setNoticeId(noticeId);
				record.setUserId(userId);
				record.setCreateTime(new Date());
				qyhNoticeCollectionService.insertSelective(record);
				errCode = ErrorCodeConstants.CODE_40007;
			}else{//取消收藏
				qyhNoticeCollectionService.deleteCollectionNotice(noticeId, userId);
				errCode = ErrorCodeConstants.CODE_40009;
			}
			
		} catch (Exception e) {
			logger.error("userId解密失败");
		}
		
		return this.rtnParam(errCode, obj);
	}
	
	/**
	 * 查看未读人员列表
	* @Title: getUnReadUserByNotice
	* @param @param userId
	* @param @param noticeId
	* @param @param pageNum
	* @param @param pageIndex
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/getUnReadUserByNotice",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUnReadUserByNotice(@RequestParam("userId")String userId,@RequestParam("noticeId")String noticeId,
			@RequestParam(value="pageNum",required=false,defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize){
		Integer errCode = ErrorCodeConstants.CODE_1;
		Object obj = null;
		try{
			userId = new String(Base64.decode(userId));
			//通过noticeId查询该通知信息
			QyhNotice qyhNotice = qyhNoticeService.getQyhNoticeByNoticeId(noticeId);
			if(qyhNotice !=null){
				if(qyhNotice.getNoticeAuthor().equals(userId)){
					errCode = ErrorCodeConstants.CODE_0;
					obj = qyhNoticeReadService.getUnReadeUserList(noticeId, pageSize, pageNum);
				}
			}
		}catch (Exception e) {
			logger.error("userId解密失败"+e.getMessage(),e);
			errCode = ErrorCodeConstants.CODE_3;
		}
		
		return this.rtnParam(errCode, obj);
	}
	
	/**
	 * 点击菜单，查找分类
	* @Title: getNoticeListByType
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/getNoticeListByType",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNoticeListByType(@RequestParam("assortmentId")Integer assortmentId,@RequestParam("typeTitle")String typeTitle,
			@RequestParam(value="pageNum",required=false,defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
			@RequestParam(value="noticeTitle",required=false,defaultValue="")String noticeTitle){
		logger.info("点击菜单，查找分类,传入参数assortmentId=[{}],typeTitle=[{}],noticeTitle=[{}]",assortmentId,typeTitle,noticeTitle);
		PageInfo<QyhNoticeVo> pageInfo =qyhNoticeService.getQyhNoticeByType(assortmentId, pageNum, pageSize,noticeTitle);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("data", pageInfo);
		returnMap.put("typeTitle", typeTitle);
		returnMap.put("assortmentId", assortmentId);
		returnMap.put("type", 1);
		return this.rtnParam(ErrorCodeConstants.CODE_0, returnMap);
	}
	
	
	/**
	 * 查询所有公告
	* @Title: getAllNoticeList
	* @param @param pageNum
	* @param @param pageSize
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/getAllNoticeList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllNoticeList(@RequestParam(value="pageNum",required=false,defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
			@RequestParam(value="noticeTitle",required=false,defaultValue="")String noticeTitle){
		PageInfo<QyhNoticeVo> pageInfo =qyhNoticeService.getAllNoticeList(pageNum, pageSize,noticeTitle);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("data", pageInfo);
		returnMap.put("typeTitle", "全部公告");
		returnMap.put("type", 2);
		return this.rtnParam(ErrorCodeConstants.CODE_0, returnMap); 
	}
	
	
	/**
	 * 点击我的收藏列表
	* @Title: getMyCollectionNotice
	* @param @param request
	* @param @param response
	* @param @param code
	* @param @param pageNum
	* @param @param pageSize
	* @param @param noticeTitle
	* @param @return    设定文件
	* @return Map<String,Object>    返回类型
	* @version 1.0
	* @author Hogen.hu
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/getMyCollectionNotice",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMyCollectionNotice(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="pageNum",required=false,defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
			@RequestParam(value="noticeTitle",required=false,defaultValue="")String noticeTitle,
			@RequestParam("userId")String userId){
		Integer errCode = ErrorCodeConstants.CODE_1;
		Object obj = null;
		
		try{
			userId = new String(Base64.decode(userId));
			PageInfo<QyhNoticeVo> pageInfo =qyhNoticeService.getMyCollectionNotice(pageNum, pageSize, noticeTitle, userId);
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("data", pageInfo);
			returnMap.put("userId", userId);
			returnMap.put("typeTitle", "我的收藏");
			returnMap.put("type", 3);
			obj = returnMap;
			errCode = ErrorCodeConstants.CODE_0;
		}catch (Exception e) {
			errCode = ErrorCodeConstants.CODE_3;
		}
		return this.rtnParam(errCode, obj); 
	}
	
	@RequestMapping(value="/getMyCollectionNoticePage",method=RequestMethod.GET)
	public ModelAndView getMyCollectionNoticePage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("code")String code){
		//先获取授权员工信息
		QyhUser qyhUser = null;
		String userId="";
		if(StringUtil.isNotBlank(code)){
			qyhUser = qyhUserService.getOAuthQyhUserInfo(code, request, response);
			logger.info("企业号获取员工授权信息,[{}]",JSON.toJSON(qyhUser));
			userId = qyhUser.getUserid();
		}
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("userId", Base64.encode(userId.getBytes()));
		returnMap.put("typeTitle", "我的收藏");
		returnMap.put("type", 3);
		return new ModelAndView("/innerNotice/noticeDetail_default",returnMap); 
	}
	
	
	@RequestMapping(value="/jumpInnerNoticeUnreadNumPage",method=RequestMethod.GET)
	public ModelAndView jumpInnerNoticeUnreadNumPage(@RequestParam("userId")String userId,@RequestParam("noticeId")String noticeId){
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("userId", userId);
		maps.put("noticeId", noticeId);
		return new ModelAndView("/innerNotice/innerNotice_unreadNum",maps);
	}
	
	/**
	 * 跳转分类页
	* @Title: jumpNoticeDetailDefaultPage
	* @param @param assortmentId
	* @param @param typeTitle
	* @param @param type 1:分类页，2：全部公告，
	* @param @return    设定文件
	* @return ModelAndView    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	@RequestMapping(value="/jumpNoticeDetailDefaultPage",method=RequestMethod.GET)
	public ModelAndView jumpNoticeDetailDefaultPage(@RequestParam(value="assortmentId",required=false,defaultValue="")String assortmentId,@RequestParam(value="typeTitle",required=false,defaultValue="")String typeTitle
			,@RequestParam("type")int type){
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("assortmentId", assortmentId);
		switch (type) {
		case 2:
			typeTitle="全部公告";
			break;
		default:
			//typeTitle="我的收藏";
			break;
		}
		maps.put("typeTitle", typeTitle);
		maps.put("type", type);
		return new ModelAndView("/innerNotice/noticeDetail_default",maps);
	}
	
	
	
}
