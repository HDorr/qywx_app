package com.ziwow.scrmapp.wechat.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.JCEBlockCipher.DESCBC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;
import com.ziwow.scrmapp.tools.utils.CommonUtil;
import com.ziwow.scrmapp.tools.utils.Sha1Util;
import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.Tools;
import com.ziwow.scrmapp.tools.weixin.XStreamAdaptor;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.tools.weixin.decode.AesException;
import com.ziwow.scrmapp.tools.weixin.decode.WXBizMsgCrypt;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenAuthorizationWeixin;
import com.ziwow.scrmapp.wechat.persistence.entity.OpenWeixin;
import com.ziwow.scrmapp.wechat.service.OpenWeixinService;
import com.ziwow.scrmapp.wechat.service.WeChatMessageProcessingHandler;
import com.ziwow.scrmapp.wechat.service.impl.WeiXinWerviceImpl;
import com.ziwow.scrmapp.wechat.vo.Articles;
import com.ziwow.scrmapp.wechat.vo.TextOutMessage;
import com.ziwow.scrmapp.wechat.weixin.WeChatUtil;

public class OpenWeChatServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger LOGGER = Logger.getLogger(OpenWeChatServlet.class);
	private OpenWeixinService openWeixinService;
	private WeChatUtil wxUtil = null;
	private WeChatMessageProcessingHandler weChatMessageProcessingHandler;

	public OpenWeixinService getOpenWeixinService() {
		return openWeixinService;
	}

	public void setOpenWeixinService(OpenWeixinService openWeixinService) {
		this.openWeixinService = openWeixinService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		wxUtil.connect(req, resp);
//		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("OpenWX输入消息来拉");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		ServletInputStream in = request.getInputStream();
		String xmlMsg = Tools.inputStream2String(in);

		String signature = request.getParameter("msg_signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数

		LOGGER.info("OpenWX输入消息:[" + xmlMsg + "]");
		String decryptMsg = getDecryptMessage(signature, timestamp, nonce, xmlMsg);

		if (decryptMsg==null){
			decryptMsg=xmlMsg;
		}
		InMessage oi = XmlUtils.xmlToObject(decryptMsg, InMessage.class);

		String xml = "";
		weChatMessageProcessingHandler.manageMessage(decryptMsg, request, response);
	}

	/**
	 * XML组装组件
	 * 
	 * @Title: message
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException 设定文件
	 * @return void 返回类型
	 * @version 1.0
	 * @author Hogen.hu
	 */
	private void message(HttpServletRequest request, HttpServletResponse response) {
		ServletInputStream in = null;
		try {
			in = request.getInputStream();
			String xmlMsg = Tools.inputStream2String(in);
			weChatMessageProcessingHandler.manageMessage(xmlMsg, request, response);
		} catch (Exception e) {

		} finally {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
	}

	private String getDecryptMessage(String msgSignature, String timestamp, String nonce, String fromXML) {
		OpenWeixin ow = openWeixinService.getOpenWeixin();
		String encodingAesKey = ow.getComponent_key();
		String token = ow.getComponent_token();
		String appid = ow.getComponent_appid();
		String descryptResult = null;
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appid);
			descryptResult = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			LOGGER.info("解密后：" + descryptResult);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return descryptResult;
	}

	private String getEncryptMessage(String replyMsg) {
		OpenWeixin ow = openWeixinService.getOpenWeixin();
		String encodingAesKey = ow.getComponent_key();
		String token = ow.getComponent_token();
		String appid = ow.getComponent_appid();
		String encryptMessage = null;
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appid);
			encryptMessage = pc.encryptMsg(replyMsg, Sha1Util.getTimeStamp(), CommonUtil.CreateNoncestr());
		} catch (AesException e) {
			e.printStackTrace();
		}
		return encryptMessage;
	}

	@Override
	public void destroy() {
		LOGGER.info("OpenWeChatServlet已经销毁");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		LOGGER.info("OpenWeChatServlet已经启动！");
		ServletContext context = config.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		openWeixinService = (OpenWeixinService) ctx.getBean("openWeixinService");

		wxUtil = new WeChatUtil();
		weChatMessageProcessingHandler = (WeChatMessageProcessingHandler) ctx.getBean("weChatMessageProcessingHandler");
	}
}