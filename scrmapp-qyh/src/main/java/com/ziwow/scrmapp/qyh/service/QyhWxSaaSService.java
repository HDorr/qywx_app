package com.ziwow.scrmapp.qyh.service;

import java.util.List;

import com.ziwow.scrmapp.qyh.vo.AddreddBookDataVo;
import com.ziwow.scrmapp.qyh.vo.AddressBookVo;
import com.ziwow.scrmapp.qyh.vo.PermanentCodeVo;


/**
 * @ClassName: QyhWxService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-11-29 上午11:01:45
 * 
 */
public interface QyhWxSaaSService {

	/**
	 * 
	* @Title: getSuiteAccessToken
	* @Description: 该API用于获取应用套件令牌（suite_access_token）
	* @param @param json
	* @param @return    设定文件
	* @return String    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public String getSuiteAccessToken(String suiteId);
	
	
	/**
	 * 
	* @Title: getPreAuthCode
	* @Description: 获取预授权码
	* @param @param suiteAccessToken
	* @param @param suiteId
	* @param @return    设定文件
	* @return String    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public String getPreAuthCode(String suiteId);
	

	/**
	 * 
	* @Title: setSessionInfo
	* @Description: 设置授权配置
	* @param @param suiteAccessToken 应用套件access_token 
	* @param @param preAuthCode      预授权码
	* @param @param appid      允许进行授权的应用id，如1、2、3， 不填或者填空数组都表示允许授权套件内所有应用
	* @param @param auth_type  授权类型：0 正式授权， 1 测试授权， 默认值为0
	* @param @return    设定文件
	* @return boolean    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public boolean setSessionInfo(String suiteId, String preAuthCode,String appids,int auth_type);
	
	/**
	 * 
	* @Title: getPermanentCode
	* @Description: 获取企业号的永久授权码
	* @param @param suiteId  应用套件id
	* @param @param authCode 临时授权码，会在授权成功时附加在redirect_uri中跳转回应用提供商网站。长度为64至512个字节
	* @param @return    设定文件
	* @return PermanentCodeVo    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public PermanentCodeVo getPermanentCode(String suiteId,String authCode);
	
	
	/**
	 * 
	* @Title: getAuthInfo
	* @Description:获取企业号的授权信息
	* @param @param suiteAccessToken
	* @param @param suiteId 应用套件id
	* * @param @param authCorpid 授权方corpid
	* @param @param permanentCode 永久授权码，通过get_permanent_code获取
	* @param @return    设定文件
	* @return AuthInfoVo    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public PermanentCodeVo getAuthInfo(String suiteId,String authCorpid);
	
	/**
	 * 
	* @Title: getCorpAccessToken
	* @Description: 获取企业号access_token
	* @param @param suiteAccessToken
	* @param @param suiteId
	* @param @param permanentCode
	* @param @param authCorpid
	* @param @return    设定文件
	* @return String    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public String getCorpAccessToken(String suiteId,String authCorpid);
	
	/**
	 * 
	* @Title: syncAddressBook
	* @Description: 通讯录同步
	* @param @param seq 变更序号，递增32位整型值，表示某次通讯录变更，填上一请求返回的next_seq,seq和offset均填0，表示全量拉取
	* @param @param offset    变更序号偏移量，填上一请求返回的next_offset
	* @return void    返回类型
	* @version 1.0
	* @author Hogen.hu
	 */
	public AddressBookVo syncAddressBook(String corpAccessToken,int seq,int offset);
	
	
	public List<AddreddBookDataVo> getAddreddBookData(String corpAccessToken,int seq,int offset);
}
