package com.youxiake.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zsm on 16/5/26.
 */
public class DateHelper {


    /** Mysql 当前时间戳
     * @return
     */
    public static String getTimestamp() {
        Date date=new Date();
        long times = date.getTime() / 1000;
        return times + "";
    }

    public static String getYearMonthDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM/dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return format.format(new Date(Long.parseLong(time)));
    }
    public static String getYearMonth(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return format.format(new Date(Long.parseLong(time)));
    }

    public static String stringToTimestamp(String user_time,String format) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try {
            d = sdf.parse(user_time);
            long lo = d.getTime();
            String str = String.valueOf((int)( lo/1000));
            re_time = str;
//            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
    public static String stringToTimestamp0(String user_time,String format) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(user_time);
// 将 Date 对象设置到 Calendar 对象中
            calendar.setTime(date);
//            calendar.set(Calendar.YEAR,  calendar.get(Calendar.YEAR));
//            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            String str = String.valueOf((int) (calendar.getTimeInMillis()/1000));
            re_time = str;
//            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
    public static long stringTransferTimestamp(String user_time,String format) {
        long re_time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(user_time);
// 将 Date 对象设置到 Calendar 对象中
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);

             re_time = calendar.getTimeInMillis() / 1000;
//            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
}
