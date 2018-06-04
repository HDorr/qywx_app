/**
 * Project Name:app-service
 * File Name:Config.java
 * Package Name:com.zvoice.scrm.util
 * Date:2015-3-19下午12:58:19
 * Copyright (c) 2015, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.tools.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: Config
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hogen
* @date 2017-1-13 下午3:11:02
*
 */
public class Config {

	private static final Logger log = LoggerFactory.getLogger(Config.class);

	private static Config instance = new Config();

	public static Properties props = new Properties();
	
	static {
		init();
	}

	private Config() {}

	private static void init() {
		InputStream is = null;
		try {
			is = Config.class.getClassLoader().getResourceAsStream("error_code.properties");
			props.load(is);
		} catch (Exception e) {
			log.error("load config.properties error.");
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {}
		}
	}
	
	public static final Config getInstance() {
		return instance;
	}
}
