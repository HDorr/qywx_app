/**
 * 
 */
package com.ziwow.scrmapp.tools.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 
* @ClassName: CookieUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hogen
* @date 2016-8-12 上午9:26:25
*
 */
public class CookieUtil {
    /**
     * 
     * readCokie:(根据名称读取Cookie). <br/>
     *
     * @author daniel.wang
     * @param request
     * @param response
     * @param name
     * @return
     * @throws ServletException
     * @throws IOException
     * @since JDK 1.6
     */
    public static String readCookie(HttpServletRequest request, HttpServletResponse response,
		String name) throws ServletException, IOException {
	String value = null;
	if (name != null) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length >= 2) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (name.equals(cookie.getName())) {
					value = cookie.getValue();
				}
			}
		}
	}
	return value;
    }
    
    	/**
    	 * 
    	 * clearCokie:(清空cookie). <br/>
    	 *
    	 * @author daniel.wang
    	 * @param request
    	 * @param response
    	 * @throws ServletException
    	 * @throws IOException
    	 * @since JDK 1.6
    	 */
	public static void clearCookie(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 
	 * writeCokie:(写Cookie). <br/>
	 *
	 * @author daniel.wang
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param days
	 * @throws ServletException
	 * @throws IOException
	 * @since JDK 1.6
	 */
	public static void writeCookie(HttpServletRequest request, HttpServletResponse response,
			String name, String value, int days) throws ServletException, IOException {
	    
		int day = 24 * 60 * 60;
		if(StringUtils.isNotBlank(value)){
		    Cookie cookie = new Cookie(name, value);
		    cookie.setPath("/");
		    cookie.setMaxAge(days * day);
		    response.addCookie(cookie);
		}
	}
	
	public static void deleteCookie(String name,String path,HttpServletResponse response) throws ServletException, IOException{
	    if(StringUtils.isNotBlank(name)){
		    Cookie cookie = new Cookie(name, null);
		    cookie.setPath(path);
		    cookie.setMaxAge(0);
		    response.addCookie(cookie);
		}
	}

}

