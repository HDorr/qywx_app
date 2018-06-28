/**   
 * @Title: QyhWxServiceImpl.java
 * @Package com.ziwow.qyhapp.weixin.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-11-29 上午11:01:58
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.common.redis.RedisService;
import com.ziwow.scrmapp.common.utils.HttpKit;
import com.ziwow.scrmapp.qyh.constants.QyhConstant;
import com.ziwow.scrmapp.qyh.constants.RedisKeyConstants;
import com.ziwow.scrmapp.qyh.persistence.entity.QyhSuite;
import com.ziwow.scrmapp.qyh.service.QyhAuthCorpService;
import com.ziwow.scrmapp.qyh.service.QyhSuiteService;
import com.ziwow.scrmapp.qyh.service.QyhWxSaaSService;
import com.ziwow.scrmapp.qyh.vo.AddreddBookDataVo;
import com.ziwow.scrmapp.qyh.vo.AddressBookVo;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;
import com.ziwow.scrmapp.tools.utils.JsonUtils;
import com.ziwow.scrmapp.tools.utils.StringUtil;

/**
 * @ClassName: QyhWxServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-29 上午11:01:58
 * 
 */
@Service
public class QyhWxSaaSServiceImpl implements QyhWxSaaSService {

Logger LOG = LoggerFactory.getLogger(QyhWxSaaSServiceImpl.class);
	
	@Autowired
	private RedisService redisService;
	
	@Autowired 
	private QyhSuiteService qyhSuiteService;
	
	@Autowired
	private QyhAuthCorpService qyhAuthCorpService;
	
	@Override
	public String getSuiteAccessToken(String suiteId) {
		String result = "";
		String suite_access_token="";
		String key = RedisKeyConstants.SUITE_ACCESS_TOKEN+suiteId;
		String suiteTicketKey = RedisKeyConstants.SUITE_TICKET+suiteId;
		try {
			if(!redisService.hasKey(key)){
				JSONObject jsonBody = new JSONObject();
				QyhSuite info = qyhSuiteService.selectByPrimaryKey(suiteId);
				if(info !=null){
					String suiteTicket = "";
					if(redisService.hasKey(suiteTicketKey)){
						suiteTicket = redisService.get(suiteTicketKey).toString();
					}else{
						suiteTicket = info.getSuiteticket();
					}
					String suiteSecret=info.getSuitesecret();
					if(StringUtil.isNotBlank(suiteSecret)){
						jsonBody.put("suite_id",suiteId);
						jsonBody.put("suite_secret",suiteSecret);
						jsonBody.put("suite_ticket",suiteTicket);
						result = HttpKit.post(QyhConstant.SUITE_ACCESS_TOKEN_URL, jsonBody.toString());
						if (StringUtil.isNotBlank(result)) {
							JSONObject object = JSONObject.fromObject(result);
							suite_access_token = object.getString("suite_access_token");
							if(StringUtil.isNotBlank(suite_access_token)){
								redisService.set(key, suite_access_token, 7000L);
							}
						}
					}else{
						LOG.info("suite_access_token,suiteSecret为空");
					}
				}else{
					LOG.info("suite_access_token,套件还未授权");
				}
			}else{
				suite_access_token = (String) redisService.get(key);
			}
			LOG.info("suiteId:[{}],suite_access_token:[{}]",suiteId,suite_access_token);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return suite_access_token;
	}

	@Override
	public String getPreAuthCode(String suiteId) {
		String pre_auth_code="";
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("suite_id",suiteId);
		
		try {
			String key = RedisKeyConstants.PRE_AUTH_CODE+suiteId;
			if(!redisService.hasKey(key)){
				String result=HttpKit.post(QyhConstant.PRE_AUTH_CODE_URL.concat(this.getSuiteAccessToken(suiteId)), jsonBody.toString());
				if(StringUtil.isNotBlank(result)){
					JSONObject object = JSONObject.fromObject(result);
					pre_auth_code = object.getString("pre_auth_code");
					if(StringUtil.isNotBlank(pre_auth_code)){
						redisService.set(key, pre_auth_code, QyhConstant.PRE_AUTH_CODE_TIME);
					}
				}
			}else{
				pre_auth_code=(String) redisService.get(key);
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return pre_auth_code;
	}

	@Override
	public boolean setSessionInfo(String suiteId, String preAuthCode,String appids, int auth_type) {
		String body = "{\"pre_auth_code\":\""+preAuthCode+"\",\"session_info\":{\"appid\":["+appids+"],\"auth_type\":"+auth_type+"}}";
		try {
			String result=HttpKit.post(QyhConstant.SET_SESSION_INFO_URL.concat(this.getSuiteAccessToken(suiteId)), body);
			LOG.info("设置授权配置,返回信息：[{}]",result);
			if(StringUtil.isNotBlank(result)){
				JSONObject object = JSONObject.fromObject(result);
				String errcode = object.getString("errcode");
				if("0".equals(errcode)){
					return true;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PermanentCodeVo getPermanentCode(String suiteId, String authCode) {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("suite_id", suiteId);
		jsonBody.put("auth_code", authCode);
		try{
			String result=HttpKit.post(QyhConstant.GET_PERMANENT_CODE_URL.concat(this.getSuiteAccessToken(suiteId)), jsonBody.toString());
			LOG.info("获取企业号的永久授权码，返回信息,[{}]",result);
			JSONObject object = JSONObject.fromObject(result);
			PermanentCodeVo vo = JsonUtils.json2Object(result, PermanentCodeVo.class);
			vo.setAuthCorpInfo(object.getString("auth_corp_info"));
			vo.setAuthInfo(object.getString("auth_info"));
			vo.setAuthUserInfo(object.getString("auth_user_info"));
			return vo;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PermanentCodeVo getAuthInfo(String suiteId, String authCorpid) {
		LOG.info("获取企业号的授权信息，参数,suiteId=[{}]，authCorpid=[{}]",suiteId,authCorpid);
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("suite_id", suiteId);
		jsonBody.put("auth_corpid", authCorpid);
		jsonBody.put("permanent_code", qyhAuthCorpService.getPermanentCodeByCorpId(authCorpid,suiteId));
		try{
			String result = HttpKit.post(QyhConstant.GET_AUTH_INFO_URL.concat(this.getSuiteAccessToken(suiteId)), jsonBody.toString());
			LOG.info("获取企业号的授权信息，返回信息,[{}]",result);
			PermanentCodeVo vo = JsonUtils.json2Object(result, PermanentCodeVo.class);
			return vo;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCorpAccessToken(String suiteId,String authCorpid) {
		String cropAccessToken = "";
		try{
			String key = RedisKeyConstants.CORP_ACCESS_TOKEN+authCorpid+":"+suiteId;
			if(!redisService.hasKey(key)){
				JSONObject jsonBody = new JSONObject();
				jsonBody.put("suite_id", suiteId);
				jsonBody.put("auth_corpid", authCorpid);
				jsonBody.put("permanent_code", qyhAuthCorpService.getPermanentCodeByCorpId(authCorpid,suiteId));
				
				String result = HttpKit.post(QyhConstant.GET_CORP_ACCESS_TOKEN.concat(this.getSuiteAccessToken(suiteId)), jsonBody.toString());
				LOG.info("获取企业号access_token，返回信息,[{}]",result);
				JSONObject object = JSONObject.fromObject(result);
				cropAccessToken = object.getString("access_token");
				redisService.set(key, cropAccessToken, 7000L);
			}else{
				cropAccessToken = (String) redisService.get(key);
			}
			return cropAccessToken;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AddressBookVo syncAddressBook(String corpAccessToken,int seq, int offset) {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("seq", seq);
		jsonBody.put("offset", offset);
		try{
			String result = HttpKit.post(QyhConstant.SYNC_ADDRESSBOOK_URL.concat(corpAccessToken), jsonBody.toString());
			//LOG.info("通讯录同步，返回信息,[{}]",result);
			//JSONObject object = JSONObject.fromObject(result);
			AddressBookVo vo = JsonUtils.json2Object(result, AddressBookVo.class);
			return vo;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AddreddBookDataVo> getAddreddBookData(String corpAccessToken,
			int seq, int offset) {
		boolean flag = true;
		List<AddreddBookDataVo> addreddBookDataVos = new ArrayList<AddreddBookDataVo>();
		while(flag){
			try{
				AddressBookVo addressBookVo=this.syncAddressBook(corpAccessToken, seq, offset);
				for(AddreddBookDataVo addreddBookDataVo:addressBookVo.getData()){
					addreddBookDataVos.add(addreddBookDataVo);
				}
				seq = addressBookVo.getNext_seq();
				offset = addressBookVo.getNext_offset();
				if(addressBookVo.getErrcode()!=0|| addressBookVo.getIs_last()==1){
					flag = false;
					break;
				}
			}catch (Exception e) {
				flag = false;
				break;
			}
			
		}
		return addreddBookDataVos;
	}
}
