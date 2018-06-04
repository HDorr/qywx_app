package com.ziwow.scrmapp.tools.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * ClassName: DateUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-7-26 下午1:29:29 <br/>
 *
 * @author hogen
 * @version 
 * @since JDK 1.6
 */
public class DateUtil {

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd */
    public final static String WEB_FORMAT = "yyyy-MM-dd";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
	private static SimpleDateFormat monthFormat = new SimpleDateFormat(YYYY_MM);

	private DateUtil() {
	}

	/**
	 * 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date 		日期格式对象
	 * @param pattern 	转换格式 形如：yyyy-MM-dd HH:mm:ss 若pattern为空，则默认转换为yyyy-MM-dd HH:mm:ss形式
	 */
	public static String DateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}

		if (pattern == null || "".equals(pattern)) {
			pattern = YYYY_MM_DD_HH_MM_SS;
		}

		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}	
	/**
	 * 将日期字符串按格式转换成对应的日期对象
	 * 
	 * @param dateString日期字符串
	 * @param pattern	转换格式 形如：yyyy-MM-dd HH:mm:ss 若pattern为空，则默认转换为yyyy-MM-dd HH:mm:ss形式
	 * @return Date
	 */
	public static Date StringToDate(String dateString, String pattern) {

		Date dateTime = null;
		if (dateString == null || "".equals(dateString)) {
			return dateTime;
		}

		if (pattern == null || "".equals(pattern)) {
			pattern = YYYY_MM_DD_HH_MM_SS;
		}

		try {
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			dateTime = formater.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateTime;
	}

	public static String getYesteryDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterDay = dateFormat.format(cal.getTime());
		return yesterDay;
	}

	  public static String format(Date date, String format) {
	        if (date == null || StringUtils.isBlank(format)) {
	            return StringUtils.EMPTY;
	        }

	        return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).format(date);
	    }
	/**
	 * 功能: 将传入的字符串转换成对应的Timestamp对象
	 * 
	 * @param str 待转换的字符串
	 * @return Timestamp 转换之后的对象
	 * @throws Exception
	 *             Timestamp
	 */
	public static Timestamp StringToDateHMS(String str) throws Exception {
		Timestamp time = Timestamp.valueOf(str);
		return time;
	}

	/**
	 * 功能: 根据传入的年月日返回相应的日期对象
	 * 
	 * @param year 年份
	 * @param month 月份
	 * @param day 天
	 * @return Date 日期对象
	 */
	public static Date YmdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 功能: 将日期对象按照MM/dd HH:mm:ss的格式进行转换，返回转换后的字符串
	 * 
	 * @param date 日期对象
	 * @return String 返回值
	 */
	public static String communityDateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd HH:mm:ss");
		String strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	/**
	 * 获取当天的最大日期，如Sat Jun 22 00:00:00 CST 2013（格式化一下为2013-06-22 00:00:00）
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMaximum(11));
			calendar.set(12, calendar.getActualMaximum(12));
			calendar.set(13, calendar.getActualMaximum(13));
			calendar.set(14, calendar.getActualMaximum(14));
			return calendar.getTime();
		}
	}

	/**
	 * 获取当天的最大日期，如Sat Jun 22 23:59:59 CST 2013（格式化下为2013-06-22 23:59:59）
	 * @param date
	 * @return
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMinimum(11));
			calendar.set(12, calendar.getActualMinimum(12));
			calendar.set(13, calendar.getActualMinimum(13));
			calendar.set(14, calendar.getActualMinimum(14));
			return calendar.getTime();
		}
	}

	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 * 
	 * @param date 日期对象
	 * @param afterDays 往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterDay(Date date, int afterDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, afterDays);
		return cal.getTime();
	}
	
	public static Date getAfterYear(Date date, int afterYears) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, afterYears);
		return cal.getTime();
	}
	// day
	/**
	 * 功能: 返回date1与date2相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int DateDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
		return i;
	}

	// min
	/**
	 * 功能: 返回date1与date2相差的分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int MinDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 1000 / 60);
		return i;
	}

	// second
	/**
	 * 功能: 返回date1与date2相差的秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int TimeDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()));
		return i;
	}

	/**
	 * 获得当前月--开始日期 hogen add
	 * @param date
	 * @return
	 */
	public static String getMinMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(monthFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获得当前月--结束日期 hogen add
	 * @param date
	 * @return
	 */
	public static String getMaxMonthDate(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(monthFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return dateFormat.format(calendar.getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前时间到晚上零点的秒数
	 * @return
	 */
	public static long getNightSecond() {
		Date date = new Date();
		long startTime = date.getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long endTime = calendar.getTimeInMillis();
		long second = (endTime - startTime) / 1000;
		return second;
	}

	/**
	 * 返回日期的前N个月数据
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getPreMonthDate(Date day, int pre, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, pre);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String preDate = dateFormat.format(c.getTime());
		return preDate;
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */  
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			if (time1 > time2) {
				return 0;
			}
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	private static Calendar calendar = Calendar.getInstance();//取得本地时间
	//以毫秒为单位指示距 GMT的大致偏移量+以毫秒为单位指示夏令时的偏移量
	private static int gmtOffset = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
	
	/**
	 * java时间转换成Delphi时间
	 * @param javaTime
	 * @return
	 */
	public static double JTime2DTime(Date javaTime){
	    long sysMillis = javaTime.getTime() + gmtOffset;
	    return sysMillis / 86400000 + 25569 + ((double) (sysMillis % 86400000) / 86400000);
	}

	/**
	 * Delphi时间转换成java时间
	 * @param delphiTime
	 * @return
	 */
	public static Date JTime2DTime(double delphiTime){
	    long timeLong = (long)(delphiTime * 86400000L) - (25569 * 86400000L);
	    return new Date(timeLong - gmtOffset);
	}
	
	/**
	 * 把Delphi日期转换成java日志之后返回字符串日期类型
	 * @param object
	 * @return
	 */
	public static String delphiDateToStr(Object object){
		return DateToString(JTime2DTime(Double.parseDouble((String)object)),"");
	}
	
	/**
	 * 指定时间和当前时间比较
	 * @return
	 */
	public static int dateCompare(String startDate, String endDate) {
		Date d1 = StringToDate(startDate, null);
		Date d2 = StringToDate(endDate, null);
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();
		startCal.setTime(d1);
		endCal.setTime(d2);
		if (startCal.compareTo(nowCal) >= 0) {
			return 1;
		} else if (nowCal.compareTo(startCal) >= 0 && nowCal.compareTo(endCal) < 0) {
			return 2;
		} else if (nowCal.compareTo(endCal) >= 0) {
			return 3;
		}
		return 0;
	}
	
	/**
	 * long类型转换为String类型
	 * 
	 * @param currentTime
	 * @param formatType
	 * @return
	 */
	public static String longToString(long currentTime, String formatType) {
		Date dateOld = new Date(currentTime);
		if (StringUtils.isEmpty(formatType)) {
			formatType = YYYY_MM_DD_HH_MM_SS;
		}
		return new SimpleDateFormat(formatType).format(dateOld);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(DateToString(JTime2DTime(42535.7668552546d),""));
	}
}
