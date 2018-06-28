/**   
 * @Title: WechatServlet.java
 * @Package com.ziwow.marketing.weixin.servlet
 * @Description: TODO(用一句话描述该文件做什么)
 * @author hogen  
 * @date 2016-8-2 上午11:37:43
 * @version V1.0   
 */
package com.ziwow.scrmapp.miniapp.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ziwow.scrmapp.miniapp.service.WxAppMessageProcessingHandler;
import com.ziwow.scrmapp.miniapp.service.WxAppUtilService;


/**
 * 微信服务端收发消息接口
 * 
 * @ClassName: WechatServlet
 * @author hogen
 * @date 2016-8-2 上午11:37:43
 * 
 */
public class WxAppServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private WxAppUtilService wxAppUtilService = null;

	private WxAppMessageProcessingHandler wxAppMessageProcessingHandler;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		wxAppUtilService.connect(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		message(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		log.info("微信小程序对接servlet启动");
		ServletContext context = config.getServletContext();
	    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
	    wxAppMessageProcessingHandler = (WxAppMessageProcessingHandler)ctx.getBean("wxAppMessageProcessingHandler");
	    wxAppUtilService = (WxAppUtilService)ctx.getBean("wxAppUtilService");
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
	private void message(HttpServletRequest request, HttpServletResponse response){
		ServletInputStream in=null;
		try{
			in = request.getInputStream();
			String postData = IOUtils.toString(in, "utf-8");
			wxAppMessageProcessingHandler.manageMessage(postData,request, response);
		}catch (Exception e) {
			log.error(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
		
	}
}
