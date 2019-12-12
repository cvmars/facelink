package com.youxiake.util;

import com.youxiake.app.MyApplication;

/** 设备工具类
 * Created by zsm on 16/5/17.
 */
public class DeviceHelper {


    /** 手机系统版本 如：4.3 4.4
     * @return
     */
    public static String osVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /** 设备名称 如 华为 小米
     * @return
     */
    public static String deviceName() {
        return android.os.Build.MODEL;
    }


    /** 安装版本号
     * @return
     */
    public static String installVersion(){
        return MyApplication.Instance.getPackageInfo().versionName;
    }


    /** 安装版本号code
     * @return
     */
    public static String installVersionCode(){
        return MyApplication.Instance.getPackageInfo().versionCode+"";
    }


    /** 设备序列号
     * @return
     */
    public static String SerialNumber(){
        String serialNumber = android.os.Build.SERIAL;
        MyLog.debug_s("SerialNumber="+serialNumber);
        return  serialNumber;
    }


    /** 版本比较
     * @param setupVer 字符串格式 2.5.1 或者 2.5.12
     * @param onLineVer 字符串格式 2.5.1 或者 2.5.12
     * @return  true:更新  false:不更新
     */
    public static boolean verSionCompare(String setupVer, String onLineVer) {

        if (StringHelper.isEmptyOrNull(setupVer) || StringHelper.isEmptyOrNull(onLineVer)) {
            return false;
        }

        if (setupVer.length() == 5) {
            StringBuffer buf = new StringBuffer(setupVer);
            buf.insert(4, "0");
            setupVer = buf.toString();
        }

        if (onLineVer.length() == 5) {
            StringBuffer buf = new StringBuffer(onLineVer);
            buf.insert(4, "0");
            onLineVer = buf.toString();
        }

        setupVer = setupVer.replace(".", "");
        onLineVer = onLineVer.replace(".", "");

        //MyLog.debug_s("setupVer: "+setupVer+",onLineVer="+onLineVer);

        if (StringHelper.parseInt(onLineVer) > StringHelper.parseInt(setupVer)) {
            return true;
        }

        return false;
    }


}
