/**   
 * @Title: WeiXinUtil.java
 * @Package com.ziwow.marketing.weixin.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-8-2 下午3:43:38
 * @version V1.0   
 */
package com.ziwow.scrmapp.weixin.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.ziwow.scrmapp.weixin.util.open.WXBizMsgCrypt;

/**
 * @ClassName: WeiXinUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-8-2 下午3:43:38
 * 
 */
public class WeiXinUtil {

	private static final Logger log = Logger.getLogger(WeiXinUtil.class);
	private   String sEchoStr; //需要返回的明文
	/*private String sToken = "VRfLV4YBknwkgOkg82aKFCZxt8OR";
	private String sCorpID = "wx7202c45ebd42afda";
	private String sEncodingAESKey = "37W6PzXMsXoA6zCKLmzESEpIJPwI7ktNdOChEkKWgcy";*/
	
	@Value("${qyh.weixin.Token}")
	private String sToken ;
	@Value("${qyh.weixin.CorpID}")
	private String sCorpID ;
	@Value("${qyh.weixin.EncodingAESKey}")
	private String sEncodingAESKey;
	
	 /**
	    * 接入连接生效验证
	   * @Title: connect
	   * @param @param request
	   * @param @param response
	   * @param @throws ServletException
	   * @param @throws IOException    设定文件
	   * @return void    返回类型
	   * @version 1.0
	   * @author Hogen.hu
	    */
	    public void connect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{  
	        log.info("RemoteAddr: "+ request.getRemoteAddr());  
	        log.info("QueryString: "+ request.getQueryString());  
	         if(!accessing(request, response)){  
	             log.info("服务器接入失败.......");  
	             return ;  
	         }  
	        String echostr=getEchostr();  
	        if(echostr!=null && !"".equals(echostr)){  
	                log.info("服务器接入生效..........");  
	                response.getWriter().print(echostr);//完成相互认证  
	        }  
	    }
	/**
	 * 用来接收微信公众平台的验证
	 * 
	 * @Title: accessing
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws ServletException
	 * @param @throws IOException 设定文件
	 * @return boolean 返回类型
	 * @version 1.0
	 * @author Hogen.hu
	 */
	private  boolean accessing(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		log.info("WeChatFilter-Path" + path);
		String sVerifyMsgSig = request.getParameter("msg_signature");
		String sVerifyTimeStamp = request.getParameter("timestamp");
		String sVerifyNonce = request.getParameter("nonce");
		String sVerifyEchoStr = request.getParameter("echostr");
		if (isEmpty(sVerifyMsgSig)) {
			return false;
		}
		if (isEmpty(sVerifyTimeStamp)) {
			return false;
		}
		if (isEmpty(sVerifyNonce)) {
			return false;
		}
		if (isEmpty(sVerifyEchoStr)) {
			return false;
		}
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
			sEchoStr = wxcpt.verifyUrl(sVerifyMsgSig, sVerifyTimeStamp,
					sVerifyNonce, sVerifyEchoStr);
			System.out.println("verifyurl echostr: " + sEchoStr);
			// 验证URL成功，将sEchoStr返回
			return true;
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isEmpty(String str) {
		return null == str || "".equals(str) ? true : false;
	}

	private static String trim(String str) {
		return null != str ? str.trim() : str;
	}

	private static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			log.info("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public  String getEchostr() {
		return sEchoStr;
	}
	
	     
	    public static String creatRevertText(JSONObject jsonObject){  
	            StringBuffer revert =new StringBuffer();  
	            revert.append("<xml>");  
	            revert.append("<ToUserName><![CDATA["+jsonObject.get("ToUserName")+"]]></ToUserName>");  
	            revert.append("<FromUserName><![CDATA["+jsonObject.get("FromUserName")+"]]></FromUserName>");  
	            revert.append("<CreateTime>"+jsonObject.get("CreateTime")+"</CreateTime>");  
	            revert.append("<MsgType><![CDATA[text]]></MsgType>");  
	            revert.append("<Content><![CDATA["+jsonObject.get("Content")+"]]></Content>");  
	            revert.append("<FuncFlag>0</FuncFlag>");  
	            revert.append("</xml>");  
	            return revert.toString();  
	        }  
}
