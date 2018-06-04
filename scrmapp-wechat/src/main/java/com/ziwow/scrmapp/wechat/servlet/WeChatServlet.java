package com.ziwow.scrmapp.wechat.servlet;

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

import com.ziwow.scrmapp.tools.weixin.Tools;
import com.ziwow.scrmapp.wechat.service.WeChatMessageProcessingHandler;
import com.ziwow.scrmapp.wechat.weixin.WeChatUtil;

/**
 * 微信服务端收发消息接口
 * 
 * @ClassName: WechatServlet
 * @author hogen
 * @date 2016-8-2 上午11:37:43
 * 
 */
public class WeChatServlet extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass().getName());
	private static final long serialVersionUID = 1L;
	private WeChatUtil wxUtil = null;

	private WeChatMessageProcessingHandler weChatMessageProcessingHandler;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		wxUtil.connect(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		message(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		wxUtil = new WeChatUtil();
		log.info("微信对接servlet启动");
		ServletContext context = config.getServletContext();
	    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
	    weChatMessageProcessingHandler = (WeChatMessageProcessingHandler)ctx.getBean("weChatMessageProcessingHandler");    //好像很多人都可以通过这个方法得到service实例，但是我的不行。  
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
	        String xmlMsg = Tools.inputStream2String(in);
	        weChatMessageProcessingHandler.manageMessage(xmlMsg,request, response);
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
		
	}
}
