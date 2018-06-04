package com.ziwow.scrmapp.miniapp.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ziwow.scrmapp.tools.weixin.InMessage;
import com.ziwow.scrmapp.tools.weixin.XmlUtils;
import com.ziwow.scrmapp.tools.weixin.decode.WXBizMsgCrypt;

/**
 * @ClassName: DefaultMessageProcessingHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hogen
 * @date 2016-8-2 下午3:48:22
 * 
 */
@Service("wxAppMessageProcessingHandler")
public class WxAppMessageProcessingHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Value("${app.weixin.Token}")
	private String token;

	@Value("${app.weixin.EncodingAESKey}")
	private String EncodingAESKey;

	@Value("${app.weixin.appId}")
	private String appId;

	/**
	 * 业务转发组件
	 * 
	 * @Title: manageMessage
	 * @param @param requestStr
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException 设定文件
	 * @return void 返回类型
	 * @version 1.0
	 * @author Hogen.hu
	 */
	public void manageMessage(String requestStr, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, EncodingAESKey, appId);
			String result2 = pc.decryptMsg(signature, timestamp, nonce, requestStr);
			log.info("微信返回信息[" + result2 + "]");
			InMessage inMessage = XmlUtils.xmlToObject(result2, InMessage.class);
			if (null != inMessage) {
				// 文本消息
				if ("text".equals(inMessage.getMsgType())) {
					log.info("文本消息，openId:[{}],text:[{}]", inMessage.getFromUserName(), inMessage.getContent());
				} else if ("image".equals(inMessage.getMsgType())) {// 图片消息
					log.info("图片消息，openId:[{}],PicUrl:[{}]", inMessage.getFromUserName(), inMessage.getPicUrl());
				} else if ("event".equals(inMessage.getMsgType())) {// 进入会话事件
					log.info("进入会话事件，openId:[{}],SessionFrom:[{}]", inMessage.getFromUserName(), inMessage.getSessionFrom());
				}
			}
			response.getWriter().write("success"); // 应用提供商在收到推送消息后需要返回字符串success,
		} catch (Exception e) {
			System.out.println("requestStr=[" + requestStr + "]");
			e.printStackTrace();
		}
	}
}
