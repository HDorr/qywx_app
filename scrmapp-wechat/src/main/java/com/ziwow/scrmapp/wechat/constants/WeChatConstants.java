package com.ziwow.scrmapp.wechat.constants;

/**
 * 
* @ClassName: WeChatConstants
* @author hogen
* @date 2016-8-4 上午11:11:15
*
*/
public class WeChatConstants {	
    public static final String BASE_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
    public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    //客服接口
    public static final String CUSTOM_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    public static final String QR_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

    //user session
  	public static final String USER = "user";
    public static final String SCRMAPP_USER = "SCRMAPP_USER";

    //授权
  	public static final String SNSAPI_BASE_COMPONENT = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${APPID}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=snsapi_base&state=${STATE}&component_appid={COMPONENT_APPID}#wechat_redirect";
  	
  	//通过code获取openId
  	public static final String CODE_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${APPID}&secret=${SECRET}&code=${CODE}&grant_type=authorization_code";
    public static final String USER_HOME_PATH = "/scrmapp/consumer/user/filter/user_home/user_home";
    //下载多媒体文件
    public static final String WECHAT_MADIA_DOWNLOAD="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
}