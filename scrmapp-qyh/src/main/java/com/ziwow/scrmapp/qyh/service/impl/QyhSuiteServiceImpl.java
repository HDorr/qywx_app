/**   
 * @Title: TSuiteServiceImpl.java
 * @Package com.ziwow.qyhapp.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-3 下午3:07:20
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.qyh.constants.QyhConstant;
import com.ziwow.scrmapp.qyh.constants.RedisKeyConstants;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhSuite;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhSuiteMapper;
import com.ziwow.scrmapp.qyh.service.QyhSuiteService;

/**
 * @ClassName: TSuiteServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午3:07:20
 * 
 */
@Service
public class QyhSuiteServiceImpl extends GenericServiceImpl<QyhSuite, String>
		implements QyhSuiteService {

	Logger LOG = LoggerFactory.getLogger(QyhSuiteServiceImpl.class);
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private QyhSuiteMapper suiteMapper;
	
	@Override
	public void insertOrUpdateSuite(QyhSuite suite) {
		QyhSuite tSuiteVo = suiteMapper.selectByPrimaryKey(suite.getSuiteid());
		if(tSuiteVo !=null){
			suiteMapper.updateByPrimaryKeySelective(suite);
		}else{
			suiteMapper.insertSelective(suite);
		}
		String suiteTicketKey = RedisKeyConstants.SUITE_TICKET+suite.getSuiteid();
		redisService.set(suiteTicketKey, suite.getSuiteticket(), QyhConstant.SUITETICKET_EXPIRATION_TIME);
	}
}