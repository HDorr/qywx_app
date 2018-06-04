package com.ziwow.scrmapp.tools.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * ClassName: Messages <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-11-11 下午5:28:07 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class Messages {
	
    private static final String BUNDLE_NAME = "messages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    @SuppressWarnings("all")
    public static String getString(String key, String... arguments) {
    	try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), arguments);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
