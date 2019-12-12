package com.youxiake.util;

import android.content.Context;
import android.net.TrafficStats;

/**
 * Created by hu on 2017/11/7.
 */

public class NetWorkSpeedUtils {

    private static long getTotalRxBytes(Context context) {
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }

    public static String showNetSpeed(Context context) {
        long lastTotalRxBytes = 0;
        long lastTimeStamp = 0;
        long nowTotalRxBytes = getTotalRxBytes(context);
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换

        String netSpeed = String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
        return netSpeed;
    }
}
