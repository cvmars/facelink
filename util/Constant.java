package com.youxiake.util;

/**
 * Created by Cvmars on 2016/8/31.
 */

public class Constant {

    /**
     * mdd_detail(目的地详情), discover_topic(遇见话题合集), travel_detail(游记详情), news_detail(攻略详情),album_detail(摄影详情), film_detail(视频详情), product_detail(线路详情), visa_detail(签证详情)
     * document_detail(主题游下级H5页面)
     */
    public static final String MDDDETAIL = "mdd_detail";
    public static final String DISCOVERTOPIC = "discover_topic";
    public static final String TRAVELDETAIL = "travel_detail";
    public static final String NEWSDETAIL = "news_detail";
    public static final String ALBUMDETAIL = "album_detail";
    public static final String FILMDETAIL = "film_detail";
    public static final String PRODUCTDETAIL = "product_detail";
    public static final String VISADETAIL = "visa_detail";
    public static final String DOCUMENTDETAIL = "document_detail";
    /**
     * 分享类型 search_travel_product(去首页搜索1条你喜欢的线路) login_daily_community(每日登录并访问社区页面)
     */
    public static final String SEARCHTRAVELPRODUCT = "search_travel_product";
    public static final String LOGINDAILYCOMMUNITY = "login_daily_community";
    public static final String SEARCHMEET = "SearchMeet";
    public static final String SEARCH_KEY= "SearchKey";


    public static int CURRENT_FIND_SEARCH = 0;

    /**
     * type int	4 摄影；3 视频； 2 攻略； 1 游记；  8 游侠； -1 综合推荐；6 签证 ；7 目的地；5 遇见
     */
    public static final int COMPREHENSIVE = -1;
    public static final int RANGER = 8;
    public static final int MEET = 5;
    public static final int PHOTOGRAPHY = 4;
    public static final int VIDEO = 3;
    public static final int STRATEGY = 2;
    public static final int TRAVELNOTES = 1;
    public static final int MALL = 100;
    public static final int VISA = 6;
    public static final int DEST = 7;

    public static int[] typeKeys = {Constant.COMPREHENSIVE, Constant.TRAVELNOTES, Constant.STRATEGY, Constant.MEET, Constant.VIDEO, Constant.PHOTOGRAPHY, Constant.RANGER};

    public static int TravelImageMaxSize = 20;
    public static int TravelVideoMaxSize = 80;
    public static final int Permission_READ_CONTACTS_Code = 400;
    public static final int Permission_WRITE_EXTERNAL_STORAGE_Code = 200;
    public static final int Permission_Camera_Code = 300;
    public static final int Permission_CALL_PHONE_Code = 100;
    public static final int Permission_LOCATION_Code = 500;

    public static final String EVENTBUS_KEY_PLACE_ID = "place_id";

    public static final String ACache_IndexInfo_Key = "aCache_indexInfo_key";


    public static boolean CACHE_IS_FISRT_INSTALL = false;


    public static final String INTENT_DETAIL_ID = "detail_id";


    public static final String INTENT_TOPIC_ID = "discu_id";

    public static int parentID = 0; //父类选中的某一些项
    public static int sonID = 0; //父类选中的某一些项

    public static int rankParentID = 0; //父类选中的某一些项
    public static int rankSonID = 0; //父类选中的某一些项

    public static final String INTENT_BOOLEN = "boolen_value";

    public static final String CACHE_Music_LIST = "cache_music";


    public static final String CACHE_FIND_VERSION = "dataVersion";


    public static final String CACHE_FIND_HOT_ARR = "findHotArr";


    public static final String INTENT_DATA1 = "data1";

    public static final String INTENT_DATA_FIND = "dataFind";


    public static final String INTENT_FIND_TIOIC_ID = "discu_id";

    public static final String INTENT_MAIN_INDEX = "index";
    public static final String INTENT_MAIN_MDD = "mdd";
    public static final String INTENT_MAIN_FIND = "find";
    public static final String INTENT_MAIN_COMMUNITY = "community";
    public static final String INTENT_MAIN_MESSAGE = "message";
    public static final String INTENT_MAIN_SETTING = "setting";

    public static final String INTENT_DATA2 = "data2";

    public static final String INTENT_DATA3 = "data3";

    public static final String INTENT_WEB_NAV = "webNav";

    public static final String INTENT_DATA4 = "data4";


    public static final String INTENT_DATA5 = "data5";


    public static final String INTENT_UID = "uid";

    public static final String INTENT_DATA6 = "data6";

    public static final String INTENT_DATA7 = "data7";

    public static final String GROINGIO_HOME = "home"; //growing.io统计首页


    public static final int TYPE_GORINGIO_ORDER = 1; //下单订
    public static final int TYPE_GORINGIO_PAYSEUCESS = 2; //支付成功
    public static final int TYPE_GORINGIO_CANCELORDER = 3; //取消订单页面属性
    public static final int TYPE_GORINGIO_PID = 4; //线路详情页面属性
    public static final int TYPE_GORINGIO_UID = 5; //用户页面属性


    public static final String GORWING_IO_GENERATE_ORDER = "GenerateOrder"; //生成订单
    public static final String GORWING_IO_PaySuccess = "PaySuccess"; //支付成功
    public static final String GORWING_IO_CancelOrder = "CancelOrder"; //取消订单

    public static final String GORWING_IO_PublishNews = "PublishNews"; //发布遇见
    public static final String GORWING_IO_PublishPhotos = "PublishPhotos"; //发布摄影作品
    public static final String GORWING_IO_UPDATE_Photos = "updatePhotos"; //更新摄影作品
    public static final String GORWING_IO_PublishTravels = "PublishTravels"; //发布游记
    public static final String GORWING_IO_UPDATE_Travels = "updateTravels"; //更新游记


    public static final String GORWING_IO_PhotoID = "PhotoID"; //目的地页面变量
    public static final String GORWING_IO_DESTION = "Destination"; //摄影详情页面变量
    public static final String GORWING_IO_VideoID = "VideoID"; //视频详情页面变量
    public static final String GORWING_IO_TravelsID = "TravelsID"; //视频详情页面变量
    public static final String GORWING_IO_StrategyID = "StrategyID"; //攻略详情页面变量
    public static final String GORWING_IO_IndexWaterID = "WaterfallClick"; //瀑布流点击变量


    public static final String INTENT_FROM = "from";
    public static final String INTENT_FROM_DESTDETAIL = "fromDestDetail";
    public static final String DESTLIST = "destList";
    /**
     * 页面来源 1 (APP首页) 2( 遇见首页) 3 (社区首页)
     */
    public static final String SEARCHFROMHOMEINDEX = "1";
    public static final String SEARCHFROMMEETHOME = "2";
    public static final String SEARCHFROMCOMMUNITYHOME = "3";

    //分享会回放记录字段
    public static final String LAST_IMSHARE_ID = "last";


    public static final String YXK_DAREN = "达人";
    public static final String YXK_YOUNVLANG = "游女郎";
    public static final String YXK_YOUFEIXIA = "游飞侠";


    //搜索默认字段 跳html
    public static final String SEARCH_MODEL_DEFATU = "search_defalut";

    public final static String PostAddComment = "addComment";
    //cache

    public static final String CACHE_DESTION_CITY = "destionCity";


    public static final String CACHE_INDEX_CITY = "city";


    public static final String CACHE_INDEX_IPCITY = "ipcity";


    public static final String INTENT_VALUE_PRIVIEW = "priview";

    public static final String Config_Video_Max_Size = "video_max_size";
    public static final String Config_Image_Max_tags = "image_max_tags";
    /**
     * BroadCastReceiver
     **/
    public static final String BCR_ADDWEIBO = "addweibo";
    public static final String BCR_ADDFind = "addfind";
    public static final String BCR_ADDFind_dismiss = "addfinddismiss";
    public static final String BCR_FindVideo = "findVideo";
    public static final String BCR_FindSlePIC = "findRequestPic";
    public static final String BCR_FindSlePICRestlt = "findRequestPicResult";
    public static final String BCR_FindSlePICList = "findRequestPicResultList";
    public static final String BCR_FindSleVIdeoRestlt = "findRequestVideoResult";


    //七牛token获取
    public static final String QINIU_TOKEN_FIND = "youxiakeapp";
    public static final String QINIU_TOKEN_CAMERA = "yxk-net";
    public static final String QINIU_TOKEN_AVATOR = "yxkavatar";
    public static final String QINIU_TOKEN_BBS = "yxk-bbs";
    public static final String QINIU_TOKEN_PRIVATE = "yxk-user-private";


    //统计类型
    public static final int TONGJI_KEFU = 1; //客服统计


    //客服统计类型
    public static final int TONGJI_PRODUCT = 1; //线路详情
    public static final int TONGJI_VAIS = 2; //签证详情
    public static final int TONGJI_PRODUCT_ORDER = 3; //线路订单客服
    public static final int TONGJI_VAIS_ORDER = 4; //签证订单客服


    /**
     * 友盟自定义事件的类型
     */
    public static final String HP_Outdoor_Relax_Top = "HP_Outdoor_Relax_Top"; //首页-户外与休闲上,0
    public static final String HP_Outdoor_Relax_Right = "HP_Outdoor_Relax_Right"; //  ,首页-户外与休闲右,0
    public static final String HP_Outdoor_Relax_Left = "HP_Outdoor_Relax_Left"; //  ,首页-户外与休闲左,0
    public static final String HP_Outdoor_Relax_Mid = "HP_Outdoor_Relax_Mid"; //   , 首页-户外与休闲中,0
    public static final String HP_ParentChild_learn_Top = "HP_ParentChild_learn_Top"; //  , 首页-亲子与研学上,0
    public static final String HP_ParentChild_learn_Left = "HP_ParentChild_learn_Left"; //  , 首页-亲子与研学左,0
    public static final String HP_ParentChild_learn_Mid = "HP_ParentChild_learn_Mid"; //  , 首页-亲子与研学中,0
    public static final String HP_ParentChild_learn_Right = "HP_ParentChild_learn_Right"; //    , 首页-亲子与研学右,0

    public static final String HP_Photography_Outdoor_Top = "HP_Photography_Outdoor_Top"; //    , 首页-摄影与外拍上,0
    public static final String HP_Photography_Outdoor_Left = "HP_Photography_Outdoor_Left"; //   , 首页-摄影与外拍左,0
    public static final String HP_Photography_Outdoor_Mid = "HP_Photography_Outdoor_Mid"; //        , 首页-摄影与外拍中,0
    public static final String HP_Photography_Outdoor_Right = "HP_Photography_Outdoor_Right"; //       , 首页-摄影与外拍右,0

    public static final String HP_Global_Recommend_TL = "HP_Global_Recommend_TL"; //         , 首页-全球推荐目的地上左,0
    public static final String HP_Global_Recommend_TR = "HP_Global_Recommend_TR"; //         , 首页-全球推荐目的地上右,0
    public static final String HP_Global_Recommend_Down = "HP_Global_Recommend_Down"; //         , 首页-全球推荐目的地下,0
    public static final String HP_Colorful_Trip_Left = "HP_Colorful_Trip_Left"; //         , 首页-多彩主题游左,0

    public static final String HP_Colorful_Trip_Top = "HP_Colorful_Trip_Top"; //            , 首页-多彩主题游上,0
    public static final String HP_Colorful_Trip_DownOne = "HP_Colorful_Trip_DownOne"; //      , 首页-多彩主题游下1,0
    public static final String HP_Colorful_Trip_DownTwo = "HP_Colorful_Trip_Left"; //        , 首页-多彩主题游下2,0


    public static final String HP_Weekly_Ranger = "HP_Weekly_Ranger"; //            , 首页-每周侠魁,0
    public static final String HP_Weekly_Ranger_More = "HP_Weekly_Ranger_More"; //        , 首页-每周侠魁-更多,0
    public static final String HP_Trip_Video = "HP_Trip_Video"; //          , 首页-旅行视频,0
    public static final String HP_Photography_Works = "HP_Photography_Works"; //            , 首页-摄影作品,0
    public static final String HP_LP_City_Exploration = "HP_LP_City_Exploration"; //             , 改名‘当地玩乐-城市探索’,0
    public static final String HP_LP_City_Exploration_More = "HP_LP_City_Exploration_More"; //         , 当地玩乐-城市探索更多,0
    public static final String LinesDetail_Customize = "LinesDetail_Customize"; //         定制


    public static final String Visa_Choose_Country = "Visa_Choose_Country"; //      签证导航-选择国家
    public static final String Visa_Special_Recommendation = "Visa_Special_Recommendation"; //  签证-特别推荐
    public static final String Visa_Popular_Country = "Visa_Popular_Country"; //   签证-热门国家
    public static final String Visa_Popular_Exemption = "Visa_Popular_Exemption"; //      签证-热门免签
    public static final String VisaDetail = "VisaDetail"; //     签证详情页面

    public static final String Homepage__Destination_Exploration_TOP = "Homepage__Destination_Exploration_TOP"; //     首页-  目的地玩乐顶部
    public static final String Homepage__Destination_Exploration_Left = "Homepage__Destination_Exploration_Left"; //     签证详情页面
    public static final String Homepage__Destination_Exploration_Mid = "Homepage__Destination_Exploration_Mid"; //     签证详情页面
    public static final String Homepage__Destination_Exploration_Righ = "Homepage__Destination_Exploration_Righ"; //     签证详情页面


    public static final String Destination_Play_Video = "Destination_Play_Video"; //     目的地- 目的地视频播放
    public static final String Destination_Local_Fun_Label = "Destination_Local_Fun_Label"; //     目的地- 当地玩乐分类标签
    public static final String Destination_Local_Fun_Label_LinesDetail = "Destination_Local_Fun_Label_LinesDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_Play_LinesDetail = "Destination_Play_LinesDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_Play_More = "Destination_Play_More"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_AroundTrip_LinesDetail = "Destination_AroundTrip_LinesDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_AroundTrip_More = "Destination_AroundTrip_More"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_Travel_Vacation_LinesDetail = "Destination_Travel_Vacation_LinesDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_DestinationVideo_VideoDetail = "Destination_DestinationVideo_VideoDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_DestinationVideo_More = "Destination_DestinationVideo_More"; //     目的地- 游玩攻略 攻略详情
    public static final String Destination_Play_Raiders_RaidersDetail = "Destination_Play_Raiders_RaidersDetail"; //     目的地- 当地玩乐单个分类标签下的线路详情
    public static final String Destination_Play_Raiders_More = "Destination_Play_Raiders_More"; //     目的地- 游玩攻略 更多


    //  首页 55
    public static final String UM_HOMEPAGE_SEARCH = "Homepage_search";
    public static final String UM_RICH_SCAN = "RichScan";
    public static final String UM_TYPE_FLASH = "HomePageFlash";

    public static final String UM_TYPE_NAV_TWO = "Homepage_Sec_Navigation";

    public static final String UM_TYPE_HOME_AD_ONE = "Homepage_Ad_One";
    public static final String UM_TYPE_HOME_AD_THREE = "Homepage_Ad_Three";

    public static final String UM_TYPE_Homepage_Ad_Two_L = "Homepage_Ad_Two_L";
    public static final String UM_TYPE_Homepage_Ad_Two_R = "Homepage_Ad_Two_R";

    public static final String UM_TYPE_Civil_Destination_L = "Civil_Destination_L";
    public static final String UM_TYPE_Civil_Destination_R = "Civil_Destination_R";


    public static final String UM_TYPE_Exit_Destination_L = "Exit_Destination_L";
    public static final String UM_TYPE_Exit_Destination_R = "Exit_Destination_R";

    public static final String UM_TYPE_Homepage_Ad_Four_L = "Homepage_Ad_Four_L";
    public static final String UM_TYPE_Homepage_Ad_Four_R = "Homepage_Ad_Four_R";

    public static final String UM_TYPE_HEADLINE = "Homepage_Headline_Scroll";
    public static final String UM_TYPE_WONDERFULFIND = "WonderfulFind";
    public static final String UM_LAUNCH_AD = "Launch_AD_Page";
    public static final String UM_HOMEPAGE_POPWINDOW = "PopWindow";
    /**
     * 首页新人有礼
     */
    public static final String Homepage_Welfare_Entrance = "Homepage_Welfare_Entrance";
    /**
     * 首页礼包领取
     */
    public static final String Homepage_Welfare_Receive = "Homepage_Welfare_Receive";

    //  首页频道产品
    public static final String UM_TYPE_HOMEPAGE_TOP_BLOCKS = "Homepage_Top_Blocks";
    public static final String UM_TYPE_HOMEPAGE_CHANNEL_INSIDE_SCROLL = "Homepage_Channels_Inside_Scroll";
    public static final String UM_HOMEPAGE_TIMELIMIT = "Homepage_TimeLimit_Preferential";


    //  首页底部频道产品
    public static final String UM_TYPE_DOWN_CN = "Down_CivilTrip";
    public static final String UM_TYPE_DOWN_ABROAD = "Down_ExitTrip";
    public static final String UM_TYPE_DOWN_AROUND = "Down_AroundTrip";
    public static final String UM_TYPE_DOWN_TONGCHENG = "Down_SameCityTrip";//xiaohai
    public static String[] buttonChannel = {UM_TYPE_DOWN_CN, UM_TYPE_DOWN_ABROAD, UM_TYPE_DOWN_AROUND, UM_TYPE_DOWN_TONGCHENG};

    //  首页周末去哪儿、当季主题、国内精选、国外精选
    public static final String UM_HOMEPAGE_WEEKENDTOGO = "Homepage_WeekendToGo";
    public static final String UM_HOMEPAGE_WEEKENDTOGO_MORE = "Homepage_WeekendToGo_More";
    public static final String UM_HOMEPAGE_TOPICTRAVEL = "Homepage_TopicTravel";
    public static final String UM_HOMEPAGE_TOPICTRAVEL_MORE = "Homepage_TopicTravel_More";
    public static final String UM_TYPE_CN_FEATURE = "Civil_Choiceness_Destination_Detail";
    public static final String UM_TYPE_CN_FEATURE_MORE = "Civil_Choiceness_Destination_More";
    public static final String UM_TYPE_ABROAD_FEATURE = "Exit_Choiceness_Destination_Detail";
    public static final String UM_TYPE_ABROAD_FEATURE_MORE = "Exit_Choiceness_Destination_More";


    //首页户外、亲子、摄影、全球目的地、多彩主题、侠魁、推荐视频摄影、当地玩乐
    public static final String UM_HP_OUTDOOR_RELAX_TOP = "HP_Outdoor_Relax_Top";
    public static final String UM_HP_OUTDOOR_RELAX_RIGHT = "HP_Outdoor_Relax_Right";
    public static final String UM_HP_OUTDOOR_RELAX_LEFT = "HP_Outdoor_Relax_Left";
    public static final String UM_HP_OUTDOOR_RELAX_MID = "HP_Outdoor_Relax_Mid";

    //亲子
    public static final String UM_HP_PARENTCHILD_LAERN_TOP = "HP_ParentChild_learn_Top";
    public static final String UM_HP_PARENTCHILD_LAERN_RIGHT = "HP_ParentChild_learn_Right";
    public static final String UM_HP_PARENTCHILD_LAERN_LEFT = "HP_ParentChild_learn_Left";
    public static final String UM_HP_PARENTCHILD_LAERN_MID = "HP_ParentChild_learn_Mid";

    //摄影外拍
    public static final String UM_HP_PHOTO_TOP = "HP_Photography_Outdoor_Top";
    public static final String UM_HP_PHOTO_RIGHT = "HP_Photography_Outdoor_Right";
    public static final String UM_HP_PHOTO_LEFT = "HP_Photography_Outdoor_Left";
    public static final String UM_HP_PHOTO_MID = "HP_Photography_Outdoor_Mid";

    //全球目的地
    public static final String UM_HP_GLOBAL_TL = "HP_Global_Recommend_TL";
    public static final String UM_HP_GLOBAL_TR = "HP_Global_Recommend_TR";
    public static final String UM_HP_GLOBAL_DOWN = "HP_Global_Recommend_Down";

    //多彩主题
    public static final String UM_HP_COLORFUL_TRIP_LEFT = "HP_Colorful_Trip_Left";
    public static final String UM_HP_COLORFUL_TRIP_TOP = "HP_Colorful_Trip_Top";
    public static final String UM_HP_COLORFUL_TRIP_DOWNONE = "HP_Colorful_Trip_DownOne";
    public static final String UM_HP_COLORFUL_TRIP_DOWNTWO = "HP_Colorful_Trip_DownTwo";

    //每周侠魁
    public static final String UM_HP_WEEKLY_RANGER = "HP_Weekly_Ranger";
    public static final String UM_HP_WEEKLY_RANGER_MORE = "HP_Weekly_Ranger_More";

    //旅行视频和摄影
    public static final String UM_HP_TRIP_VIDEO = "HP_Trip_Video";
    public static final String UM_HP_PHOTOGRAPHY_WORKS = "HP_Photography_Works";

    //当地玩乐
    public static final String UM_HP_CITY_EXPLOR = "HP_LP_City_Exploration";
    public static final String UM_HP_CITY_EXPLOR_MORE = "HP_LP_City_Exploration_More";

    //  线路详情
    public static final String UM_TYPE_LINEDETAIL = "LinesDetail";
    //  融云聊天
    public static final String UM_TYPE_CHAT_RONG = "ChatAction";

    //  遇见
    public static final String UM_TYPE_FINDACTIVITY = "FindActivity";
    public static final String UM_TYPE_FINDBIGIMAGE = "FindImageMagnify";
    public static final String UM_TYPE_FINDMODULESCAN = "FindModuleScan";
    public static final String UM_TYPE_FINDDETAIL = "FindDetailScan";
    public static final String UM_TYPE_RELEASEFIND = "ReleaseFindSuccess";
    public static final String UM_TYPE_TOPIC_HOT = "Find_Topic_Blocks";
    public static final String UM_TYPE_TOPIC_HOT_MORE = "Find_Topic_Blocks_more";
    public static final String UM_TYPE_FIND_BANNERS = "FindBanners";
    //  摄影
    public static final String UM_TYPE_CAMERASORT = "PhotographyCategroy";
    public static final String UM_TYPE_CAMERADETAIL = "PhotographyWorkDetail";
    public static final String UM_TYPE_CAMERAfEATURE = "PhotographyRecommendWorks";
    public static final String UM_TYPE_CAMERAPRODUCTION = "PhotographyCompetitionDetail";

    //  订单
    public static final String UM_TYPE_PaySucc = "OrderPaySuccess";
    public static final String UM_TYPE_REGISTER = "RegisterSuccess";

    //  社区
    public static final String UM_TYPE_COMMUNITY_SEARCH = "Community_Search";
    public static final String UM_TYPE_COMMUNITY_CAMERA = "Community_Channel_Photography";
    public static final String UM_TYPE_COMMUNITY_VIDEO = "Community_Channel_Video";
    public static final String UM_TYPE_COMMUNITY_FORUM = "Community_Channel_Forum";
    public static final String UM_TYPE_COMMUNITY_HEADLINE = "Community_Channel_Headline";
    public static final String UM_TYPE_POST_WEEKRECOMMEND_LIST = "Post_WeeklyRecommend_List";
    public static final String UM_TYPE_POST_WEEKRECOMMEND_DETAIL = "Post_WeeklyRecommend_Detail";

    public static final String UM_TYPE_COMMUNITY_RECOMMEND_PHOTOS = "Community_Recommend_Photos";
    public static final String UM_TYPE_COMMUNITY_RECOMMEND_VIDEOS = "Community_Recommend_Videos";
    public static final String UM_TYPE_COMMUNITY_RECOMMEND_HEADLINES = "Community_Recommend_Headlines";
    public static final String UM_TYPE_COMMUNITY_RECOMMEND_POSTS = "Community_Recommend_Posts";
    public static final String UM_TYPE_COMMUNITY_POST_DETAIL = "Community_Post_Detail";
    public static final String UM_TYPE_COMMUNITY_TOP_BANNERS = "Community_Top_Banners";


    //sp
    public static final String SP_INDEX_MODEL = "indexModel";

    public static final String SP_IMG_SIZE = "ImgSize";
    public static final String SP_FIRST_NEWPERSONL = "firstnewgift";
    public static final String SP_FIRST_NEWPERSONL_PRODUCT = "firstnewgiftproduct";
    public static final String SP_FIRST_NEWPERSONL_INDEX = "firstnewgiftindex";
    public static final String SP_FIRST_NEWPERSONL_TIME = "firstnewgifttime";
    public static final String SP_FIRST_NEWPERSONL_TIME_PRODUCT = "firstnewgifttimeproduct";
    public static final String SP_FIRST_NEWPERSONL_TIME_INDEX = "firstnewgifttimeindex";
    public static final String SP_FIRST_NEWPERSONL_TIME_INDEX_FLOAT = "firstnewgifttimeindexfloat";
    public static final String SP_FIRST_SHOW_AD = "firstshowad";

    public static final String SP_LOCATION_ADDTAG = "FindLocation";

    public static final String SP_FIRST_ADDTAG = "FirstAddTag";


    public static final String SP_DESTION = "destion";


    public static final String SP_FIRST_PERSSION = "FirstPersion";


    public static final String SP_LOGIN = "spLogin";


    public static final String SP_FIRST_TIP1 = "FirstTIP1";

    public static final String SP_FIRST_TIP2 = "FirstTIP2";


    public static final String SP_FIRST_FIND_PUBLISH = "firstPublishTip";

    //    public static final String SP_FIRST_TIP3 = "FirstTIP3";
    public static final String SP_FIRST_TIP3 = "FirstTIP3";


    public static final String SP_TRAVEL_TIP1 = "TravelTip1";

    public static final String SP_TRAVEL_TIP2 = "TravelTip2";

    public static final String SP_TRAVEL_TIP3 = "TravelTip3";

    public static final String SP_TRAVEL_TIP4 = "TravelTip4";


    public static final String SP_FIRST_VISA_COLLECT = "VisaCollect";


    public static final String SP_QIYU_SOURCE = "qiyuServer";


    public static final String SP_FIRST_FIND_DRAG = "FirstFindDrag";


    public static final String SP_LOCATION_ADDTAG_TOP = "FindLocation_top";

    public static final String SP_LOCATION_ADDTAG_BOTTOM = "FindLocation_bottom";

    public static final String SP_FIRST_LOAD = "first_load";

    public static final String SP_FIRST_INSTALL = "first_install";


    public static final String SP_FIRST_WIFI_TIP = "first_wifi_tip";


    public static final String SP_FIRST_TIP = "first_find_tip";


    public static final String SP_FIRST_PUBLISH_TIP = "first_findp_tip";

    public static final String SP_FIRST_TOPIC_TIP = "first_findtopic_tip";


    public static final String SP_APP_CONFIG = "app_Config";

    public static final String SP_DATE_OPEN = "date";
    public static final String SP_DATE_OPEN_FIST = "date_first";


    public static final String SP_FIRST_COMMUNITY = "first_community";

    public static final String SP_FIRST_3DTIP = "first_tip_3d";


    public static final String MODEL_PRODUCT = "product_model";


    public static final String SP_FIRST_NewGift = "first_new_gift";

    //File缓存
    public static final String FILE_INDEX = "index";
    public static final String FILE_INDEX_REGION = "indexRegion";

    //首页周边游数据缓存
    public static final String FILE_NEARBY = "nearby";


    public static final String Direct_RIGHT = "right";
    public static final String Direct_LEFT = "left";

    public static final String TYPE_INTENT_KEY_ImgInfo = "ImgInfo";
    public static final String TYPE_INTENT_KEY_POSITION = "position";

    //intent
    public static final String TYPE_FIND_ACTIVITY = "activity";
    //intent
    public static final String TYPE_FIND_FOLLWE = "follow";
    public static final String TYPE_FIND_HOT = "hot";
    public static final String TYPE_FIND_NEARBY = "nearby";
    public static final String TYPE_FIND_Video = "video";
    public static final String TYPE_FIND_MEET = "discuss";
    public static final String MEETDRAFT = "MEETDRAFT";
    public static final String TYPE = "type";

    public static final int TYPE_MAP_HOTEL = 1;
    public static final String SUBTYPEPOSITION = "subposition";
    public static final String SUBTYPE = "subType";
    public static final String ISSEARCHSTRATEGY = "isSearchStrategy";
    public static final String KEYWORD = "keyWord";
    public static final String TRAVELNUM = "travelNum";
    public static final String MEETNUM = "meetNum";

    /**
     * 遇见类型 video
     */
    public static final String TYPE_FIND_ACTIVITY＿PHOTO = "photo";
    public static final String TYPE_FIND_ACTIVITY＿TOPIC = "topic";
    public static final String TYPE_FIND_ACTIVITY＿VIDEO = "video";
    public static final String TYPE_FIND_ACTIVITY＿PRODUCT = "product";
    public static final String TYPE_FIND_ACTIVITY＿RECOMMEND = "talent";
    public static final String TYPE_FIND_ACTIVITY＿EMPEY = "empey";

    public static final String TAG_KEY_QuoteId = "quote_id";


    public static final String TAG_KEY_FIND = "quote_id";

    public static final String TAG_KEY_PERSION = "uid";

    public static final String TAG_KEY_PID = "pid";


    public static final String TAG_KEY_VideoUrl = "videoUrl";
    public static final String TAG_KEY_VideoImgUrl = "videoImgUrl";

    public static final String TAG_FORM = "TAG";

    public static final String TAG_title = "title";

    public static final String TAG_Mode = "mode";

    public static final String TAG_FORM_IMG = "imageUrl";

    public static final String TAG_FORM_Conversation = "Conversation";

    public static final String TAG_FORM_Discu = "discussion";

    public static final String TAG_FORM_ProductOutDoor = "ProductOutDoor";

    public static final String TAG_FORM_SelectPic = "SelectPic";

    public static final String TAG_AddWeibo = "addweibo";

    public static final String TAG_FORM_TAGINFO = "tagINFO";

    public static final String TAG_ISPHONE_RESET = "ISPHONE_RESET_PASSWORD";

    public static final String TAG_TITLE = "title";

    public static final String TAG_DATA = "data";

    public static final String TAG_PHOTO_PICK = "PHOTO_PICK";

    public static final String TAG_Url = "url";

    public static final String TAG_Id = "tag_id";
    public static final String COMMENTID = "COMMENTID";
    public static final String CURRENTPAGE = "currentPage";
    public static final String PHOTOLIST = "mPhotosList";
    public static final String TOTALPAGE = "totalPage";
    public static final String SUMARIZES = "sumarizes";
    public static final String MDD_NAME = "mddName";
    public static final String PUBLISHMEET = "PUBLISHMEET";
    public static final String LNG_BAIDU = "lngBaidu";
    public static final String LAT_BAIDU = "latBaidu";
    public static final String LNG_GOOGLE = "lngGoogle";
    public static final String LAT_GOOGLE = "latGoogle";
    public static final String MDD_PIC = "mddPic";

    public static final String INTENT_ZT_PROTOCOL = "zt_protocol";

    public static final String LOGIN_PROTOCOL = "login_protocol";

    public static final String TAG_CONTENT = "content";

    public static final String TAG_SIZE = "size";

    public static final String TAG_KEY = "key";
    public static final String MEET_RECOMMEND_STATUS = "meetRecoStatus";
    /**
     * 是否管理员
     */
    public static final String IS_MANAGER = "isManager";
    public static final String MEET_LEVEL = "meetLevel";
    public static final String RECOMMEND_DEST = "RECOMMEND_DEST";

    public static final String TAG_VALUE = "value";

    public static final String TAG_AUTOSHOW_WEBTITLE = "Auto"; //传递值就不设置Intent传递的title,获取网页title

    //intent 目标来自 LoginActivity
    public static final String TAG_FORM_LoginActivity = "LoginActivity";

    public static final String TAG_Close_LoginActivity = "Close_LoginActivity";


    public static final String TAG_FORM_MeinfoActivity = "MeInfoActivity";
    public static final String TAG_FORM_loginOut = "loginOut";

    public static final String TAG_PlateName_QQ = "QQ";
    public static final String TAG_PlateName_Wechat = "Wechat";
    public static final String TAG_PlateName_SinaWeibo = "SinaWeibo";

    public final static String LOGIN_REQUEST = "LOGIN_REQUEST";

    public final static String USER_SEX_BOY = "1";
    public final static String USER_SEX_GIRL = "2";
    public final static String USER_SEX_NULL = "0";


    public final static String TYPE_FOLLOW = "FOLLOW";

    /**
     * 互动消息类型
     */

    public static final String TYPE_THREAD_LIKE = "like_thread";//
    public static final String TYPE_THREAD_COMMENT = "reply_thread";//
    public static final String TYPE_THREAD_REPLY = "reply_thread_comment";//
    public static final String TYPE_THREAD_COLLECTION = "thread_collection";//

    public static final String TYPE_CAMERA_LIKE = "like_photo";//
    public static final String TYPE_CAMERA_COMMENT = "reply_photo";//
    public static final String TYPE_CAMERA_REPLY = "reply_photo_comment";//
    public static final String TYPE_CAMERA_COLLECTION = "photo_collection";//

    public static final String TYPE_LINE_DISCUSS = "line_discuss";//
    public static final String TYPE_NEWS_REPLY = "news_reply";//
    public static final String TYPE_VIDEO_REPLY = "reply_video_comment";//
    public static final String TYPE_NEW_FOLLOWER = "new_follower";//

}
