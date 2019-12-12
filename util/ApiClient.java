package com.youxiake.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.youxiake.api.ApiX1;
import com.youxiake.app.MyApplication;
import com.youxiake.app.MyConfig;
import com.youxiake.controls.CollectsListModel;
import com.youxiake.listener.CallBack;
import com.youxiake.listener.GsonCallBack;
import com.youxiake.model.ActModel;
import com.youxiake.model.ActivityIndexModel;
import com.youxiake.model.AppConfigModel;
import com.youxiake.model.BaseDataModel;
import com.youxiake.model.BaseItemModel;
import com.youxiake.model.Batchs_list;
import com.youxiake.model.CollectModel;
import com.youxiake.model.DiscussionModel;
import com.youxiake.model.FindCommentModel;
import com.youxiake.model.FindModel;
import com.youxiake.model.FindPriseModel;
import com.youxiake.model.FriendModel;
import com.youxiake.model.GsonResultModel;
import com.youxiake.model.HomeModel;
import com.youxiake.model.MonthProductModel;
import com.youxiake.model.MsgBoxModel;
import com.youxiake.model.OrderDateListModel;
import com.youxiake.model.OrderDetailModel;
import com.youxiake.model.OrderModel;
import com.youxiake.model.PreOrderModel;
import com.youxiake.model.ProductDetailModel;
import com.youxiake.model.ProductListMode;
import com.youxiake.model.ProductModel;
import com.youxiake.model.ProductModes;
import com.youxiake.model.ResultModel;
import com.youxiake.model.SearchFilterModel;
import com.youxiake.model.SearchTagListModel;
import com.youxiake.model.UserContactModel;
import com.youxiake.model.UserDataModel;
import com.youxiake.model.UserInfoModel;
import com.youxiake.model.UserModel;
import com.youxiake.model.VersionItemModel;
import com.youxiake.model.VersionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 *
 */
public class ApiClient {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String versionName = MyApplication.Instance.getPackageInfo().versionName;


    static {
        client.setTimeout(25000);
        client.setUserAgent(UtilHelper.getUserAgent());
        try {
            client.setSSLSocketFactory(new MySSLSocketFactory(initSSL()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static KeyStore initSSL() throws CertificateException,
            IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream in = MyApplication.Instance.getAssets().open("load-der.crt");
        Certificate ca = cf.generateCertificate(in);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setCertificateEntry("ca", ca);

//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keystore);
//        SSLContext context = SSLContext.getInstance("TLS");
//        context.init(null, tmf.getTrustManagers(), null);
        return keystore;
    }

    // 服务器地址
    public final static String ApI_Url = MyConfig.getApp2_youxiake_com(); //


    // ======================================v2====================
    //public final static String Api_Index = "n.php/home,index,home"; // 首页数据

    // public final static String Api_Index = "n.php/home,index,homev2"; // 首页数据

    public final static String Api_Index = "n.php/home,v3,pager";

    public final static String Api_Activity = "n.php/activity,group,groups"; // 活动列表

    public final static String Api_ProductDetail = "n.php/activity,product,detail"; // 产品详情

    public final static String Api_MyOrderList = "orders"; // 我的订单列表

    public final static String Api_MyContactList = "n.php/mine,account,passenger"; // 常用户管理

    public final static String Api_NewMyContactList = "api/passenger/list"; // 常用户管理新接口

    public final static String Api_MyContactSave = "home/passenger/save"; // 常用户添加

    public final static String Api_MyContactDelete = "home/passenger/delete"; // 常用户删除

    public final static String Api_DiscoverAdd = "discover/quote/save"; // 添加发现

    public final static String Api_DiscoverDel = "discover/quote/delete"; // 发现删除

    public final static String Api_Login = "home/login/verify"; // 登录

    public final static String Api_FindList = "n.php/discover,feed,fetch"; // 发现列表

    public final static String Api_FindDetail = "n.php/discover,quote,detail"; // 发现明细

    public final static String Api_FindMyInfo = "n.php/user,account,userinfo"; // 发现个人详情

    public final static String Api_FindMeDetail = "n.php/user,account,quotes"; // 发现我的图说

    public final static String Api_FindCommentAdd = "discover/quote_comment/save"; // 增加发现评论

    public final static String Api_FindCommentDelete = "discover/quote_comment/delete"; // 删除发现评论

    public final static String Api_FindCommentList = "n.php/discover,quote,comments"; // 发现评论列表

    public final static String Api_FindPriseAdd = "discover/quote_prise/give"; // 发现点赞

    public final static String Api_FindPriseCancel = "discover/quote_prise/cancel"; // 发现点赞取消

    public final static String Api_DiscusList = "n.php/discover,quote,discus"; // 话题列表

    public final static String Api_OrderDate = "n.php/activity,product,batchs"; // 出行日期

    // ======================================v2====================
    // public final static String Api_OrderBook = "book"; // 订单预定
    public final static String Api_OrderBook = "book_v2"; // 订单预定

    // ======================================v2====================
    // public final static String Api_OrderSave = "book/save"; // 订单保存
    public final static String Api_OrderSave = "book_v2/save"; // 订单保存

    // ======================================v2====================
    // public final static String Api_OrderUpdate = "orders/updateorder";//
    // 订单修改
    public final static String Api_OrderUpdate = "orders_v2/updateorder";// 订单修改

    // ======================================v2====================
    // public final static String Api_OrderDetail = "orders/details"; // 订单详情
    public final static String Api_OrderDetail = "orders_v2/details"; // 订单详情

    // ======================================v2====================
    public final static String Api_CalcFee = "orders_v2/calcFee"; // 订单详情

    public final static String Api_OrderDateCheck = "book/select_batch";// 订单添加常用户界面
    // －－日期框－－订单批次检查
    // －－日期框－－订单批次检查
    // ======================================v2====================
    public final static String Api_OrderDateChange = "orders_v2/changedate"; // 订单详情界面
    // －－日期框
    // －－修改日期
    // ======================================v2====================
    public final static String Api_OrderPayMode = "orderspay_v2/paymode"; // 支付检查

    public final static String Api_AppConfigs = "n.php/common,base,configs";// 配置信息 (包含更新版本号和app下载地址)

    public final static String Api_SearchProduct = "n.php/common,search,product"; // 查询产品

    public final static String Api_SendCode = "iservices/sendmobileauthcode"; // 发送手机号

    //	public final static String Api_Reg = "register/add"; // 注册 android 2.2.2之前使用

    public final static String Api_Reg = "register_v2/add"; // 注册 android 2.2.2之后使用，增加性别属性，优化验证流程

    public final static String Api_City = "n.php/common,base,citys"; // 城市列表

    public final static String Api_MyFind = "n.php/mine,account,quotes";// 我的图说

    public final static String Api_MyFindDelete = "discover/quote/delete";// 删除我的图说

    public final static String Api_UpdateImg = "http://www.youxiake.com/newzone-modifyavatar-avaterUploadForApp.html"; // 上传图片

    public final static String Api_AltPwd = "login/change_password"; // 修改密码

    //	public final static String Api_NegNext= "register/next"; // 注册点击 下一步

    public final static String Api_NegNext = "register_v2/next"; // android 2.2.2 之后 ，注册点击 下一步

    public final static String Api_KuiqianPay = "kuaipay"; // 快钱支付接口

    public final static String APi_MyInfo = "n.php/mine,account,userinfo"; // 个人信息

    public final static String Api_MyInfoUpdate = "usercenter/change_profile";// 修改个人资料

    public final static String Api_AltMobile = "login/change_phone"; // 修改手机号

    public final static String Api_LinCollect = "/lines/favorites";// 线路收藏

    public final static String Api_SendForgotCode = "iservices/sendmobileauthcode"; // 发送验证码

    public final static String Api_VerifyForgotCode = "register/check_verify_code"; // 检测验证码

    public final static String Api_ResetPassword = "register/reset_password"; // 重制密码

    public final static String Api_BugReport = "n.php/common,bug_report"; // 意见建议

    public final static String Api_MeLogo = "assets/app_resource/images/myhead.png";

    public final static String Api_WxGetPay = "wxpay/"; // 微信预先支付获取支付信息

    public final static String Api_AliGetPay = "alipay"; // 支付宝预先支付获取支付信息

    public final static String Api_SNSVerify = "oAuth/verify"; // 第三方登陆验证

    public final static String Api_SNSBindUser = "oAuth/bind"; // 第三方登陆验证-绑定已有用户

    //	public final static String Api_SNSBindRegUser = "register/oauth"; // 第三方登陆验证-绑定注册用户  android2.2.2 之前使用

    public final static String Api_SNSBindRegUser = "register_v2/oauth"; // 第三方登陆验证-绑定注册用户  android2.2.2 之前使用

    public final static String Api_Cancel_Order = "orders/cancel_order"; // 取消订单

    public final static String Api_Pre_Detail_collect = "/lines/favorite";// 线路收藏
    // ---------------分享链接
    public final static String Api_ShareProduct = "http://wap.youxiake.com/lines.html?id=%s";

    // ===========================html5 页面===========================
    // 产品详情
    public final static String Api_ProductDetail_Html = "assets/app_resource/product_detail.html";
    // 产品Meta
    public final static String Api_ProductMeta_Html = "assets/app_resource/product_meta.html";
//    // 关于我们
//    public final static String Api_AboutUs = "assets/app_resource/aboutus.html";
    // 关于我们
    public final static String Api_AboutUs = "html/aboutus";
    // 优惠券使用帮助
    public final static String Api_help = "api/coupon/help";
    // 使用协议
    public final static String Api_Licence = "assets/app_resource/licence.html";
    // 下单协议
    public final static String Api_OrderLicence = "agree";

    // =======================1.2版本新增接口=======================
    // 推荐游侠
    public final static String Api_FriendRecommand = "n.php/user,follow,recommend";
    // 同团好友
    public final static String Api_Samelines = "n.php/user,follow,same_lines";
    // 通讯录好友
    public final static String Api_Friendcontact = "n.php/user,follow,contact";
    // 附近好友
    public final static String Api_FriendNearby = "n.php/user,follow,nearby";
    // 关注
    public final static String Api_Following = "discover/follow_relation/following";
    // 关注的游侠
    public final static String Api_List_Following = "n.php/mine,account,following";
    // 粉丝
    public final static String Api_List_Follower = "n.php/mine,account,follower";
    // 查询游侠
    public final static String Api_Search_Friend = "n.php/common,search,user";
    // 保存经纬度
    public final static String Api_Save_Gps = "discover/lbs/pin";
    // 主页关注的游侠
    public final static String Api_My_Following = "n.php/user,account,following";
    // 主页粉丝
    public final static String Api_My_Follower = "n.php/user,account,follower";
    // 消息列表
    public final static String Api_MsgList = "discover/message/msgbox";
    // 我的详情
    public final static String Api_MyInfo = "n.php/mine,account,userinfo";

    // 我的关注的游侠
    public final static String Api_Mine_Following = "n.php/mine,account,following";
    // ?我的粉丝
    public final static String Api_Mine_Follower = "n.php/mine,account,follower";
    // 是否显示动态
    public final static String Api_Mine_ShowBooking = "privacy/showBooking";

    // ---------首页改动------------
    public final static String Api_MonthProductList = "n.php/activity,product_v2,products";

    // 查询产品
    public final static String Api_SearchProduct_V2 = "n.php/common,search_v2,product";
    //刷选菜单列表
    public final static String Api_Product_Menu_V2 = "n.php/activity,product_v2,products";


    public static String getApi(String url) {
        return ApI_Url + url;
    }





    // 带参数，获取json对象或者数组
    public static void post(final CallBack callback, final String urlString,
                            RequestParams params, final JsonHttpResponseHandler res) {

        //client.removeAllHeaders();

        if (!UtilHelper.isNetworkConnected()) {
            UtilHelper.showToast("网络已经断开");
            callback.onFailure(null);
            return;
        }

        if (params == null) {
            params = new RequestParams();
        }

        params.put("os", "android"); // 操作系统
        params.put("version", versionName);// 版本

        if (MyConfig.SelLocation != null) { // 地理位置
            params.put("city", MyConfig.SelLocation.name);
            params.put("province", MyConfig.SelLocation.province);
        }

        UserModel acc = UserModel.getAccount(MyApplication.Instance);
        if (acc != null) {
            params.put("token", acc.token);
        }

        MyLog.debug("Rquest url : " + urlString + "  \t params: " + params);

        client.post(urlString, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                // TODO Auto-generated method stub
                MyLog.debug(response.toString());
                res.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // TODO Auto-generated method stub
                if (throwable.getMessage().contains("timed push_nav_out")) {
                    UtilHelper.showToast("网络链接超时");
                } else {

                    MyLog.debug(errorResponse + "," + statusCode
                            + ", onFailure2 : url:" + urlString + ","
                            + throwable.getMessage());
                    res.onFailure(statusCode, headers, "", throwable);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // TODO Auto-generated method stub
                if (throwable.getMessage() != null) {
                    if (throwable.getMessage().contains("timed push_nav_out")) {
                        UtilHelper.showToast("网络链接超时");
                    } else {
                        UtilHelper.showToast("网络繁忙");
                    }

                    MyLog.debug("youxiake" + errorResponse + ","
                            + statusCode + ", onFailure2 : url:" + urlString
                            + "," + throwable.getMessage());
                    res.onFailure(statusCode, headers, "", throwable);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  String responseString) {
                // TODO Auto-generated method stub
                MyLog.debug(+statusCode + ", onSuccess : url:" + urlString
                        + ",");
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                if (throwable.getMessage().contains("timed push_nav_out")) {
                    UtilHelper.showToast("网络链接超时");
                } else {
                    UtilHelper.showToast("网络繁忙");
                    MyLog.debug(statusCode + ", onFailure4 : url:" + urlString
                            + "  \t" + responseString + ","
                            + throwable.getMessage() + "--result");
                    res.onFailure(statusCode, headers, responseString,
                            throwable);
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                MyLog.debug(statusCode + ", onSuccess json data  :"
                        + response.toString());
                res.onSuccess(statusCode, headers, response);
            }

        });
    }



    public static void postDirect(String url, RequestParams params,
                                  AsyncHttpResponseHandler handler) {

        client.removeAllHeaders();

        if (!UtilHelper.isNetworkConnected()) {
            UtilHelper.showToast("网络已经断开");
            handler.onFailure(0,null,(byte[])null,null);
            return;
        }

        if (params == null) {
            params = new RequestParams();
        }

        params.put("os", "android"); // 操作系统
        params.put("version", versionName);// 版本

        if (MyConfig.SelLocation != null) { // 地理位置
            params.put("city", MyConfig.SelLocation.name);
            params.put("province", MyConfig.SelLocation.province);
        }

        UserModel acc = UserModel.getAccount(MyApplication.Instance);
        if (acc != null) {
            params.put("token", acc.token);
        }

        MyLog.debug("Rquest url : " + url + "  \t params: " + params);

        client.post(url, params, handler);
    }


    // 解析数据
    public static BaseDataModel parseIndexModel(JSONObject data) {

        if (data == null) {
            return null;
        }

        BaseDataModel model = new BaseDataModel();
        model.alias = data.optString("alias");
        model.uri = data.optString("uri");
        model.name = data.optString("name");
        model.moreLink = data.optString("moreLink");
        model.handler = data.optString("handler");
        model.more = data.optString("more");
        model.itemList = UtilHelper.parseList(data.optJSONArray("itemsList"),
                BaseItemModel.class);

        JSONArray array = data.optJSONArray("extends");
        if (array != null && array.length() > 0) {
            List<BaseDataModel> extend = new ArrayList<BaseDataModel>();
            try {
                for (int i = 0; i < array.length(); ++i) {
                    JSONObject jo = array.getJSONObject(i);
                    BaseDataModel _data = parseIndexModel(jo);
                    extend.add(_data);
                }
                model.extend = extend;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                MyLog.debug(" parseIndexModel Error: " + e.getMessage());
            }
        }
        return model;
    }

    // 获取首页
    public static void getIndex(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_Index);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    ResultModel rl = new ResultModel();
                    rl.code = response.optInt("code");
                    rl.msg = response.optString("msg");
                    rl.cache = response.optString("cache");
                    HomeModel model = new HomeModel();
                    JSONObject data = response.optJSONObject("data");
                    model.cache = rl.cache;
                    model.flash = parseIndexModel(data.optJSONObject("flash"));
                    model.navs = parseIndexModel(data.optJSONObject("navs"));
                    model.route = parseIndexModel(data.optJSONObject("route"));
                    model.notice = parseIndexModel(data.optJSONObject("notice"));
                    model.topic1 = parseIndexModel(data.optJSONObject("topic1"));
                    model.topic2 = parseIndexModel(data.optJSONObject("topic2"));
                    model.feedList = parseIndexModel(data
                            .optJSONObject("feedList"));
                    model.searchListFilter = parseIndexModel(data
                            .optJSONObject("searchListFilter"));
                    model.special = parseIndexModel(data
                            .optJSONObject("special"));
                    model.cnList = parseIndexModel(data.optJSONObject("cnList"));
                    rl.data = model;

                    callback.onSuccess(rl, null);
                } catch (Exception e) {
                    MyLog.debug("onSuccess parse Error: " + e.getMessage());
                }
            }
        });
    }

    // 目的地精选
    public static void getsearchListFilter(String url, RequestParams params,
                                           final CallBack callback) {

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    ProductModes mode = new ProductModes();
                    mode.totalPage = data.optInt("totalPage");
                    mode.products = UtilHelper.parseList(
                            data.optJSONArray("productList"),
                            ProductListMode.class);
                    rl.data = mode;
                }
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    // 发现列表
    public static void getFindList(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_FindList);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    rl.data = UtilHelper.parseList(
                            data.optJSONArray("quotesList"), FindModel.class);
                }
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 获取发现明细
    public static void getFindDetail(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(Api_FindDetail);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                // rl.data = UtilHelper.parseList(data.optJSONArray("list"),
                // HotelModel.class);
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 线路收藏列表
    // 获取发现明细
    public static void getLineCollectList(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_LinCollect);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    CollectsListModel mode = new CollectsListModel();
                    // JSONArray ja = data.optJSONArray("favoriteList");
                    // for (int i = 0; i < ja.length(); i++) {
                    // JSONObject object = ja.optJSONObject(i);
                    // CollectModel collect = new CollectModel();
                    // collect.id = object.optString("id");
                    // collect.uid = object.optString("uid");
                    // collect.pid = object.optString("pid");
                    // collect.addtime = object.optString("addtime");
                    // collect.product_name = object.optString("product_name");
                    // collect.product_sub_name =
                    // object.optString("product_sub_name");
                    // collect.product_pic = object.optString("product_pic");
                    // PriceModel pModel = new PriceModel();
                    // JSONObject priceModel = object.optJSONObject("price");
                    // pModel.price_status =
                    // priceModel.optString("price_status");
                    // pModel.price_label = priceModel.optString("price_label");
                    // pModel.adultprice = priceModel.optString("adultprice");
                    // pModel.childprice = priceModel.optString("childprice");
                    //
                    //
                    // }
                    mode.favoriteList = UtilHelper.parseList(
                            data.optJSONArray("favoriteList"),
                            CollectModel.class);
                    rl.data = mode;
                }
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 发现个人主页
    public static void getFindMeHome(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(Api_FindMeDetail);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("quotesList"),
                        FindModel.class);
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 发现个人图说
    public static void getFindMeDetail(RequestParams params,
                                       final CallBack callback) {

        final String url = getApi(Api_FindMeDetail);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if(data!=null){
                    rl.data = UtilHelper.parseList(data.optJSONArray("quotesList"),
                            FindModel.class);
                }
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 我的资料
    public static void getMeInfo(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_MyInfo);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    rl.data = UtilHelper.parseObject(
                            response.optJSONObject("data"), UserInfoModel.class);
                }
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 发现个人资料
    public static void getFindMeInfo(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(Api_FindMyInfo);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    rl.data = UtilHelper.parseObject(
                            response.optJSONObject("data"), UserInfoModel.class);
                }
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 话题
    public static void getDiscussion(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(Api_DiscusList);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub
                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("discuList"),
                        DiscussionModel.class);
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 活动类型列表
    public static void getActivity(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_Activity);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("list"),
                        ActModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 活动详情列表
    public static void getActivityDetail(String url, RequestParams params,
                                         final CallBack callback) {

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject json = response.optJSONObject("data");
                ActivityIndexModel indexModel = UtilHelper.parseObject(json,
                        ActivityIndexModel.class);

                MonthProductModel model = new MonthProductModel();

                model.totalPage = json.optInt("totalPage");
                model.pageSize = json.optInt("pageSize");

                JSONArray array = json.optJSONArray("listFilter");

                if (array.length() > 0) {
                    model.locationModel = UtilHelper.parseList(array
                                    .optJSONObject(0).optJSONArray("extends"),
                            SearchFilterModel.class);

                    model.alias = array.optJSONObject(0).optString("alias");
                    model.name = array.optJSONObject(0).optString("name");

                    model.orderModel = UtilHelper.parseObject(
                            array.optJSONObject(1), SearchFilterModel.class);

                    model.dayModel = UtilHelper.parseObject(
                            array.optJSONObject(2), SearchFilterModel.class);

                    model.typeModel = UtilHelper.parseObject(
                            array.optJSONObject(3), SearchFilterModel.class);
                }

                indexModel.filters = model;
                rl.data = indexModel;

                callback.onSuccess(rl, json);
            }
        });
    }

    // 产品日期列表
    public static void getOrderDateList(RequestParams params,
                                        final CallBack callback) {

        String url = getApi(Api_OrderDate);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper
                        .parseObject(data, OrderDateListModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 产品预定
    public static void orderBook(RequestParams params, final CallBack callback) {

        String url = getApi(Api_OrderBook);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                List<String> ls = new ArrayList<String>();

                if (data != null) {
                    JSONArray arr = data.optJSONArray("limited");

                    if (arr != null) {
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                ls.add(arr.getString(i));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    PreOrderModel model = UtilHelper.parseObject(data,
                            PreOrderModel.class);
                    model.limited = ls;
                    rl.data = model;
                }

                callback.onSuccess(rl, data);
            }
        });
    }

    // 订单明细
    public static void orderDetail(RequestParams params, final CallBack callback) {

        String url = getApi(Api_OrderDetail);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                List<String> ls = new ArrayList<String>();
                if (data != null) {
                    JSONArray arr = data.optJSONArray("limited");
                    if (arr != null) {

                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                ls.add(arr.getString(i));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    OrderDetailModel model = UtilHelper.parseObject(data,
                            OrderDetailModel.class);
                    model.limitedLs = ls;
                    rl.data = model;
                }

                callback.onSuccess(rl, data);
            }
        });
    }

    // 订单详情界面 －－日期框 －－修改日期
    public static void orderDateChange(RequestParams params,
                                       final CallBack callback) {

        String url = getApi(Api_OrderDateChange);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                List<String> ls = new ArrayList<String>();
                if (data != null) {
                    JSONArray arr = data.optJSONArray("limited");
                    if (arr != null) {

                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                ls.add(arr.getString(i));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    OrderDetailModel model = UtilHelper.parseObject(data,
                            OrderDetailModel.class);
                    model.limitedLs = ls;
                    rl.data = model;
                }
                callback.onSuccess(rl, data);
            }
        });
    }

    // 支付检查
    public static void orderPayMode(RequestParams params,
                                    final CallBack callback) {

        String url = getApi(Api_OrderPayMode);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseObject(data, OrderDetailModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 产品详情
    public static void getProductDetail(RequestParams params,
                                        final CallBack callback) {
        String url = getApi(Api_ProductDetail);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                ProductDetailModel model = UtilHelper.parseObject(data,
                        ProductDetailModel.class);
                if (data.optJSONArray("batchs_list") != null) {
                    model.batchs_list = UtilHelper.parseList(
                            data.optJSONArray("batchs_list"), Batchs_list.class);
                }
                model.batchs_list = UtilHelper.parseList(
                        data.optJSONArray("batchs_list"), Batchs_list.class);
                MyLog.error(model.batchs_list.toString());

                try {
                    JSONArray ja;
                    if (data != null) {
                        ja = data.getJSONArray("product_pic_list");
                        if (ja != null) {
                            model.product_pic_list = new ArrayList<String>();
                            for (int i = 0; i < ja.length(); ++i) {
                                model.product_pic_list.add(ja.getString(i));
                            }
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    MyLog.debug(" parse Error: " + e.getMessage());
                }
                rl.data = model;
                callback.onSuccess(rl, data);
            }
        });
    }

    // 登陆
    public static void login(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_Login);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    ResultModel rl = new ResultModel();
                    rl.code = response.optInt("code");
                    rl.msg = response.optString("msg");
                    rl.data = UtilHelper.parseObject(
                            response.optJSONObject("data"), UserModel.class);
                    callback.onSuccess(rl, null);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    MyLog.debug("onSuccess parse Error: " + e.getMessage());
                }

            }
        });
    }

    // 添加发现评论
    public static void addFindComment(RequestParams params,
                                      final CallBack callback) {

        final String url = getApi(Api_FindCommentAdd);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    ResultModel rl = new ResultModel();
                    rl.code = response.optInt("code");
                    rl.msg = response.optString("msg");
                    JSONObject data = response.optJSONObject("data");
                    FindCommentModel model = UtilHelper.parseObject(
                            data.getJSONObject("comment"),
                            FindCommentModel.class);
                    rl.data = model;
//                    model.commentsCount = data.getString("commentsCount");
                    callback.onSuccess(rl, data);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    MyLog.debug("onSuccess parse Error: " + e.getMessage());
                }

            }
        });
    }

    // 点赞
    public static void addFindPrise(RequestParams params,
                                    final CallBack callback) {

        final String url = getApi(Api_FindPriseAdd);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    ResultModel rl = new ResultModel();
                    rl.code = response.optInt("code");
                    rl.msg = response.optString("msg");
                    JSONObject data = response.optJSONObject("data");
                    if (data != null && !data.isNull("prise")) {
                        rl.data = UtilHelper.parseObject(
                                data.getJSONObject("prise"),
                                FindPriseModel.class);
                    }
                    callback.onSuccess(rl, data);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    MyLog.debug("onSuccess parse Error: " + e.getMessage());
                }

            }
        });
    }

    // 线路收藏

    // 点赞
    public static void lineCollect(RequestParams params, final CallBack callback) {

        final String url = getApi(Api_Pre_Detail_collect);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                MyLog.debug(response.toString());

                try {
                    ResultModel rl = new ResultModel();
                    rl.code = response.optInt("code");
                    rl.msg = response.optString("msg");
                    JSONObject data = response.optJSONObject("data");
                    callback.onSuccess(rl, data);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    MyLog.debug("onSuccess parse Error: " + e.getMessage());
                }

            }
        });
    }

    // 常用户管理
    public static void getMeContactList(RequestParams params,
                                        final CallBack callback) {

        final String url = getApi(Api_NewMyContactList);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(
                        data.optJSONArray("passengerList"),
                        UserContactModel.class);
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 删除常用户
    public static void getMeContactDelete(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_MyContactDelete);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 添加常用户
    public static void getMeContactAdd(RequestParams params,
                                       final CallBack callback) {

        final String url = getApi(Api_MyContactSave);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 更新常用户
    public static void getMeContactUpdate(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_MyContactSave);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 获取配置信息
    public static void getAppConfig(RequestParams params,
                                    final CallBack callback) {

        final String url = getApi(Api_AppConfigs);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject json = response.optJSONObject("data");
                AppConfigModel model = new AppConfigModel();

                model.verModel = UtilHelper.parseObject(
                        json.optJSONObject("verModel"), VersionItemModel.class);

                model.search_tags = UtilHelper.parseObject(
                        json.optJSONObject("search_tags"),
                        SearchTagListModel.class);

                JSONObject filter = json.optJSONObject("search_list_filter");
                model.search_list_filter = UtilHelper.parseList(
                        filter.optJSONArray("extends"), SearchFilterModel.class);

                model.app_version = UtilHelper.parseObject(
                        json.optJSONObject("app_version"), VersionModel.class);

                model.api_urls = parseIndexModel(json.optJSONObject("api_urls"));

                model.startup = UtilHelper.parseObject(
                        json.optJSONObject("startup"), SearchFilterModel.class);

                // UtilHelper.parseObject(
                // json.optJSONObject("api_urls"), BaseDataModel.class);

                rl.data = model;
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 个人资料
    public static void getMeInfoList(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(APi_MyInfo);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                rl.data = UtilHelper.parseObject(
                        response.optJSONObject("data"), UserDataModel.class);
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 修改个人资料
    public static void getMeInfoUpdate(RequestParams params,
                                       final CallBack callback) {

        final String url = getApi(Api_MyInfoUpdate);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 我的图说
    public static void getMyFindList(RequestParams params,
                                     final CallBack callback) {
        final String url = getApi(Api_MyFind);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("quotesList"),
                        FindModel.class);
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 删除我的图说
    public static void getMyFindDelete(RequestParams params,
                                       final CallBack callback) {

        final String url = getApi(Api_MyFindDelete);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 注册时候 ，点击下一步
    public static void getNextReg(RequestParams params,
                                  final CallBack callback) {

        final String url = getApi(Api_NegNext);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = data;
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 修改密码
    public static void getMePasswodUpdate(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_AltPwd);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 修改手机号
    public static void getMeTelUpdate(RequestParams params,
                                      final CallBack callback) {

        final String url = getApi(Api_AltMobile);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 获取验证码
    public static void getSendForgotCode(RequestParams params,
                                         final CallBack callback) {

        final String url = getApi(Api_SendForgotCode);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 检测验证码
    public static void getVerifyForgotCode(RequestParams params,
                                           final CallBack callback) {

        final String url = getApi(Api_VerifyForgotCode);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

    // 重置密码
    public static void ResetPassword(RequestParams params,
                                     final CallBack callback) {

        final String url = getApi(Api_ResetPassword);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                // TODO Auto-generated method stub

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                callback.onSuccess(rl, data);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

//    // 意见建议
//    public static void BugReport(RequestParams params, final CallBack callback) {
//
//        final String url = getApi(Api_BugReport);
//
//        post(callback, url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  String responseString, Throwable throwable) {
//                // TODO Auto-generated method stub
//                callback.onFailure(throwable);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  JSONObject response) {
//                // TODO Auto-generated method stub
//
//                ResultModel rl = new ResultModel();
//                rl.code = response.optInt("code");
//                rl.msg = response.optString("msg");
//                JSONObject data = response.optJSONObject("data");
//                callback.onSuccess(rl, data);
//            }
//
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                super.onFinish();
//            }
//        });
//    }

    // 发现评论列表
    public static void getFindCommentList(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_FindCommentList);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(
                        data.optJSONArray("commentList"),
                        FindCommentModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 我的订单
    public static void getMyOrderList(RequestParams params,
                                      final CallBack callback) {

        final String url = getApi(Api_MyOrderList);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("orderList"),
                        OrderModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 注册
    public static void RegisterUser(RequestParams params,
                                    final CallBack callback) {

        final String url = getApi(Api_Reg);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseObject(
                        response.optJSONObject("data"), UserModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 产品列表
    public static void getProductList(String url, RequestParams params,
                                      final CallBack callback) {

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(
                        data.optJSONArray("productList"), ProductModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

//    // 城市列表
//    public static void getCityList(RequestParams params, final CallBack callback) {
//        final String url = getApi(Api_City);
//        post(callback, url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  String responseString, Throwable throwable) {
//                callback.onFailure(throwable);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  JSONObject response) {
//
//                ResultModel rl = new ResultModel();
//                rl.code = response.optInt("code");
//                rl.msg = response.optString("msg");
//                JSONObject data = response.optJSONObject("data");
//                rl.data = UtilHelper.parseList(data.optJSONArray("citys"),
//                        CityModel.class);
//                callback.onSuccess(rl, data);
//            }
//        });
//    }

    // 消息列表
    public static void getMsgList(RequestParams params, final CallBack callback) {
        final String url = getApi(Api_MsgList);
        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = UtilHelper.parseList(data.optJSONArray("msgList"),
                        MsgBoxModel.class);
                callback.onSuccess(rl, data);
            }
        });
    }

    // 请求url
    public static void reuqestUrl(String url, RequestParams params,
                                  final CallBack callback) {

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                rl.data = data;
                callback.onSuccess(rl, response);
            }
        });
    }

    // 游侠列表
    public static void getFriendLs(String _url, RequestParams params,
                                   final CallBack callback) {

        final String url = getApi(_url);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject data = response.optJSONObject("data");
                if (data != null) {
                    rl.data = UtilHelper.parseList(
                            data.optJSONArray("userList"), FriendModel.class);
                }

                callback.onSuccess(rl, data);
            }
        });
    }




    public static void saveGps() {

        if (!UserModel.isLogin()) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("lat", MyConfig.lat + "");
        params.put("lng", MyConfig.lng + "");
//        params.put("lbs_city", MyConfig.MyLocation.name);
//        params.put("lbs_lbs_pro", MyConfig.MyLocation.province);
//        // params.put("mipush_regid", MyConfig.mipush_regid);

        ApiX1.getInstance().baseReuqestUrl(ApiX1.Api_SaveGps, params, new GsonCallBack<JSONObject>() {
            @Override
            public void onFailure(int statusCode, Throwable arg0) {

            }

            @Override
            public void onSuccess(GsonResultModel<JSONObject> resultModel, Object obj) {
                if (resultModel.isSucc()) {
                    // notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable arg0) {

            }
        });

    }

//    // 搜索产品列表
//    public static void searchProductList(String url, RequestParams params,
//                                         final CallBack callback) {
//
//        post(callback, url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  String responseString, Throwable throwable) {
//                callback.onFailure(throwable);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  JSONObject response) {
//
//                ResultModel rl = new ResultModel();
//                rl.code = response.optInt("code");
//                rl.msg = response.optString("msg");
//                JSONObject json = response.optJSONObject("data");
//
//                rl.data = UtilHelper.parseList(
//                        json.optJSONArray("productList"), ProductModel.class);
//
//                MonthProductModel model = new MonthProductModel();
//
//                // model.productList=UtilHelper.parseList(
//                // json.optJSONArray("productList"), ProductModel.class);
//
//                model.totalPage = json.optInt("totalPage");
//                model.pageSize = json.optInt("pageSize");
//
//                JSONArray array = json.optJSONArray("listFilter");
//
//                if (array.length() > 0) {
//                    model.locationModel = UtilHelper.parseList(array
//                                    .optJSONObject(0).optJSONArray("extends"),
//                            SearchFilterModel.class);
//
//                    model.alias = array.optJSONObject(0).optString("alias");
//                    model.name = array.optJSONObject(0).optString("name");
//
//                    model.orderModel = UtilHelper.parseObject(
//                            array.optJSONObject(1), SearchFilterModel.class);
//
//                    model.dayModel = UtilHelper.parseObject(
//                            array.optJSONObject(2), SearchFilterModel.class);
//
//                    model.typeModel = UtilHelper.parseObject(
//                            array.optJSONObject(3), SearchFilterModel.class);
//                }
//
//                callback.onSuccess(rl, model);
//            }
//        });
//    }

//    // 按月产品列表
//    public static void getMonthProductList(RequestParams params,
//                                           final CallBack callback) {
//
//        final String url = getApi(Api_MonthProductList);
//
//        post(callback, url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  String responseString, Throwable throwable) {
//                // TODO Auto-generated method stub
//                callback.onFailure(throwable);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  JSONObject response) {
//
//                ResultModel rl = new ResultModel();
//                rl.code = response.optInt("code");
//                rl.msg = response.optString("msg");
//                JSONObject json = response.optJSONObject("data");
//
//                MonthProductModel model = new MonthProductModel();
//
//                model.productList = UtilHelper.parseList(
//                        json.optJSONArray("productList"), ProductModel.class);
//
//                model.totalPage = json.optInt("totalPage");
//                model.pageSize = json.optInt("pageSize");
//
//                JSONArray array = json.optJSONArray("listFilter");
//
//                if (array.length() > 0) {
//                    model.locationModel = UtilHelper.parseList(array
//                                    .optJSONObject(0).optJSONArray("extends"),
//                            SearchFilterModel.class);
//
//                    model.alias = array.optJSONObject(0).optString("alias");
//                    model.name = array.optJSONObject(0).optString("name");
//
//                    model.orderModel = UtilHelper.parseObject(
//                            array.optJSONObject(1), SearchFilterModel.class);
//
//                    model.dayModel = UtilHelper.parseObject(
//                            array.optJSONObject(2), SearchFilterModel.class);
//
//                    model.typeModel = UtilHelper.parseObject(
//                            array.optJSONObject(3), SearchFilterModel.class);
//                }
//
//                rl.data = model;
//                callback.onSuccess(rl, null);
//            }
//
//            @Override
//            public void onFinish() {
//                // TODO Auto-generated method stub
//                super.onFinish();
//            }
//        });
//    }


    // 涮选产品列表
    public static void getMenuProductList(RequestParams params,
                                          final CallBack callback) {

        final String url = getApi(Api_Product_Menu_V2);

        post(callback, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // TODO Auto-generated method stub
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                ResultModel rl = new ResultModel();
                rl.code = response.optInt("code");
                rl.msg = response.optString("msg");
                JSONObject json = response.optJSONObject("data");

                MonthProductModel model = new MonthProductModel();

                model.productList = UtilHelper.parseList(
                        json.optJSONArray("productList"), ProductModel.class);

                model.totalPage = json.optInt("totalPage");
                model.pageSize = json.optInt("pageSize");

                rl.data = model;
                callback.onSuccess(rl, null);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
            }
        });
    }

}
