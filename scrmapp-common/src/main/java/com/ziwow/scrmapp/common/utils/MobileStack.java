package com.ziwow.scrmapp.common.utils;

import java.util.HashMap;
/**
 * 
 * ClassName: MobileStack <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-8-16 下午5:53:50 <br/>
 *
 * @author Daniel.Wang
 * @version 
 * @since JDK 1.6
 */
public class MobileStack
{
    private static HashMap <String,Object> mobiles = new HashMap <String,Object >();

    public static void addMobile (String mobile)
    {
    	mobiles.put (mobile, new Long (System.currentTimeMillis ()));
    }

    public static void removeMobile (String mobile)
    {
    	mobiles.remove (mobile);
    }
    public static boolean repeatRequest(String mobile){
    	 Long time = (Long) mobiles.get (mobile);
    	 if(time==null ){
    		 return true;
    	 }  else {
    		 if(System.currentTimeMillis () - time.longValue () > 100 * 1000){
    			 return true;
    		 } else {
    			 return false;
    		 }
    	 }
    }
    
    public static void checkMobile (String mobile)
    {
        while (true)
        {
            Long time = (Long) mobiles.get (mobile);
            if (time == null || System.currentTimeMillis () - time.longValue () > 300 * 1000) return;
            try
            {
                Thread.sleep (1000);
            }
            catch (Exception ex) {}
        }
    }
}