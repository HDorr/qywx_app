/**
 * Project Name:dubbo-common
 * File Name:StringUtil.java
 * Package Name:com.ziwow.dubbo.common.util
 * Date:2015-4-28上午11:16:19
 * Copyright (c) 2015, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.weixin.util;

/**
 * ClassName: StringUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015-4-28 上午11:16:19 <br/>
 *
 * @author ezreal.liao
 * @version 
 * @since JDK 1.6
 */
public class StringUtil {
	
	public static final int MOBILE_LENGTH = 11;
	public static final int MOBILE_DELETE_LENGTH = 20;
	public static final String EMPTY_STRING = "";
	
	public static String confuseMobilePhone(String mobile) {
		if(mobile == null) {
			return "";
		} else if(mobile.length() == MOBILE_LENGTH) {
			return mobile.substring(0,3) + "****" + mobile.substring(7, mobile.length());
		} else if(mobile.length() == MOBILE_DELETE_LENGTH) {
			mobile = mobile.substring(0, 11);
			return mobile.substring(0,3) + "****" + mobile.substring(7, mobile.length());
		}
		throw new IllegalArgumentException("The mobilephone was wrong, length must be " + MOBILE_LENGTH +" or " + MOBILE_DELETE_LENGTH);
	}

	public static boolean isBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return true;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(String str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0))
            return false;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }
	/**
	 * main:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author ezreal.liao
	 * @param args
	 * @since JDK 1.6
	 */
	public static void main(String[] args) {
		System.out.println(confuseMobilePhone("13918694427"));
		System.out.println(confuseMobilePhone("15000162857_20150414"));
		System.out.println(confuseMobilePhone("15000162857_201504142"));
	}

}

