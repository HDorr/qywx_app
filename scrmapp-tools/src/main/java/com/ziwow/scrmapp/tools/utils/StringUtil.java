/**
 * Project Name:app-util
 * File Name:StringUtil.java
 * Package Name:com.zvoice.scrm.util
 * Date:2014-7-16上午10:40:02
 * Copyright (c) 2014, 上海智握网络科技有限公司版权所有.
 *
 */

package com.ziwow.scrmapp.tools.utils;

/**
 * ClassName: StringUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-16 上午10:40:02 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 */
public class StringUtil {

    public static final String EMPTY_STRING = "";

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
}
