package com.ziwow.scrmapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created by Ian on 2016/8/20.
 */
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        try {

            ServletContext sc = event.getServletContext();
            String appName = sc.getServletContextName();
            logger.info("[" + appName + "] init context ...");
            setVersionAndCDN(sc);

        } catch (Exception e) {
            logger.error("error detail:", e);
        }
    }

    private void setVersionAndCDN(ServletContext sc){
        Properties props;
        String version = "1",cdnPath = "",qiniuBucket="",qiniuImageDomain="";
        try {
            props = PropertiesLoaderUtils
                    .loadAllProperties("application.properties");
            version =  System.currentTimeMillis() + "";//props.getProperty("frontend.file.version");
            cdnPath = props.getProperty("frontend.cdn.path");
            if(cdnPath==null||"".equals(cdnPath.trim())){
                cdnPath="/marketing";
            }
            if(!cdnPath.endsWith("/")){
                cdnPath+="/";
            }
            qiniuBucket = props.getProperty("qiniu.bucket");
            qiniuImageDomain = props.getProperty("qiniu.domain");
        } catch (Exception e) {
            logger.info("get frontend version and cdnPath fail");
        }

        sc.setAttribute("f_cdnPath", cdnPath);
        sc.setAttribute("f_ver", "?t="+version);
        sc.setAttribute("qiniuBucket", qiniuBucket);
        sc.setAttribute("qiniuImageDomain", qiniuImageDomain);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("Invoke ApplicationContextListener contextDestroyed");
    }
}
