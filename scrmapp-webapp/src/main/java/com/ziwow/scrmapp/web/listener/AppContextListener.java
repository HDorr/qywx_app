package com.ziwow.scrmapp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ian on 2015/6/1.
 */
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String appName = sc.getServletContextName();
        setProperties(sc);
        logger.info("[" + appName + "] init context ...");
    }

	private void setProperties(ServletContext sc) {
		String f_ctxPath = sc.getContextPath();
		String f_ver = "?v=" + System.currentTimeMillis();//
		sc.setAttribute("f_ctxpath", f_ctxPath);
		sc.setAttribute("f_ver", f_ver);
	}

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Invoke ApplicationContextListener contextDestroyed");
    }
}