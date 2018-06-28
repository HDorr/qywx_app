/**   
 * @Title: WechatServlet.java
 * @Package com.ziwow.marketing.weixin.servlet
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-8-2 上午11:37:43
 * @version V1.0   
 */
package com.ziwow.scrmapp.qyh.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.ziwow.scrmapp.qyh.service.QyhMessageProcessingHandler;
import com.ziwow.scrmapp.qyh.service.QyhUtilService;
import com.ziwow.scrmapp.tools.weixin.Tools;

/**
 * 微信服务端收发消息接口
 * 
 * @ClassName: WechatServlet
 * @author hogen
 * @date 2016-8-2 上午11:37:43
 * 
 */
public class QyhWeixinServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private QyhUtilService qyhUtilService = null;

	private QyhMessageProcessingHandler qyhMessageProcessingHandler;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		qyhUtilService.connect(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		message(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {

		log.info("微信对接servlet启动");
		ServletContext context = config.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		qyhMessageProcessingHandler = (QyhMessageProcessingHandler) ctx.getBean("qyhMessageProcessingHandler");
		qyhUtilService = (QyhUtilService) ctx.getBean("qyhUtilService");
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
			qyhMessageProcessingHandler.manageMessage(xmlMsg, request, response);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
	}
}