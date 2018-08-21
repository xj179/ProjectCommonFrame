package com.common.projectcommonframe.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 时间工具类
 */
public class TimeUtil {
    public static final String FORMAT_A = "yyyy-MM-dd";
    public static final String FORMAT_B = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_C = "MM-dd";
    public static final String FORMAT_D = "yyyy-MM-dd HH:mm:ss";

    public static boolean isTimestamp(String time) {
        return isNumericThanZero(time);
    }

    public static String a(String time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        try {
            Date date = format.parse(time);
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String b(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = new Date(time);
        return format.format(date);
    }

    public static String b(String time, String formatStr) {
        if (isTimestamp(time)) {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            long times = Long.parseLong(time);
            Date date = new Date(times);
            return format.format(date);
        }
        return time;
    }

    /**
     * 判断字符串是否匹配yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd HH:mm时间格式
     */
    public static boolean isTimeFormat(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s(([0-1][0-9])|(2?[0-3])):[0-5][0-9]((:|\\s)[0-5][0-9])?");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否匹配yyyy-MM-dd格式
     */
    public static boolean isYMDFormat(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否是当年
     *
     * @param time
     * @return
     */
    public static boolean isSameYear(long time) {
        String curYear = dateToStr("yyyy");
        if (TextUtils.isEmpty(curYear)) {
            return false;
        } else {
            return curYear.equals(dateToStr(time, "yyyy"));
        }
    }

    /**
     * 判断时间是不是今天
     *
     * @param time
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(long time) {
        String curDate = dateToStr("yyyyMMdd");
        if (TextUtils.isEmpty(curDate)) {
            return false;
        } else {
            return curDate.equals(dateToStr(time, "yyyyMMdd"));
        }
    }

    /**
     * 获取时间
     *
     * @param time
     * @param curFrontYearformat
     * @param curYearformat
     * @return
     */
    public static String dateToStrOrFrontTime(long time, String curFrontYearformat, String curYearformat) {
        Date date = new Date();
        String frontTime = "";
        long curTime = date.getTime();
        if (isNow(time)) {
            long timeDifference = curTime - time;
            if (timeDifference / (60 * 60 * 1000) > 0) {
                frontTime = timeDifference / (60 * 60 * 1000) + "小时前";
            } else if (timeDifference / (60 * 1000) > 0) {
                frontTime = timeDifference / (60 * 1000) + "分钟前";
            } else if (timeDifference / (1000) > 0) {
                frontTime = timeDifference / (1000) + "秒前";
            } else {
                timeDifference = Math.abs(timeDifference);
                if (timeDifference / (60 * 60 * 1000) > 0) {
                    frontTime = timeDifference / (60 * 60 * 1000) + "小时后";
                } else if (timeDifference / (60 * 1000) > 0) {
                    frontTime = timeDifference / (60 * 1000) + "分钟后";
                } else if (timeDifference / (1000) > 0) {
                    frontTime = timeDifference / (1000) + "秒后";
                } else {
                    frontTime = dateToStr(time, curYearformat);
                }
            }
        } else {
            if (isSameYear(time)) {
                frontTime = dateToStr(time, curYearformat);
            } else {
                frontTime = dateToStr(time, curFrontYearformat);
            }
        }
        if (frontTime == null) {
            frontTime = "";
        }
        return frontTime;
    }

    /**
     * 将字符串按照时间格式转换为Date
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将字符串按照时间格式转换为指定时间格式的字符串
     *
     * @param date
     * @param startFormat
     * @param endFormat
     * @return
     */
    public static String stringDateToString(String date, String startFormat,
                                            String endFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(startFormat);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(date, pos);

        SimpleDateFormat formatter1 = new SimpleDateFormat(endFormat);
        String str = formatter1.format(strtodate);
        return str;
    }

    /**
     * 将毫秒数按照时间格式转换为字符串
     *
     * @param milliseconds
     * @param format
     * @return
     */
    public static String dateToStr(Long milliseconds, String format) {
        if (milliseconds == 0) {
            return "";
        }
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(date);
        return str;
    }

    /**
     * 将毫秒数按照时间格式转换为字符串
     * @param date
     * @param format
     * @return
     */
    public static String dateToStr(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(date);
        return str;
    }

    /**
     * 将现在时间按照时间格式转换为字符串
     *
     * @param format
     * @return
     */
    public static String dateToStr(String format) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(date);
        return str;
    }

    /**
     * 根据时间格式format获取现在日期
     *
     * @param format 时间格式
     * @return
     */
    public static Date getNowDateShort(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateString, pos);
        return strtodate;
    }

    /**
     * 判断字符串是否是大于0的数字
     */
    public static boolean isNumericThanZero(String str) {
        boolean isNumericThanZero = false;
        if (isNumeric(str)) {
            long num = Long.parseLong(str);
            if (num > 0l) {
                isNumericThanZero = true;
            }
        }
        return isNumericThanZero;
    }

    /**
     * 判断字符串是否由数字组成
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }
}
