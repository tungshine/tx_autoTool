/**
 * speedframework-core
 *
 * @(#) DateUtils.java 2010 2010-12-14 下午01:49:46
 * <p>
 * Copyright 2010 G51.ORG, Inc. All rights reserved.
 */
package com.tanglover.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author: TangXu
 * @date: 2018/10/18 13:56
 * @description: 时间工具类
 */
public class DateUtils {
    private static DateFormat FULL_STANDARD_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    private static DateFormat STANDARD_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DateFormat INCOMPLETE_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static DateFormat SHORT_DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    private static DateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private static DateFormat SHORT_MMDD_FORMAT = new SimpleDateFormat("MMdd");

    private static DateFormat SHORT_TIME_FORMAT = new SimpleDateFormat("HHmmss");

    /**
     * 格式完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param date 日期时间
     * @return 完全日期时间字符串
     */
    public static String formatFullStandardDateTime(Date date) {
        return FULL_STANDARD_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期时间
     * @return 日期时间字符串
     */
    public static String formatStandardDateTime(Date date) {
        return STANDARD_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param date 日期时间
     * @return 日期时间字符串
     */
    public static String formatShortDateTime(Date date) {
        return SHORT_DATETIME_FORMAT.format(date);
    }

    /**
     * 格式日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatStandardDate(Date date) {
        return STANDARD_DATE_FORMAT.format(date);
    }

    /**
     * 格式日期字符串（短格式：yyyyMMdd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatShortDate(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    /**
     * 格式日期字符串（短格式：MMdd）
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatShortMMDDDate(Date date) {
        return SHORT_MMDD_FORMAT.format(date);
    }

    /**
     * 格式时间字符串（短格式：HHmmss）
     *
     * @param date 时间
     * @return 时间字符串
     */
    public static String formatShortTime(Date date) {
        return SHORT_TIME_FORMAT.format(date);
    }

    /**
     * 解析完全日期时间字符串（包含毫秒，标准格式：yyyy-MM-dd HH:mm:ss.S）
     *
     * @param dateTimeStr 完全日期时间字符串
     * @return 日期时间
     */
    public static Date parseFullStandardDateTime(String dateTimeStr) {
        try {
            return FULL_STANDARD_DATETIME_FORMAT.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static Date parseStandardDateTime(String dateTimeStr) {
        try {
            return STANDARD_DATETIME_FORMAT.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期时间字符串 (不完整格式：yyyy-MM-dd HH:mm)
     *
     * @param dateTimeStr
     * @return
     */
    public static Date parseIncompleteDateTime(String dateTimeStr) {
        try {
            return INCOMPLETE_DATETIME_FORMAT.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static Date parseShortDateTime(String dateTimeStr) {
        try {
            return SHORT_DATETIME_FORMAT.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期字符串（标准格式：yyyy-MM-dd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseStandardDate(String dateStr) {
        try {
            return STANDARD_DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取传入日期的上一天
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date getStandardDatePre(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取传入日期的下一天
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date getStandardDateNext(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期字符串（短格式：yyyyMMdd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseShortDate(String dateStr) {
        try {
            return SHORT_DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析日期字符串（短格式：MMdd）
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseShortMMDDDate(String dateStr) {
        try {
            return SHORT_MMDD_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析时间字符串（短格式：HHmmss）
     *
     * @param dateStr 时间字符串
     * @return 时间
     */
    public static Date parseShortTime(String dateStr) {
        try {
            return SHORT_TIME_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期时间字符串（标准格式：yyyy-MM-dd HH:mm:ss）
     *
     * @return 日期时间字符串
     */
    public static String getStandardNowDateTimeStr() {
        return formatStandardDateTime(new Date());
    }

    /**
     * 获取当前日期时间字符串（短格式：yyyyMMddHHmmss）
     *
     * @return 日期时间字符串
     */
    public static String getShortNowDateTimeStr() {
        return formatShortDateTime(new Date());
    }

    /**
     * 获取当前日期字符串（标准格式：yyyy-MM-dd）
     *
     * @return 日期字符串
     */
    public static String getStandardNowDateStr() {
        return formatStandardDate(new Date());
    }

    /**
     * 获取当前日期（不完整格式：yyyy-MM-dd）
     *
     * @return
     */
    public static Date getStandardNowDate() {
        return parseStandardDate(getStandardNowDateStr());
    }

    /**
     * 获取当前日期字符串（短格式：yyyyMMdd）
     *
     * @return 日期字符串
     */
    public static String getShortNowDateStr() {
        return formatShortDate(new Date());
    }

    /**
     * 比较当前系统时间是否在传入时间之前
     *
     * @param date 要比较的时间
     * @return
     */
    public static boolean isDateBefore(String date) {
        try {
            Date sysDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sysDate.before(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 比较当前系统时间是否在传入时间之前
     *
     * @param date
     * @return
     */
    public static boolean isDateBefore(Date date) {
        try {
            Date sysDate = new Date();
            return sysDate.before(date);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 比较时间date1是否在date2之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(Date date1, Date date2) {
        try {
            return date1.before(date2);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @author: TangXu
     * @date: 2018/10/18 14:06
     * @description: 日期转时间戳
     * @param: [date]
     * @return: long
     */
    public static long getTime(Date date) {
        return date.getTime();
    }

    public static Date getDate(long timeStamp, String formatType) {
        if (formatType == null) {
            formatType = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(timeStamp); // 根据long类型的毫秒数生命一个date类型的时间

        return date;
    }

    public static void main(String[] args) {
        System.out.println(getTime(new Date()));
    }
}
