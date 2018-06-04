package com.ziwow.scrmapp.wechat.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ziwow.scrmapp.tools.weixin.Tools;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.tools.weixin.decode.AesException;
import com.ziwow.scrmapp.tools.weixin.decode.WXBizMsgCrypt;
import com.ziwow.scrmapp.tools.weixin.enums.InfoTypes;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenWeixin;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;
import com.ziwow.scrmapp.wechat.vo.OpenInMessage;

/**
 * ClassName: TicketServlet <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016-4-22 下午6:06:19 <br/>
 * 
 * @author daniel.wang
 * @version
 * @since JDK 1.6
 */
public class TicketServlet extends HttpServlet {

	private OpenWeixinService openWeixinService;

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 7267931323584302043L;

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream in = request.getInputStream();
		String xmlMsg = Tools.inputStream2String(in);
		System.out.println("第三方平台ticket:" + xmlMsg);
		OpenInMessage inMessage = XmlUtils.xmlToObject(xmlMsg, OpenInMessage.class);
		// 获取解密的message
		OpenInMessage descryptMessage = getDecryptInMessage(inMessage);
		String ticket = descryptMessage.getComponentVerifyTicket();

		System.out.println("第三方平台消息解密:" + JSONObject.fromObject(descryptMessage));
		if (descryptMessage.getInfoType().equals(InfoTypes.COMPONENT_VERIFY_TICKET.getType())) {
			OpenWeixin owUpdate = new OpenWeixin();
			owUpdate.setComponent_appid(inMessage.getAppId());
			owUpdate.setComponent_verify_ticket(ticket);
			// 更新ticket
			openWeixinService.refreshOpenWeixin(owUpdate);
		} else if (descryptMessage.getInfoType().equals(InfoTypes.AUTHORIZED.getType())
				|| descryptMessage.getInfoType().equals(InfoTypes.UPDATEAUTHORIZED.getType())) {
			/*
			 * OpenAuthorizationWeixin oaw = new OpenAuthorizationWeixin();
			 * oaw.setComponent_appid(descryptMessage.getAppId());
			 * oaw.setAuthorizer_appid(descryptMessage.getAuthorizerAppid());
			 * oaw
			 * .setAuthorization_code(descryptMessage.getAuthorizationCode());
			 * System.out.println("进入授权信息更新，"+JSONObject.fromObject(oaw));
			 * openWeixinService.refreshAuthorizerWeixin(oaw);
			 */
		} else if (descryptMessage.getInfoType().equals(InfoTypes.UNAUTHORIZED.getType())) {
			openWeixinService.deleteAuthorizerWeixinByAuthorizeAppid(descryptMessage.getAuthorizerAppid());
		}
	}

	/**
	 * getDecryptTickt:(获取ticket). <br/>
	 * 
	 * @author daniel.wang
	 * @param appId
	 * @return
	 * @since JDK 1.6
	 */
	private OpenInMessage getDecryptInMessage(OpenInMessage inMessage) {
		OpenWeixin ow = openWeixinService.getOpenWeixin(inMessage.getAppId());
		String encodingAesKey = ow.getComponent_key();
		String token = ow.getComponent_token();
		OpenInMessage decrypMessage = null;
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, inMessage.getAppId());
			String descryptResult = pc.decrypt(inMessage.getEncrypt());
			decrypMessage = XmlUtils.xmlToObject(descryptResult, OpenInMessage.class);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return decrypMessage;
	}

	/**
	 * TODO 简单描述该方法的实现功能.
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("第三方平台ticket servlet启动");
		ServletContext context = config.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		openWeixinService = (OpenWeixinService) ctx.getBean("openWeixinService");
	}
}