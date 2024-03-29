package com.youxiake.util;

/**
 * Created by hehaifeng on 2019/1/11.
 */

public class StringUtil {




    /**
     * 定义所有常量
     */
    public static final String EMPTY = "";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;

    /**
     * @Description 字符串向左截取
     * @author ShengLiu
     * @date 2018/7/4
     * @param str
     * @param len
     * @return java.lang.String
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < ZERO) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(ZERO, len);

    }

    /**
     * @Description 字符串向右截取
     * @author ShengLiu
     * @date 2018/7/4
     * @param str
     * @param len
     * @return java.lang.String
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < ZERO) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);

    }

    /**
     * @Description 根据不同名字的长度返回不同的显示数据
     * @author ShengLiu
     * @date 2018/7/4
     * @param str
     * @return java.lang.String
     */
    public static String checkNameLength(String str){
        if(str == null){
            return null;
        }
        if(str.length() == ONE) {
            return str;
        }else if(str.length() == TWO){
            return "*" + StringUtil.right(str, ONE);
        }else if(str.length() == THREE){
            return StringUtil.left(str, ONE) + "*" + StringUtil.right(str, ONE);
        }else if(str.length() == FOUR){
            return StringUtil.left(str, ONE) + "**" + StringUtil.right(str, ONE);
        }
        return str;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
//        System.out.println("名字: " + TestUtil.checkNameLength("海"));
//        System.out.println("名字: " + TestUtil.checkNameLength("贼王"));
//        System.out.println("名字: " + TestUtil.checkNameLength("海贼王"));
//        System.out.println("名字: " + TestUtil.checkNameLength("大海贼王"));
//        System.out.println("手机号: " + TestUtil.left("15838883888", THREE) + "*****" + TestUtil.right("15838883888", FOUR));
//        System.out.println("信用卡号16位: " + TestUtil.left("1234567891011121", FOUR) + "*****" + TestUtil.right("1234567891011121", FOUR));
//        System.out.println("银行卡号19位: " + TestUtil.left("1234567891011121314", FOUR) + "*****" + TestUtil.right("1234567891011121314", FOUR));
    }

}
