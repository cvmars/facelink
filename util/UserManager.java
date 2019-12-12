package com.youxiake.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.youxiake.R;
import com.youxiake.api.ApiX1;
import com.youxiake.app.MyApplication;
import com.youxiake.app.MyConfig;
import com.youxiake.business.BusinessHandlerManager;
import com.youxiake.eventbus.BaseEvent;
import com.youxiake.eventbus.EventBusWrap;
import com.youxiake.eventbus.MeEvent;
import com.youxiake.listener.CallBack;
import com.youxiake.listener.GsonCallBack;
import com.youxiake.listener.UILImageLoader;
import com.youxiake.model.BaseItemModel;
import com.youxiake.model.FindModel;
import com.youxiake.model.FollowModel;
import com.youxiake.model.FriendModel;
import com.youxiake.model.GsonResultModel;
import com.youxiake.model.ResultModel;
import com.youxiake.model.TongTuanModel;
import com.youxiake.model.UserModel;
import com.youxiake.model.WebUrlModel;
import com.youxiake.model.YSFonLineModel;
import com.youxiake.ui.main.MainActivity;
import com.youxiake.ui.me.ui.LoginActivity;
import com.youxiake.ui.product.ui.ProductDetailActivity;
import com.youxiake.ui.visa.ui.VisaDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Cvmars on 2016/6/21
 * <p>
 * 用户操作管理类
 */
public class UserManager {

    private static final String DESKEY = "yxk_2088_2016";

    private static volatile UserManager singleton = null;

    private UserManager() {
    }

    public static UserManager getSingleton() {
        if (singleton == null) {
            synchronized (UserManager.class) {
                if (singleton == null) {
                    singleton = new UserManager();
                }
            }
        }
        return singleton;
    }


    /**
     * 关注
     *
     * @param me_follow
     */
    public static void onFindUserGuanZhu(final Context context, final TextView me_follow, final FindModel findModel, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", findModel.uid);

        String requestUrl = ApiX1.API_USER_FOLLOWBATH;
        if (MyConfig.Relation_Both.equals(findModel.relation)
                || MyConfig.Relation_Following.equals(findModel.relation)) {
            requestUrl = String.format(requestUrl, "unfollow");
        } else {
            requestUrl = String.format(requestUrl, "followed");
        }

        ApiX1.getInstance().postUserFollow(MyApplication.Instance, requestUrl,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {
                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                ResultModel model = new ResultModel();
                                model.msg = resultModel.getData().getRelation();
                                if (callBack != null) {
                                    callBack.onSuccess(model, null);
                                }
                                findModel.relation = resultModel.getData().getRelation();
                                setFindUserSatus(context, me_follow, findModel.uid + "", resultModel.getData().getRelation());
                            }
                        }
                    }
                });
    }


    /**
     * 关注
     *
     * @param me_follow
     */
    public static void onFindUserGuanZhu(final Context context, final ImageView me_follow, final FindModel findModel, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", findModel.uid);

        String requestUrl = ApiX1.API_USER_FOLLOWBATH;
        if (MyConfig.Relation_Both.equals(findModel.relation)
                || MyConfig.Relation_Following.equals(findModel.relation)) {
            requestUrl = String.format(requestUrl, "unfollow");
        } else {
            requestUrl = String.format(requestUrl, "followed");
        }

        ApiX1.getInstance().postUserFollow(MyApplication.Instance, requestUrl,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {
                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                ResultModel model = new ResultModel();
                                model.msg = resultModel.getData().getRelation();
                                if (callBack != null) {
                                    callBack.onSuccess(model, null);
                                }
                                findModel.relation = resultModel.getData().getRelation();
                                setFindUserSatus(context, me_follow, findModel.uid + "", resultModel.getData().getRelation());
                            }
                        }
                    }
                });
    }


    /**
     * 关注
     *
     * @param me_follow
     */
    public static void onFindUserGuanZhu(final Context context, final TextView me_follow, final TongTuanModel.UserListEntity findModel, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", findModel.uid);

        String requestUrl = ApiX1.API_USER_FOLLOWBATH;
        if (MyConfig.Relation_Both.equals(findModel.relation)
                || MyConfig.Relation_Following.equals(findModel.relation)) {
            requestUrl = String.format(requestUrl, "unfollow");
        } else {
            requestUrl = String.format(requestUrl, "followed");
        }

        ApiX1.getInstance().postUserFollow(MyApplication.Instance, requestUrl,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {
                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                ResultModel model = new ResultModel();
                                model.msg = resultModel.getData().getRelation();
                                if (callBack != null) {
                                    callBack.onSuccess(model, null);
                                }
                                findModel.relation = resultModel.getData().getRelation();
                                setFindUserSatus(context, me_follow, findModel.uid + "", resultModel.getData().getRelation());
                            }
                        }
                    }
                });
    }


    /**
     * 关注
     *
     * @param me_follow
     */
    public static void onFindUserGuanZhu(final Context context, final TextView me_follow, final FriendModel findModel, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", findModel.uid);

        String requestUrl = ApiX1.API_USER_FOLLOWBATH;
        if (MyConfig.Relation_Both.equals(findModel.relation)
                || MyConfig.Relation_Following.equals(findModel.relation)) {
            requestUrl = String.format(requestUrl, "unfollow");
        } else {
            requestUrl = String.format(requestUrl, "followed");
        }

        ApiX1.getInstance().postUserFollow(MyApplication.Instance, requestUrl,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {
                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                ResultModel model = new ResultModel();
                                model.msg = resultModel.getData().getRelation();
                                if (callBack != null) {
                                    callBack.onSuccess(model, null);
                                }
                                findModel.relation = resultModel.getData().getRelation();
                                setFindUserSatus(context, me_follow, findModel.uid + "", resultModel.getData().getRelation());
                            }
                        }
                    }
                });
    }


    // 设置关注状态
    public static void setFindUserSatus(Context mContext, TextView me_follow, String uid, String relation) {

        UserModel user = UserModel.getAccount(mContext);
        if (user != null && user.uid.equals(uid)) {
            me_follow.setVisibility(View.INVISIBLE);
        } else {
            if ("both".equals(relation)) {
                me_follow.setText("已关注");
                me_follow.setTextColor(MyConfig.Color999);
                me_follow.setBackgroundResource(R.drawable.shape_find_guanzhu_s);
                me_follow.setVisibility(View.VISIBLE);
            } else if ("following".equals(relation)) {
                me_follow.setVisibility(View.VISIBLE);
                me_follow.setTextColor(MyConfig.Color999);
                me_follow.setBackgroundResource(R.drawable.shape_find_guanzhu_s);
                me_follow.setText("已关注");
            } else {
                me_follow.setVisibility(View.VISIBLE);
                me_follow.setBackgroundResource(R.drawable.shape_find_guanzhu_yellow);
                me_follow.setTextColor(MyConfig.Color333);
                me_follow.setText("+关注");
            }
        }
    }


    // 设置关注状态
    public static void setFindUserSatus(Context mContext, ImageView me_follow, String uid, String relation) {

        MyLog.debug("relation :" + relation);
        UserModel user = UserModel.getAccount(mContext);
        if (user != null && user.uid.equals(uid)) {
            me_follow.setVisibility(View.INVISIBLE);
        } else {
            if ("following".equals(relation) || "both".equals(relation)) {
                me_follow.setImageResource(R.drawable.icon_find_guanzhu1_un);
            } else {
                me_follow.setImageResource(R.drawable.icon_find_guanzhu1_s);
            }
        }
    }


    /***
     *
     * 修改个人背景
     *
     * @param filePath 头像的路径
     */

    public void updateUserBg(String filePath) {

        RequestParams params = new RequestParams();
        params.put("appBgPic", filePath);
        ApiX1.getInstance().baseReuqestUrl(ApiX1.Api_UpdateAvatar,
                params, new GsonCallBack<JSONObject>() {

                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {

                    }

                    @Override
                    public void onSuccess(GsonResultModel<JSONObject> obj, Object obj1) {

                        UtilHelper.showToast(obj.getMsg());
                        if (obj.isSucc()) {
                            EventBusWrap.post(new MeEvent.MeOpEvent(MeEvent.MeOpEvent.MeType.SET_USERAVATOR));
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {

                    }
                });
    }



    /***
     *
     * 修改个人头像
     *
     * @param filePath 头像的路径
     */

    public void updateUserAvator(String filePath) {

        RequestParams params = new RequestParams();
        params.put("image", filePath);
        ApiX1.getInstance().baseReuqestUrl(ApiX1.Api_UpdateAvatar,
                params, new GsonCallBack<JSONObject>() {

                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {

                    }

                    @Override
                    public void onSuccess(GsonResultModel<JSONObject> obj, Object obj1) {

                        UtilHelper.showToast(obj.getMsg());
                        if (obj.isSucc()) {
                            EventBusWrap.post(new MeEvent.MeOpEvent(MeEvent.MeOpEvent.MeType.SET_USERAVATOR));
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {

                    }
                });
    }


    //分享成功调用
    public void onShareSuccess(String shareType, String id) {

        RequestParams requestParams = new RequestParams();
        requestParams.put("type", shareType);
        requestParams.put("id", id);
        ApiX1.getInstance().baseReuqestUrl(ApiX1.Api_ShareSuccess,
                requestParams, new GsonCallBack<JSONObject>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {


                    }

                    @Override
                    public void onSuccess(GsonResultModel<JSONObject> resultModel, Object obj) {

                    }
                });
    }


    //获得代金劵
    public void couponGet(String id, final int isFromScan) {


        RequestParams params = new RequestParams();
        params.put("coupon_id", id);

        ApiX1.getInstance().couponGet(params, new GsonCallBack<JSONObject>() {
            @Override
            public void onFailure(int statusCode, Throwable arg0) {
                // TODO Auto-generated method stub

                handleError(statusCode);
            }

            @Override
            public void onSuccess(GsonResultModel<JSONObject> obj, Object obj1) {

                if (obj.isSucc()) {

                    if (isFromScan == 0) {
                        EventBusWrap.post(new BaseEvent("success"));
                    } else if (isFromScan == 99) {
                        EventBusWrap.post(new BaseEvent(99));
                    }

                } else {
                    UtilHelper.showToast(obj.getMsg());
                }
            }
        });


    }

//    //获得代金劵
//    public void couponGet(String id) {
//
//        couponGet(id,-1);
//
//    }

    /**
     * 网易七鱼
     *
     * @return
     */
    // 如果返回值为null，则全部使用默认参数。
    private static YSFOptions options() {

        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }

    /**
     * @param product 产品的model
     */
    public static void onLineServer(Context context, boolean isFromOrder, int groupId, YSFonLineModel product) {

        Unicorn.init(context, MyConfig.WangYiQiYuAppKey, options(), new UILImageLoader());
        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
        UserModel model = UserModel.getAccount(MyApplication.Instance);
        YSFUserInfo userInfo = new YSFUserInfo();
        String username = "";
        if (model != null) {

            userInfo.userId = model.uid;
            username = model.username;
            if (!TextUtils.isEmpty(model.realname)) {
                username = model.realname;
            }
            if (product != null) {

                userInfo.data = product.data;
            }
            Unicorn.setUserInfo(userInfo);
        }
        ConsultSource source = new ConsultSource("", "游侠 " + username, "游侠客APP");
        source.groupId = groupId;
        if (product != null) {
            ProductDetail.Builder builder = new ProductDetail.Builder();

            if (!TextUtils.isEmpty(product.product_name)) {

                if (isFromOrder) {
                    builder.setTitle(product.product_name)
                            .setNote("订单编号:" + product.oid)
                            .setPicture(product.picture)
                            .setDesc(product.sub_name)
                            .setAlwaysSend(true)
                            .setShow(1)
                            .setUrl(product.url_string);
                } else {
                    builder.setTitle(product.product_name)
                            .setNote(product.price)
                            .setDesc(product.sub_name)
                            .setPicture(product.picture)
                            .setShow(1)
                            .setAlwaysSend(true)
                            .setUrl(product.url_string);
                }
            }

            source.productDetail = builder.create();
        }


        Hawk.put(Constant.SP_QIYU_SOURCE, source);
        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
        Unicorn.openServiceActivity(MyApplication.Instance, // 上下文
                "在线客服", // 聊天窗口的标题
                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等.
        );

        YSFOptions options = new YSFOptions();

        options.onMessageItemClickListener = new OnMessageItemClickListener() {
            @Override
            public void onURLClicked(Context context, String result) {

                MyLog.debug("result :" + result);


                if (result != null && result.contains("lines")) {

                    Pattern urlPattern = Pattern
                            .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
                    String pid = null;
                    Matcher matcher = urlPattern.matcher(result);
                    while (matcher.find()) {
                        if (matcher.groupCount() >= 3) {
                            pid = matcher.group(2);
                        }
                    }


                    UrlUtil.UrlEntity parse = UrlUtil.parse(result);

                    String code = "";
                    if (parse.params != null) {

                        if (parse.params.containsKey("spm")) {

                            String spm = parse.params.get("spm");

                            MyLog.debug("spm :" + spm);

                            try {

                                byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(spm.getBytes());

                                if (decode == null) {
                                    code = "";
                                }
                                String results = new String(decode);
                                MyLog.debug("spm result:" + results);
                                JSONObject jsonObject = new JSONObject(results);

                                String codes = jsonObject.optString("code");
                                String original_id = jsonObject.optString("original_id");
                                code = codes + "&" + original_id + "&" + pid;
                                MyLog.debug("spm code:" + code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(pid)) {
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("pid", pid.replaceAll("[^\\d]+", ""));
                        intent.putExtra(Constant.INTENT_ZT_PROTOCOL, code);
                        context.startActivity(intent);
                        return;
                    }

                }
                if (result != null && result.contains("visas")) {

                    //签证详情
                    Pattern visaPattern = Pattern
                            .compile("(^|&|\\?)+vid=+([^&]*)(&|$)");
                    String vsia_id = null;
                    Matcher visaMatcher = visaPattern.matcher(result);
                    while (visaMatcher.find()) {
                        if (visaMatcher.groupCount() >= 3) {
                            vsia_id = visaMatcher.group(2);
                        }
                    }
                    if (!TextUtils.isEmpty(vsia_id)) {
                        Intent intent = new Intent(context, VisaDetailActivity.class);
                        intent.putExtra(Constant.INTENT_DETAIL_ID, UtilHelper.ParseInt(vsia_id));
                        context.startActivity(intent);
                        return;
                    }

                }

                //订单详情客服

                Pattern urlPatternOid = Pattern
                        .compile("(^|&|\\?)+oid=+([^&]*)(&|$)");
                String oid = null;
                Matcher matcherOid = urlPatternOid.matcher(result);
                while (matcherOid.find()) {
                    if (matcherOid.groupCount() >= 3) {
                        oid = matcherOid.group(2);
                    }
                }
//                if (!TextUtils.isEmpty(oid)) {
//
//                    if (result.contains("visa")) {
//                        Intent intent = new Intent(context, VisaOrderDetailActivity.class);
//                        intent.putExtra("oid", oid);
//                        context.startActivity(intent);
//
//                    } else {
//
//                        Intent intent = new Intent(context, OrderDetailActivity.class);
//                        intent.putExtra("oid", oid);
//                        context.startActivity(intent);
//                    }
//
//                    return;
//                }


                String[] split = result.split(":");
                if (split != null && split.length == 2 && "visadetail".equals(split[0])) {
                    Intent intent = new Intent(context, VisaDetailActivity.class);
                    intent.putExtra(Constant.INTENT_DETAIL_ID, UtilHelper.ParseInt(split[1]));
                    context.startActivity(intent);
                    return;
                }
//
//                Intent intent = new Intent(context, MyWebViewActivity.class);
//                intent.putExtra("url", result);
//                context.startActivity(intent);
                requestProtocol(context, result);

            }
        };

        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.downTimeToggle = true;
        config.bigIconUri = "http://gallery.youxiake.com/Public/Data/upload/yxk_logo.png";
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        config.notificationEntrance = MainActivity.class;
//        options.statusBarNotificationConfig = config;

        UICustomization ui = new UICustomization();
//        ui.avatarShape = 0;
        UserModel mUser = UserModel.getAccount(context);
        if (mUser != null) {
            ui.rightAvatar = mUser.upic;
        }
//        ui.titleBarStyle = 0;
        options.uiCustomization = ui;
        Unicorn.updateOptions(options);
    }


    /**
     * des加密
     *
     * @param str
     * @return
     */
    public static String encryptDes(String str) {
        try {
            return UtilHelper.encryptDes(str, DESKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * des 解密
     *
     * @param str
     * @return
     */
    public static String decryptDes(String str) {

        try {
            return UtilHelper.decryptDes(str, DESKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 处理token错误
     *
     * @param context
     * @param code
     */
    public void handleError(Context context, int code) {

//        if(code== BaseApi.Code_ReLoginError){
//
//            MyLog.debug("handleError and login:"+code);
//            UserModel.loginOut(context);
//
//            Intent intent = new Intent(context, LoginActivity.class);
//            intent.putExtra("IsFinish", "true");
//            context.startActivity(intent);

    }

    /**
     * 处理token错误
     */
    public void handleError(int code) {

//        if(code== BaseApi.Code_ReLoginError){
//            MyLog.debug("handleError:"+code);
//            UserModel.loginOut(MyApplication.Instance);
//        }
    }

    /**
     * // 设置关注状态
     *
     * @param context
     * @param me_follow
     * @param uid
     * @param relation
     */

    public static void setFollowSatus(Context context, ImageView me_follow, String uid, String relation) {

        UserModel user = UserModel.getAccount(context);
        if (me_follow == null) {
            return;
        }
        if (user != null && user.uid.equals(uid)) {
            me_follow.setVisibility(View.INVISIBLE);
        } else {
            if (MyConfig.Relation_Both.equals(relation) ||
                    MyConfig.Relation_Following.equals(relation)) {
                me_follow.setVisibility(View.INVISIBLE);
            } else {
                me_follow.setVisibility(View.VISIBLE);
                me_follow.setImageResource(R.drawable.find_guanzhu);
            }
        }
    }

    /**
     * 显示 关注 相互关注界面
     *
     * @param context
     * @param me_follow
     * @param uid
     * @param relation
     */
    public static void setFollowStatus(Context context, ImageView me_follow, String uid, String relation) {

        UserModel user = UserModel.getAccount(context);
        if (user != null && user.uid.equals(uid)) {
            me_follow.setVisibility(View.INVISIBLE);
        } else {
            me_follow.setVisibility(View.VISIBLE);
            if (MyConfig.Relation_Both.equals(relation)) {
                me_follow.setImageResource(R.drawable.find_guanzhu_both);
            } else if (MyConfig.Relation_Following.equals(relation)) {
                me_follow.setImageResource(R.drawable.find_guanzhuing);
            } else {
                me_follow.setImageResource(R.drawable.find_guanzhu);
            }
        }
    }

    /**
     * 关注 取消关注
     *
     * @param context
     * @param me_follow
     * @param follow_uid
     */
    public static void onFollowStatus(final Context context, final ImageView me_follow,
                                      final int follow_uid, final String relation, final GuanZhuCallback onCallback) {
        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", follow_uid);

        String requestUrl = ApiX1.API_USER_FOLLOWBATH;
        if (MyConfig.Relation_Both.equals(relation)
                || MyConfig.Relation_Following.equals(relation)) {
            requestUrl = String.format(requestUrl, "unfollow");
        } else {
            requestUrl = String.format(requestUrl, "followed");
        }
        ApiX1.getInstance().baseReuqestUrl(requestUrl, params, new GsonCallBack<JSONObject>() {
            @Override
            public void onFailure(int statusCode, Throwable arg0) {
            }

            @Override
            public void onSuccess(GsonResultModel<JSONObject> resultModel, Object obj1) {

                UtilHelper.showToast(resultModel.getMsg());
                if (resultModel.isSucc()) {
                    JSONObject json = resultModel.getData();
                    try {
                        String relation = json.getString("relation");
                        setFollowStatus(context, me_follow, String.valueOf(follow_uid), relation);
                        onCallback.onSuccess(relation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface GuanZhuCallback {
        public void onSuccess(String relative);
    }

    /**
     * // 设置关注状态
     *
     * @param context
     * @param me_follow
     * @param uid
     * @param relation
     */

    public static void setFollowSatusView(Context context, ImageView me_follow, String uid, String relation, View view) {

        UserModel user = UserModel.getAccount(context);
        if (user != null && user.uid.equals(uid)) {
            me_follow.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);
        } else {
            if ("both".equals(relation) || "following".equals(relation)) {
                me_follow.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
            } else {
                me_follow.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                me_follow.setImageResource(R.drawable.find_guanzhu);
            }
        }
    }

    /**
     * 关注
     *
     * @param context
     * @param me_follow
     * @param follow_uid
     */
    public static void onFollowView(final Context context, final ImageView me_follow, final int follow_uid,
                                    final View view, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", follow_uid);
        ApiX1.getInstance().postUserFollow(context, params, new GsonCallBack<FollowModel>() {
            @Override
            public void onFailure(int statusCode, Throwable arg0) {
            }

            @Override
            public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                UtilHelper.showToast(resultModel.getMsg());
                if (resultModel.isSucc()) {
                    if (resultModel.getData() != null) {
                        ResultModel model = new ResultModel();
                        model.msg = resultModel.getData().getRelation();
                        callBack.onSuccess(model, null);
                        setFollowSatusView(context, me_follow, follow_uid + "", resultModel.getData().getRelation(), view);
                    }
                }
            }
        });
    }


    /**
     * 关注
     *
     * @param context
     * @param me_follow
     * @param follow_uid
     */
    public static void onFollow(final Context context, final ImageView me_follow, final int follow_uid) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }

        RequestParams params = new RequestParams();
        params.put("follow_uid", follow_uid);
        ApiX1.getInstance().postUserFollow(MyApplication.Instance,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {

                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                setFollowSatus(context, me_follow, follow_uid + "", resultModel.getData().getRelation());

                                if (me_follow == null) {

                                    EventBusWrap.post(new BaseEvent(true));
                                }
                            }
                        }
                    }
                });
    }


    /**
     * 关注
     *
     * @param context
     * @param me_follow
     * @param follow_uid
     */
    public static void onFollowView(final Context context, final ImageView me_follow, final int follow_uid, final CallBack callBack) {

        final UserModel user = UserModel.getAccount(context);
        if (user == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("IsFinish", "true");
            context.startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        params.put("follow_uid", follow_uid);
        ApiX1.getInstance().postUserFollow(MyApplication.Instance,
                params, new GsonCallBack<FollowModel>() {
                    @Override
                    public void onFailure(int statusCode, Throwable arg0) {
                    }

                    @Override
                    public void onSuccess(GsonResultModel<FollowModel> resultModel, Object obj) {

                        UtilHelper.showToast(resultModel.getMsg());
                        if (resultModel.isSucc()) {
                            if (resultModel.getData() != null) {
                                ResultModel model = new ResultModel();
                                model.msg = resultModel.getData().getRelation();
                                callBack.onSuccess(model, null);
                                setFollowSatus(context, me_follow, follow_uid + "", resultModel.getData().getRelation());
                            }
                        }
                    }
                });
    }


    public static String getPhoneContacts(Context context, Uri uri) {
        String phone = "此联系人暂未存入号码";
        MyLog.debug("phone1 :" + phone);
        //得到ContentResolver对象
        try {
            CursorLoader cursorLoader = new CursorLoader(context, uri, null, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor.moveToFirst()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phones = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                        null,
                        null);
                if (phones.moveToFirst()) {
                    phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
            cursor.close();
        } catch (Exception e) {

            phone = "";
        }
        String replace = phone.replace("+", "");
        String replace1 = replace.replace("-", "");
        String replace2 = replace1.replace(" ", "");
        MyLog.debug("phone2 :" + phone);
        return replace2;
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
                        if (model.handler.equals("productmd")) {
                            String[] split = model.url.split("&");
                            if (split.length == 3) {
                                model.statisticsCode = model.url;
                                model.url = split[2];
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
}
