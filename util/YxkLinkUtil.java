package com.youxiake.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.loopj.android.http.RequestParams;
import com.youxiake.api.ApiX1;
import com.youxiake.app.MyConfig;
import com.youxiake.business.BusinessHandlerManager;
import com.youxiake.eventbus.EventBusWrap;
import com.youxiake.listener.GsonCallBack;
import com.youxiake.model.BaseItemModel;
import com.youxiake.model.GsonResultModel;
import com.youxiake.model.WebUrlModel;
import com.youxiake.ui.base.BaseActivity;
import com.youxiake.ui.base.MyWebViewActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * Created by hehaifeng on 2018/1/9.
 */

//处理游侠客的链接问题
public class YxkLinkUtil {


    /**
     * 扫描处理链接
     ***/
    public static boolean handleUrl(BaseActivity mContext, String url) {


        boolean isDo = handleUrl(url, mContext, false); //判断是否做过处理
        if (isDo) {
            return true;
        }

        if (url != null) {
            MyLog.error("url :" + url);
//            外链
            if (url.indexOf("youxiake") == -1) {

                //是否外部链接
                boolean isURl = BusinessHandlerManager.getInstance().doQCodeHandler(url);
                if (!isURl) {
                    goActivity(mContext, "url", url, MyWebViewActivity.class);
                }
            } else {
                requestProtocol(mContext, url);

            }
            return true;
        }
        return false;
    }


    /**
     * 网页上处理链接
     **/
    public static boolean handleWebUrl(BaseActivity mContext, String url) {


        boolean isDo = handleUrl(url, mContext, false); //判断是否做过处理
        if (isDo) {
            return true;
        }
        if (url != null) {
            MyLog.error("url :" + url);
//            外链
            if (url.indexOf("youxiake") == -1) {

                return false;
            } else {
                requestProtocol(mContext, url);
            }
            return true;
        }
        return false;
    }

    private static void requestProtocol(final Context context, String url) {

        RequestParams params = new RequestParams();
        params.put("link", url);
        ApiX1.getInstance().getJumpUrl(context, params, new GsonCallBack<BaseItemModel>() {
            @Override
            public void onFailure(int statusCode, Throwable arg0) {

            }

            @Override
            public void onSuccess(GsonResultModel<BaseItemModel> resultModel, Object obj) {
                if (resultModel.isSucc()) {
                    BaseItemModel model = resultModel.getData();
                    if (model != null) {
                        if (model.handler.equals("productmd") && model.url != null) {

                            String[] split = model.url.split("&");
                            if (split.length != 0) {
                                model.statisticsCode = model.url;
                                model.url = split[split.length - 1];
                                MyLog.debug("statisticsCode :" + model.statisticsCode);
                            }
                        }

                        if (MyConfig.handler_html5.equals(model.handler)) {

                            if (isTopActivity(context)) {
                                EventBusWrap.post(new WebUrlModel(model.url));
                                return;
                            }
                        }
                        BusinessHandlerManager.getInstance().doHandler(model);
                    }

                }
            }
        });
    }

    // 判断MywebviewActivity是不是首页
    public static boolean isTopActivity(Context context) {
        boolean isTop = false;

        try {

            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            if (am.getRunningTasks(1).size() > 0) {
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                if (cn.getClassName().contains("MyWebViewActivity")) {
                    isTop = true;
                }
            }

        } catch (Exception e) {

        }
        MyLog.debug("istop :" + isTop);
        return isTop;
    }

    public static boolean handleUrl(String url, Context mContext, boolean isRong) {
        if (url != null) {
            MyLog.error("url :" + url);
//            外链
            if (url.indexOf("youxiake") == -1) {
                if (isRong) {
                    return false;
                }
                Intent intent = new Intent(mContext, MyWebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            } else {
                requestProtocol(mContext, url);

            }
            return true;
        }
        return false;
    }


    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }


    private static void goActivity(BaseActivity mContext, String key, String url, Class<?> clazz) {
        Intent intent = new Intent();
        intent.putExtra(key, url);
        intent.setClass(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private static void goActivity(BaseActivity mContext, String key, int value, Class<?> clazz) {
        Intent intent = new Intent();
        intent.putExtra(key, value);
        intent.putExtra("rong", false);
        intent.setClass(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    private static void goActivity(BaseActivity mContext, Bundle bundle, Class<?> clazz) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 融云外链点击
     ***/
    public static boolean handleRongUrl(BaseActivity mContext, String url) {


        boolean isDo = handleUrl(url, mContext, true); //判断是否做过处理
        if (isDo) {
            return true;
        }

        if (url != null) {
            MyLog.error("url :" + url);
//            外链
            if (url.indexOf("youxiake") == -1) {

                //是否外部链接
//                boolean isURl = BusinessHandlerManager.getInstance().doQCodeHandler(url);
//                if (!isURl) {
//                    return false;
//                }
            } else {

                requestProtocol(mContext, url);
//                if (url.contains("thread-")) {
//                    String[] split = url.split("-");
//                    String tid = split[1];
//                    if (!TextUtils.isEmpty(tid)) {
//                        goActivity(mContext, "tid", StringHelper.parseInt(tid), CommunityForumDetailActivity.class);
//                    }
//                } else if (url.contains("forum")) {
//                    String[] split = url.split("=");
//                    String[] split1 = split[2].split("&");
//                    String tid = split1[0];
//                    if (!TextUtils.isEmpty(tid)) {
//                        goActivity(mContext, "tid", StringHelper.parseInt(tid), CommunityForumDetailActivity.class);
//                    }
//                } else if (url.contains("film")) {
//                    Pattern urlPattern = Pattern
//                            .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
//                    String pid = null;
//                    Matcher matcher = urlPattern.matcher(url);
//                    while (matcher.find()) {
//                        if (matcher.groupCount() >= 3) {
//                            pid = matcher.group(2);
//                        }
//                    }
//                    if (!TextUtils.isEmpty(pid)) {
//                        goActivity(mContext, "film_id", StringHelper.parseInt(pid), CommunityVideoPlayActivity.class);
//                    }
//                } else if (url.contains("album")) {
//                    String id = url.substring(url.lastIndexOf("/") + 1, url.length());
//                    if (!TextUtils.isEmpty(id)) {
//                        goActivity(mContext, "album_id", StringHelper.parseInt(id), CameraDetailActivity.class);
//                    }
//                } else if (url.contains("lines.html")) {
//                    Pattern urlPattern = Pattern
//                            .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
//                    String pid = null;
//                    Matcher matcher = urlPattern.matcher(url);
//                    while (matcher.find()) {
//                        if (matcher.groupCount() >= 3) {
//                            pid = matcher.group(2);
//                        }
//                    }
//                    if (!TextUtils.isEmpty(pid)) {
//                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                        intent.putExtra("pid", pid.replaceAll("[^\\d]+", ""));
//                        mContext.startActivity(intent);
//                    }
//                } else if (url.contains("visas/detail")) {
//
//                    Pattern urlPattern = Pattern
//                            .compile("(^|&|\\?)+vid=+([^&]*)(&|$)");
//                    String vid = null;
//                    Matcher matcher = urlPattern.matcher(url);
//                    while (matcher.find()) {
//                        if (matcher.groupCount() >= 3) {
//                            vid = matcher.group(2);
//                        }
//                    }
//                    if (!TextUtils.isEmpty(vid)) {
//                        MyLog.debug("vid :" + vid);
//                        goActivity(mContext, Constant.INTENT_DETAIL_ID, UtilHelper.ParseInt(vid), VisaDetailActivity.class);
//                    }
//                } else {
//                    return false;
//                }
            }
            return true;
        }
        return false;
    }


}
