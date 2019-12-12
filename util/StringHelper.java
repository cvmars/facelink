package com.youxiake.util;

import java.util.regex.Pattern;

/**
 * Created by zsm on 16/5/17.
 */
public class StringHelper {

    // 判断字符串是否为空
    public static Boolean isEmptyOrNull(String s) {
        if (s == null || s.length() <= 0) {
            return true;
        }
        return false;
    }


    // 判断字符串是否为空
    public static Boolean isNotEmptyOrNull(String s) {
        return !isEmptyOrNull(s);
    }


    /**字符串是否数字
     * @param s
     * @return
     */
    public static boolean isDouble(String s) {

        Pattern pattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$");
        if (pattern.matcher(s).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**字符串是否数字
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {

        Pattern pattern = Pattern.compile("^[-+]?[0-9]");
        if (pattern.matcher(s).matches()) {
            return true;
        } else {
            return false;
        }
    }


    /** 字符串转化Double
     * @param str
     * @return
     */
    public static double parseDouble(String str) {
        if (isEmptyOrNull(str)) {
            return 0;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }


    /** 字符串转化long
     *
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        if (isEmptyOrNull(str)) {
            return 0;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }


    /** 字符串转化int
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        if (isEmptyOrNull(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }


    /** 字符串转化int
     * @param str
     * @return
     */
    public static int parseInt(String str,int def) {
        if (isEmptyOrNull(str)) {
            return def;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            // TODO: handle exception
            return def;
        }
    }

}
