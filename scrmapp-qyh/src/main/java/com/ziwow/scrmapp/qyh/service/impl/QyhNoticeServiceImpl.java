package com.ziwow.scrmapp.qyh.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.persistence.entity.QyhUser;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.qyh.constants.QyhConstant;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthAgent;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNotice;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhNoticeType;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhAuthAgentMapper;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhNoticeMapper;
import com.ziwow.scrmapp.qyh.service.QyhNoticeService;
import com.ziwow.scrmapp.qyh.service.QyhNoticeTypeService;
import com.ziwow.scrmapp.qyh.service.QyhWxSaaSService;
import com.ziwow.scrmapp.qyh.vo.QyhNoticeVo;
import com.ziwow.scrmapp.qyh.vo.QyhSendTextVo;
import com.ziwow.scrmapp.tools.oss.OSSUtil;
import com.ziwow.scrmapp.tools.utils.ImageMarkUtil;
import com.ziwow.scrmapp.tools.utils.StringUtil;

/**
 * @ClassName: QyhNoticeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2017-3-28 上午11:15:58
 * 
 */
@Service
public class QyhNoticeServiceImpl extends GenericServiceImpl<QyhNotice, Long> implements QyhNoticeService{
	private Logger logger = LoggerFactory.getLogger(QyhNoticeServiceImpl.class);

	@Resource
	private QyhNoticeMapper qyhNoticeMapper;
	
	@Resource
	private QyhAuthAgentMapper qyhAuthAgentMapper;
	
	
	@Resource
	private QyhNoticeTypeService qyhNoticeTypeService;
	
	@Resource
	private QyhWxSaaSService qyhWxSaaSService;
	
	@Value("${qyh.mdgl.suitId}")
	private String suiteId;
	
	@Value("${qyh.open.corpid}")
	private String corpId;
	
	@Value("${qyh.xwgg.appId}")
	private String appId;
	
	@Override
	public QyhNotice getQyhNoticeByNoticeId(String noticeId) {
		return qyhNoticeMapper.getQyhNoticeByNoticeId(noticeId);
	}

	@Override
	public boolean checkViewNoticeIsPermissions(QyhUser qyhUser,
			QyhNotice qyhNotice) {
		boolean flag = false;
		//特殊情况：指定为@all，则向关注该企业应用的全部成员发送
		try{
			if(qyhNotice.getUserids().equals("@all")){
				return true;
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		//先比较当个用户
		//String [] userIdsArr = qyhUser.getUserid().split("\\|");
		String [] noticeUserIdsArr =null;
		try{
			noticeUserIdsArr = qyhNotice.getUserids().split("\\|");
			for(int j=0;j<noticeUserIdsArr.length;j++){
				logger.info("单个用户比较"+qyhUser.getUserid()+"      "+noticeUserIdsArr[j]);
				if(qyhUser.getUserid().equals(noticeUserIdsArr[j])){
					flag=true;
					break ;
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		//部门比较
		if(!flag){
			String [] userDepartmentsArr = qyhUser.getDepartments().split(",");
			String [] noticeDepartmentsArr=null;
			try{
				noticeDepartmentsArr = qyhNotice.getDepartments().split("\\|");
				OK:
					for(int i=0;i<userDepartmentsArr.length;i++){
						for(int j=0;j<noticeDepartmentsArr.length;j++){
							logger.info("部门比较"+userDepartmentsArr[i]+"      "+noticeDepartmentsArr[j]);
							if(userDepartmentsArr[i].equals(noticeDepartmentsArr[j])){
								flag=true;
								break OK;
							}
							
						}
						
					}
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		}
		
		return flag;
	}

	@Override
	public String generateWatermarkImg(String basePath,String name,String userId) {
		basePath=basePath+"/resources/images/qyhnoticebg.png";
		ByteArrayOutputStream os = null;
		try {
			// 生成图片
			os = new ByteArrayOutputStream();  
			ImageIO.write(ImageMarkUtil.pressText(name, basePath, "微软雅黑", "", 36, 1, true), "PNG", os);  
			InputStream is = new ByteArrayInputStream(os.toByteArray());  
			String ossFileUrl = OSSUtil.uploadFile(is, "png");
			return ossFileUrl;
		} catch (Exception e) {
			logger.info("生成图片:[{}]",e);
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				os=null;
			}
		}
		return "";
	}

	@Override
	public PageInfo<QyhNoticeVo> getQyhNoticeByType(Integer assortmentId,int pageNum,int pageSize,String noticeTitle) {
		//根据assortmentId查找是否一级分类
		QyhNoticeType noticeType = qyhNoticeTypeService.selectByPrimaryKey(assortmentId);
		
		if(noticeType !=null){
			String assortmentIds = "";
			assortmentIds = StringUtils.join(qyhNoticeTypeService.getQyhNoticeTypeByAssortmentParentId(assortmentId), ",");
			if(!StringUtil.isNotBlank(assortmentIds)){
				assortmentIds = String.valueOf(noticeType.getAssortmentId());
			}
			PageHelper.startPage(pageNum, pageSize);
			return new PageInfo<QyhNoticeVo>(qyhNoticeMapper.getQyhNoticeByType(assortmentIds, noticeTitle));
		}
		return null;
	}

	@Override
	public PageInfo<QyhNoticeVo> getAllNoticeList(int pageNum, int pageSize,String noticeTitle) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<QyhNoticeVo>(qyhNoticeMapper.getAllNoticeList(noticeTitle));
	}

	@Override
	public PageInfo<QyhNoticeVo> getMyCollectionNotice(int pageNum,
			int pageSize, String noticeTitle, String userId) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<QyhNoticeVo>(qyhNoticeMapper.getMyCollectionNotice(noticeTitle, userId));
	}
	
	
	private JSONObject qyhSendMsgNews(QyhSendTextVo qyhSendTextVo,String suiteId, String corpId){
		JSONObject news = new JSONObject();
		
		JSONObject content = new JSONObject();
		news.put("touser", qyhSendTextVo.getTouser());
		news.put("toparty", qyhSendTextVo.getToparty());
		news.put("totag", qyhSendTextVo.getTotag());
		news.put("msgtype", "text");
		news.put("agentid", qyhSendTextVo.getAgentid());
		
		content.put("content", qyhSendTextVo.getContent());
		news.put("text", content);
		news.put("safe", qyhSendTextVo.getSafe());
		try{
			String jsonStr = HttpKit.post(QyhConstant.SEND_MESSAGE.replace("ACCESS_TOKEN", qyhWxSaaSService.getCorpAccessToken(suiteId, corpId)), news.toString());
			logger.info("发送企业号通知，返回结果[{}]",jsonStr);
			return JSONObject.parseObject(jsonStr);
		} catch (Exception e) {

		}
		
		return null;
	}

	@Override
	public void qyhSendMsgText(String toUser, String content) {
		QyhAuthAgent qyhAuthAgent = qyhAuthAgentMapper.getQyhAuthAgentByAppId(corpId, appId, suiteId);
		if (qyhAuthAgent != null) {
			QyhSendTextVo qyhSendTextVo = new QyhSendTextVo();
			qyhSendTextVo.setAgentid(qyhAuthAgent.getAgentid());
			qyhSendTextVo.setContent(content);
			qyhSendTextVo.setTouser(toUser);
			qyhSendTextVo.setSafe(0);
			qyhSendMsgNews(qyhSendTextVo, suiteId, corpId);
		} else {
			logger.info("没有查到应用信息");
		}
	}
}