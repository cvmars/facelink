package com.youxiake.util;

import android.os.Looper;
import android.util.Log;

import com.youxiake.BuildConfig;

public class MyLog {

    private static final int MaxLength = 2000;

    public static void debug(String tag, String msg) {
//        if (BuildConfig.DEBUG) {
        Log.d(tag, msg);
//        }
    }

    public static void debug(String msg) {
//        if (BuildConfig.DEBUG) {
        debugLong("youxiake", msg);
//        }
    }


    public static void debugLong(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.d("youxiake", msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.d("youxiake", msg);
    }


    public static void debug_s(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d("youxiake", msg);
        }
    }

    public static void debug_thread(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d("youxiake", "isMainTread:" + (Looper.getMainLooper() == Looper.myLooper()) + ",thread:" + Thread.currentThread().getName() + ":" + msg);
        }
    }

    public static void error(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e("youxiake", msg);
        }
    }

    public static void error_s(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e("youxiake", msg);
        }
    }

    public static void error_s(String msg, Exception tr) {
        if (BuildConfig.DEBUG) {
            Log.e("youxiake", msg, tr);
        }
    }
}
