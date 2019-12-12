package com.youxiake.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.amap.api.maps.model.LatLng;
import com.youxiake.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alice on 2018/1/8.
 */

public class MapUtils {

    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String TENCENT_PACKAGE_NAME = "com.tencent.map";

    /**
     * 判断手机是否安装百度或者高德地图 只要安装一个即可
     */
    public static boolean ifInstallGaodeMap(Context context) {
        return isPackageInstalled(context, "com.autonavi.minimap");
    }

    /**
     * 判断手机是否安装百度或者高德地图 只要安装一个即可
     */
    public static boolean ifInstallBaiduMap(Context context) {
        return isPackageInstalled(context, "com.baidu.BaiduMap");
    }

    private static boolean isPackageInstalled(Context c, String pn) {
        PackageManager packageManager = c.getPackageManager();
        try {
            PackageInfo pi = packageManager.getPackageInfo(pn, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (null != pi) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * 是否安装百度地图
     */
    public static boolean haveBaiduMap(Context context) {
        return exist(context, BAIDU_PACKAGE_NAME);
    }

    public static boolean haveGaodeMap(Context context) {
        return exist(context, GAODE_PACKAGE_NAME);
    }

    public static boolean haveTencentMap(Context context) {
        return exist(context, TENCENT_PACKAGE_NAME);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return true 存在
     */
    public static boolean exist(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有true，没有false
        return packageNames.contains(packageName);
    }

    /**
     * 调用高德地图app,导航
     *
     * @param context            上下文
     * @param myAddress          起点
     * @param destination        目标经纬度
     * @param destinationAddress 目标地址
     *                           高德地图：http://lbs.amap.com/api/amap-mobile/guide/android/route
     */
    public static void openGaodeMap(Context context, LatLng myLatlng, String myAddress, LatLng destination,
                                    String destinationAddress) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("amapuri://route/plan/?" +
                "slat=" + myLatlng.latitude +
                "&slon=" + myLatlng.longitude +
                "&sname=" + myAddress +
                "&dlat=" + destination.latitude +
                "&dlon=" + destination.longitude +
                "&dname=" + destinationAddress +
                "&dev=0" +
                "&t=0"));
        context.startActivity(intent);
    }

    /**
     * 调用百度地图
     *
     * @param myLocation         起点经纬度
     * @param myAddress          起点地址
     * @param destination        目的地经纬度
     * @param destinationAddress 目的地地址
     */
    public static void openBaiduMap(Context context, LatLng myLocation, String myAddress, LatLng destination,
                                    String destinationAddress) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?" +
                "origin=latlng:" + myLocation.latitude + "," + myLocation.longitude +
                "|name:" + myAddress +
                "&destination=latlng:" + destination.latitude + "," + destination.longitude +
                "|name:" + destinationAddress +
                "&mode=driving"));
        context.startActivity(intent);
    }

    /**
     * 调用腾讯地图
     *
     * @param context
     * @param destination        目的地经纬度
     * @param destinationAddress 目的地地址
     *                           腾讯地图参考网址：http://lbs.qq.com/uri_v1/guide-route.html
     */
    public static void openTentcentMap(Context context, LatLng myLocation, String myAddress, LatLng destination, String destinationAddress) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("qqmap://map/routeplan?" +
                "type=drive" +
                "&from=" + myAddress +
                "&fromcoord=" + myLocation.latitude + "," + myLocation.longitude +
                "&to=" + destinationAddress +
                "&tocoord=" + destination.latitude + "," + destination.longitude +
                "&policy=0" +
                "&referer=appName"));
        context.startActivity(intent);
    }

    /**
     * 网页版百度地图 有经纬度
     *
     * @param originLat
     * @param originLon
     * @param originName  ->注：必填
     * @param desLat
     * @param desLon
     * @param destination
     * @param region      : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName
     * @return
     */
    public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, appName);
    }

    /**
     * 打开网页版 高德导航
     * 不填我的位置，则通过浏览器定未当前位置
     *
     * @param context
     * @param myLatLng           起点经纬度
     * @param myAddress          起点地址名展示
     * @param destination        终点经纬度
     * @param destinationAddress 终点地址名展示
     */
    public static void openBrowserBaiduMap(Context context, double mLat, double mLng, String myAddress, double destLat, double destLng,
                                           String destinationAddress) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");

//        http:
////api.map.baidu.com/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving&region=西安&output=html&src=webapp.baidu.openAPIdemo
        intent.setData(Uri.parse("http://api.map.baidu.com/direction?" +
                "origin_region=name:"+myAddress+
                "&destination_region=name:"+destinationAddress+
                "&origin=latlng:" + mLat + "," + mLng + "|name:" + myAddress +
                "&destination=latlng" + destLat + "," + destLng + "|name:" + destinationAddress +
                "&mode=driving&output=html&src=" + context.getString(R.string.app_name) + "&coord_type=bd09ll"));
        context.startActivity(intent);

    }

    /**
     * 百度地图定位经纬度转高德经纬度
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     *
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }
}
