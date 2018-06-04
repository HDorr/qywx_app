package com.ziwow.scrmapp.qyh.constants;

/**
 * 
 * ClassName: QyhConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-23 上午9:56:25 <br/>
 *
 * @author 
 * @version 
 * @since JDK 1.6
 */
public class QyhConstant {
	//缓存时间
	public static final Long SUITETICKET_EXPIRATION_TIME = 10*60L;//suiteticke 缓存时间
	public static final Long PRE_AUTH_CODE_TIME = 1000L;//pre_auth_code 缓存时间
	
	// 企业号接口url
	public static final String ASSESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	
	// 企业号三方授权接口url
	public static final String SUITE_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
	public static final String PRE_AUTH_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code?suite_access_token=";
	public static final String AUTH_URL = "https://qy.weixin.qq.com/cgi-bin/loginpage?suite_id=$suite_id$&pre_auth_code=$pre_auth_code$&redirect_uri=$redirect_uri$&state=$state$";
	public static final String SET_SESSION_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info?suite_access_token=";
	public static final String GET_PERMANENT_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=";
	public static final String GET_AUTH_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token=";
	public static final String GET_CORP_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token=";
	public static final String SYNC_ADDRESSBOOK_URL = "https://qyapi.weixin.qq.com/cgi-bin/sync/getpage?access_token=";
	
	//根据code获取成员信息
	public static final String GET_USERINFO_URL="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	
	public static final String SEND_MESSAGE="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	// 创建成员地址
	public static String CREATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	// 更新成员地址
	public static String UPDATA_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	//删除员工地址
	public static String DELETE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
	// 读取成员
	public static String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
}