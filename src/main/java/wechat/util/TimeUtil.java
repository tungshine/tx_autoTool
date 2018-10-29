package wechat.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:41
 * @description: 时间操作器
 */
public class TimeUtil {

	/**
	 * 获取今天时间 格式YY-MM-DD eg:2010-04-19
	 * 
	 * @return
	 */
	public static String getToday() {
		String str = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		str = dateformat.format(new Date());
		return str;
	}

	/**
	 * 获取今天时间 格式YY-MM-DD eg:2010-04-19
	 * 
	 * @return
	 */
	public static String getTomorrow() {
		String str = "";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
		str = dateformat.format(date.getTime());
		return str;
	}

	/**
	 * 获取现在时间 格式： yy-mm-dd HH:mm:ss eg:2010-04-19 22:42:13
	 * 
	 * @return
	 */
	public static String getNow() {
		String str = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = dateformat.format(c.getTime());
		return str;
	}

	/**
	 * 获取Timestamp 当前时间
	 * 
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取现在时间 格式： yyyy-MM-dd HH:mm eg:2010-04-19 22:42
	 * 
	 * @return
	 */
	public static String getNow(String formator) {
		String str = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat(formator);
		str = dateformat.format(c.getTime());
		return str;
	}

	/**
	 * <p>
	 * 标题：获取今天是星期几
	 * </p>
	 * <p>
	 * 说明： TODO
	 * </p>
	 * 
	 * @version 1.0 2013-12-16 下午03:41:52
	 * @return 1至7 ，分别代表周一到周日
	 */
	public static int getTodayForWeek() {
		int dw = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (dw == 1) {
			return 7;
		} else {
			return dw - 1;
		}
	}

	/**
	 * <p>
	 * 标题：getNextDay
	 * </p>
	 * <p>
	 * 说明：获取当前日期day的下一天日期
	 * </p>
	 * <p>
	 * 时间：2012-6-14 上午10:18:18
	 * </p>
	 * <p>
	 * @version 1.0
	 * </p>
	 * <p>
	 * 传入参数：
	 * </p>
	 * day eg:2012-06-14
	 * <p>
	 * 传出参数：
	 * </p>
	 * 
	 */
	public String getNextDay(String day) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(day);
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Timestamp 转 str
	 * 
	 * @param t
	 * @param format
	 *            转化后的字符串格式 eg:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String timestampToStr(Timestamp t, String format) {
		if (t == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(t);
	}

	/**
	 * str 转 Timestamp
	 * 
	 * @param time
	 *            字符串时间
	 * @param timeFormator
	 *            字符串时间的格式 eg:yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp strToTimestamp(String time, String timeFormator) throws ParseException {
		DateFormat format = new SimpleDateFormat(timeFormator);
		Date armFormateDate = format.parse(time);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = df.format(armFormateDate);
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 * <p>
	 * 标题：checkIsTime
	 * </p>
	 * <p>
	 * 说明：检查时间格式是否正确
	 * </p>
	 * <p>
	 * 时间：2012-6-14 上午10:52:17
	 * </p>
	 * <p>
	 * @version 1.0
	 * </p>
	 * <p>
	 * 传入参数：
	 * </p>
	 * time，format eg:time=2012-05-14,format=yyyy-MM-dd
	 * <p>
	 * 传出参数：
	 * </p>
	 * true:正确 ；false:不正确;
	 */
	public boolean checkIsTime(String time, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = df.parse(time);
		} catch (Exception e) {
			return false;
		}
		String s1 = df.format(d);
		return time.equals(s1);
	}

	/**
	 * 获取昨天的开始时间 如：2015-05-12 00:00:00
	 * 
	 * @return Date
	 * @exception @since
	 *                1.0.0
	 */
	public static Date getYesTerdayBegin() {
		Calendar calendar = Calendar.getInstance();
		// 昨天的当前时间
		calendar.add(Calendar.DATE, -1);
		// 设置时
		calendar.set(Calendar.HOUR, 0);
		// 设置分
		calendar.set(Calendar.MINUTE, 0);
		// 设置秒
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取昨天的最后时间 如：2015-05-12 23:59:59
	 * 
	 * @return Date
	 * @exception @since
	 *                1.0.0
	 */
	public static Date getYesTerdayEnd() {
		Calendar calendar = Calendar.getInstance();
		// 昨天的当前时间
		calendar.add(Calendar.DATE, -1);
		// 设置时
		calendar.set(Calendar.HOUR, 23);
		// 设置分
		calendar.set(Calendar.MINUTE, 59);
		// 设置秒
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取明天两点钟
	 * 
	 * @return long
	 * @exception @since
	 *                1.0.0
	 */
	public static long getTomorrow2() {
		Calendar calendar = Calendar.getInstance();
		// 把日期设置到明天
		calendar.add(Calendar.DATE, +1);
		// 设置时
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		// 设置分
		calendar.set(Calendar.MINUTE, 0);
		// 设置秒
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 获取今天最后时刻 23：59：59
	 * 
	 * @return Date
	 * @exception @since
	 *                1.0.0
	 */
	public static Date getTodayEnd() {
		Calendar calendar = Calendar.getInstance();
		// 设置时
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		// 设置分
		calendar.set(Calendar.MINUTE, 59);
		// 设置秒
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 给时间增加一天
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 * @exception @since
	 *                1.0.0
	 */
	public static Date addDateTimeWithDay(Date d, int day) {
		Calendar calendar = Calendar.getInstance();
		// 把时间设置到d
		calendar.setTime(d);
		// 设置天数增加
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
}
