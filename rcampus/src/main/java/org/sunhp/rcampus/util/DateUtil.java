package org.sunhp.rcampus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期转换工具类
 * 
 * @author fuyufeng
 * 
 */
public final class DateUtil {

	/**
	 * 一天的毫秒数
	 */
	public static final long DAY_IN_MILLISECOND = 24 * 3600 * 1000L;

	/**
	 * 常规日期格式
	 */
	public static final String NORMAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy-MM-dd
	 */
	public static final String YEAR_PATTERN = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd HH:mm:ss.S
	 */
	public static final String MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String MILLISECOND_PATTERN_2 = "yyyyMMddHHmmssSSS";

	public static final String TIME = "HH:mm:ss";

	/**
	 * 日期格式化
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            目标日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null || pattern == null) {
			throw new IllegalArgumentException("invalid argument");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String formated = sdf.format(date);
		return formated;
	}

	/**
	 * 使用默认日期格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, NORMAL_PATTERN);
	}

	/**
	 * 将字符串转换成日期格式
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		if (strDate == null) {
			throw new IllegalArgumentException("invalid argument");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期拼接
	 * 
	 * @param date
	 *            日期：例如： 2012-09-01
	 * @param time
	 *            时间：例如： 8:01
	 * @return
	 */
	public static Date aggregation(String date, String time) {
		String str = date + " " + time + ":00";
		return parse(str, NORMAL_PATTERN);
	}

	/**
	 * 利用默认格式将字符串格式转换成日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, YEAR_PATTERN);
	}

	/**
	 * 计算另个日期相差的毫秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long diffMills(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		long times1 = calendar1.getTimeInMillis();
		long times2 = calendar2.getTimeInMillis();
		long delt = times1 - times2;
		return delt;
	}

	/**
	 * 计算两个日期之间的相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDays(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		long times1 = calendar1.getTimeInMillis();
		long times2 = calendar2.getTimeInMillis();
		long delt = times1 - times2;
		int days = (int) (delt / DAY_IN_MILLISECOND);
		return days;
	}

	/**
	 * 格式化开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date startDateFormat(Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		return calendar1.getTime();
	}

	/**
	 * 格式化结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date endDateFormat(Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.set(Calendar.HOUR_OF_DAY, 23);
		calendar1.set(Calendar.MINUTE, 59);
		calendar1.set(Calendar.SECOND, 59);
		calendar1.set(Calendar.MILLISECOND, 999);
		return calendar1.getTime();
	}

	/**
	 * 在指定的日期上增加/减少天数
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		return add(date, Calendar.DATE, days);
	}

	/**
	 * 在指定的日期上增加/减少月数
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, Calendar.MONTH, months);
	}

	/**
	 * 在指定日期上增加或减少年数
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addYears(Date date, int years) {
		return add(date, Calendar.YEAR, years);
	}

	/**
	 * 日期计算
	 * <p>
	 * 添加或者减少时间
	 * 
	 * @param date
	 *            指定日期
	 * @param field
	 *            年<code>Calendar.YEAR</code>；月<code>Calendar.MONTH</code>；日
	 *            <code>Calendar.DATE</code>
	 * @param amount
	 * @return
	 */
	private static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	// // 用来全局控制 上一周，本周，下一周的周数变化
	// private static int weeks = 0;
	// private static int MaxDate;// 一月最大天数
	private static int MaxYear;// 一年最大天数

	/**
	 * 
	 * @return
	 */
	private static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 计算当月最后一天
	public static Date getLastDayofMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 0);
		lastDate.set(Calendar.HOUR_OF_DAY, 24);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);
		lastDate.set(Calendar.MILLISECOND, 0);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.MILLISECOND, -1);// 减去一天，变为当月最后一天
		return lastDate.getTime();
	}

	// 获取当月第一天
	public static Date getFirstDayOfMonth() {
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1);
		firstDate.set(Calendar.HOUR_OF_DAY, 0);
		firstDate.set(Calendar.MINUTE, 0);
		firstDate.set(Calendar.SECOND, 0);
		firstDate.set(Calendar.MILLISECOND, 0);
		return firstDate.getTime();
	}

	// 获得本周星期日的日期
	public static Date getCurrentWeekday() {
		int weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		currentDate.set(Calendar.HOUR_OF_DAY, 24);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.add(Calendar.MILLISECOND, -1);
		Date monday = currentDate.getTime();
		return monday;
	}

	// 获得本周一的日期
	public static Date getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 24);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		Date monday = currentDate.getTime();
		return monday;
	}

	public static Date getStartOfDay() {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, -1);
		currentDate.set(Calendar.HOUR_OF_DAY, 24);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		Date date = currentDate.getTime();
		return date;
	}

	public static Date getEndOfDay() {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, 0);
		currentDate.set(Calendar.HOUR_OF_DAY, 24);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.add(Calendar.MILLISECOND, -1);
		Date date = currentDate.getTime();
		return date;
	}

	public static void main(String args[]) {
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		// System.out.println(sdf.format(getCurrentWeekday()));
		// System.out.println(DateUtil.diffMills(
		// DateUtil.parse("2015-09-18 21:34:01"),
		// DateUtil.parse("2015-10-19 21:33:54")));
		int a = (int) (2678400000L / 1000) / 3600;
		System.out.println(a / 24);
	}

}
