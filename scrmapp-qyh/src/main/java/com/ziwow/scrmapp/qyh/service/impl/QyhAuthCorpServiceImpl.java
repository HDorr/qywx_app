/**   
 * @Title: TAuthCorpServiceImpl.java
 * @Package com.ziwow.qyhapp.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-12-3 下午4:57:24
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ziwow.scrmapp.common.bean.GenericServiceImpl;
import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.qyh.constants.RedisKeyConstants;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthAgent;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhAuthCorp;
import com.ziwow.scrmapp.qyh.persistence.mapper.QyhAuthCorpMapper;
import com.ziwow.scrmapp.qyh.service.QyhAuthAgentService;
import com.ziwow.scrmapp.qyh.service.QyhAuthCorpService;
import com.ziwow.scrmapp.qyh.vo.AgentVo;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;
import com.ziwow.scrmapp.tools.utils.UUIDTool;

/**
 * @ClassName: TAuthCorpServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-12-3 下午4:57:24
 * 
 */
@Service
public class QyhAuthCorpServiceImpl extends GenericServiceImpl<QyhAuthCorp, String> implements QyhAuthCorpService {

	Logger LOG = LoggerFactory.getLogger(QyhAuthCorpServiceImpl.class);
	@Autowired
	private QyhAuthCorpMapper authCorpMapper;

	@Autowired
	private QyhAuthAgentService tAuthAgentService;

	@Autowired
	private RedisService redisService;

	@Override
	public QyhAuthCorp checkAuthCorpExist(String corpId, String suiteId) {
		return authCorpMapper.checkAuthCorpExist(corpId, suiteId);
	}

	@Override
	public void cancelAuth(String suiteId, String authCorpId) {
		QyhAuthCorp authCorp = this.checkAuthCorpExist(authCorpId, suiteId);
		LOG.info("取消授权，id=[{}],suiteId=[{}],authCorpId=[{}]", authCorp.getId(), suiteId, authCorpId);
		authCorpMapper.deleteByPrimaryKey(authCorp.getId());
	}

	@Override
	@Transactional
	public void qyWXSaasAuthCallbackSave(PermanentCodeVo permanentCodeVo, String suiteId) {
		// t_qyh_auth_corp
		QyhAuthCorp authCorp = this.checkAuthCorpExist(permanentCodeVo.getAuth_corp_info().getCorpid(), suiteId);
		QyhAuthCorp tAuthCorp = new QyhAuthCorp();

		tAuthCorp.setCorpid(permanentCodeVo.getAuth_corp_info().getCorpid());
		tAuthCorp.setSuiteid(suiteId);
		tAuthCorp.setCorpname(permanentCodeVo.getAuth_corp_info().getCorp_name());
		tAuthCorp.setSuitepermanentcode(permanentCodeVo.getPermanent_code());
		tAuthCorp.setAuthcorpinfo(permanentCodeVo.getAuthCorpInfo());
		tAuthCorp.setAuthappinfo(permanentCodeVo.getAuthInfo());
		tAuthCorp.setAuthuserinfo(permanentCodeVo.getAuthUserInfo());
		tAuthCorp.setCreatetime(new Date());
		String id = "";
		if (authCorp == null) {
			id = UUIDTool.getUUID();
			tAuthCorp.setId(id);
			this.insertSelective(tAuthCorp);
		} else {
			id = authCorp.getId();
			tAuthCorp.setId(id);
			this.updateByPrimaryKeySelective(tAuthCorp);
		}
		// t_qyh_auth_agent
		for (AgentVo vo : permanentCodeVo.getAuth_info().getAgent()) {
			QyhAuthAgent tAuthAgent = new QyhAuthAgent();
			tAuthAgent.setAppid(vo.getAppid());
			tAuthAgent.setAgentid(vo.getAgentid());
			tAuthAgent.setAuthcorpid(id);
			tAuthAgent.setCreatetime(new Date());
			if (!tAuthAgentService.checkAuthAgenExist(vo.getAppid(), id)) {
				tAuthAgentService.insertSelective(tAuthAgent);
			}

		}
		// 保存缓存
		String key = RedisKeyConstants.PERMANENT_CODE + permanentCodeVo.getAuth_corp_info().getCorpid() + ":" + suiteId;
		redisService.set(key, permanentCodeVo.getPermanent_code());
	}

	@Override
	public String getPermanentCodeByCorpId(String corpId, String suiteId) {
		String permanentCode = "";
		String key = RedisKeyConstants.PERMANENT_CODE + corpId + ":" + suiteId;
		if (redisService.hasKey(key)) {
			permanentCode = (String) redisService.get(key);
		} else {
			QyhAuthCorp authCorp = this.checkAuthCorpExist(corpId, suiteId);
			if (authCorp != null) {
				permanentCode = authCorp.getSuitepermanentcode();
				redisService.set(key, permanentCode);
			}
		}
		LOG.info("获取企业永久码PermanentCode=[{}],corpId=[{}],suiteId=[{}]", permanentCode, corpId, suiteId);
		return permanentCode;
	}
}
