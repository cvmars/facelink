package com.youxiake.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hjq.toast.ToastUtils;
import com.youxiake.R;
import com.youxiake.app.MyApplication;
import com.youxiake.app.MyConfig;
import com.youxiake.model.BaseConfigModel;
import com.youxiake.model.Filesys;
import com.youxiake.model.GUserInfo;
import com.youxiake.model.UserModel;
import com.youxiake.ui.base.BaseActivity;
import com.youxiake.ui.base.MyWebViewActivity;
import com.youxiake.ui.camera.ui.CameraDetailActivity;
import com.youxiake.ui.community.ui.CommunityForumDetailActivity;
import com.youxiake.ui.community.ui.CommunityStrategyDetailActivity;
import com.youxiake.ui.community.ui.CommunityVideoPlayActivity;
import com.youxiake.ui.product.ui.ProductDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class UtilHelper {

    private static final double EARTH_RADIUS = 6378137.0;

    private static final String patStr = ":([a-zA-Z0-9_]+?):";

    private static Pattern pattern = Pattern.compile(patStr);

    private static final String patEmogiStr = "\\[em_\\d{1,2}\\]";

    private static Pattern gifPattern = Pattern.compile(patEmogiStr);

    public static Pattern patternNet = Pattern
            .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/%])+$");
    public static String imgPath;

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = null;
        try {
            storePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/MyImg";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);

            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            imgPath = file.getAbsolutePath();
//            if (file.exists()) {
//                file.delete();
//            }

            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String onFammter(int time) {

        int timeL = time / 1000;

        int i = timeL / 60;

        int j = timeL % 60;

        return i + ":" + fatter(j);

    }


    public static String fatter(int time) {

        if (time > 9) {
            return time + "";
        } else {
            return "0" + time;
        }
    }


    /**
     * 计算指定画笔下指定字符串需要的宽度
     */
    public static int getTheTextNeedWidth(Paint thePaint, String text) {
        float[] widths = new float[text.length()];
        thePaint.getTextWidths(text, widths);
        int length = widths.length, nowLength = 0;
        for (int i = 0; i < length; i++) {
            nowLength += widths[i];
        }
        return nowLength;
    }

    public static String download(String urlStr) {

        MyLog.debug("urlStr :" + urlStr);
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(urlStr);
            //创建http连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            //使用IO流读取数据
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {

                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("TAG", "下载txt文件");
        MyLog.debug(sb.toString());

        return sb.toString();
    }


//    public static Bitmap base64ToBitmap(String base64Str) {
//
//            if (TextUtils.isEmpty(base64Str)) {
//                return null;
//            }
//
////            base64Str  = "data:image/jpeg;base64," + base64Str ;
////        String result = base64Str.split(",")[1];
//        byte[] bytes = new byte[0];
//        try {
//            bytes = Base64.decode(new String(base64Str.getBytes("UTF-8")), Base64.DEFAULT);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////            byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
//            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//    }


    public static Bitmap base64ToBitmap(String base64Str) {

        if (TextUtils.isEmpty(base64Str)) {
            return null;
        }
        try {
            byte[] data = base64Str.getBytes("UTF-8");
            byte[] bytes = Base64.decode(data, Base64.DEFAULT);

            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void decoderBase64FileNext(String base64Code) throws Exception {
        //byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);

        String imgPath = MyConfig.getYxkDir("photo") + "photo_"
                + new Date().getTime() + ".jpg";
//        byte[] buffer =Base64.decode(base64Code, Base64.DEFAULT);

//        String s = base64Code.split(",")[1];
        String s1 = new String(base64Code.getBytes("UTF-8"));
        byte[] bitmapArray = Base64.decode(s1, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(imgPath);
        out.write(bitmapArray);
        out.close();
    }


    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code) throws Exception {


        Bitmap bitmap = base64ToBitmap(base64Code);

        String imgPath = MyConfig.getYxkDir("photo") + "photo_"
                + new Date().getTime() + ".jpg";

        savePic(imgPath, bitmap);

    }


    public static int downloadFile(String urlStr, String path, String fileName) {
        InputStream inputStream = null;
        try {
            Filesys filesys = new Filesys();
            if (filesys.isFileExist(path + fileName)) {
                return 1;
            } else {
                inputStream = getInputStreamFromUrlStr(urlStr);
                File resultFile = filesys.write2SDFromInput(path, fileName, inputStream);
                if (resultFile == null) {

                    return -1;
                }
                onSaveCamera(MyApplication.Instance, resultFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    public static String parseColor(String blurColor) {
        String[] split = blurColor.split("#");
        if (split.length == 2 && !TextUtils.isEmpty(split[1]) && split[1].length() == 3) {
            blurColor = "#" + split[1].charAt(0) + split[1].charAt(0) + split[1].charAt(1) + split[1].charAt(1) + split[1].charAt(2) + split[1].charAt(2);
        }
        return blurColor;
    }

    public static String getEncourTime(String year, String data) {

        Calendar c = strToCalendar(year + "-" + data);
        int month1 = c.get(Calendar.MONTH) + 1;
        //获取当前天数
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = UtilHelper.strToCalendar(year + "-" + data);
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        int month = calendar.get(Calendar.MONTH) + 1;
        //获取当前天数
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return getFammter(month) + "." + getFammter(day) + "-" + getFammter(month1) + "." + getFammter(day1);
    }

    public static String getFammter(int month) {

        if (month > 9) {

            return "" + month;
        } else {

            return "0" + month;
        }
    }


    public void inputstreamtofile(InputStream ins, String path, String fileName) {
        File file = new File(path + fileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static InputStream getInputStreamFromUrlStr(String urlStr) throws IOException {

        InputStream input = null;
//创建URL对象
        URL url = new URL(urlStr);

//创建http连接
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

//获取输入流
        input = urlConnection.getInputStream();
        return input;
    }


    //保存视频到本地
    public static void onSaveCamera(Context mContext, File file) {

        ContentResolver localContentResolver = mContext.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(mContext, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
    }


    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }


    /**
     * 判断身份证格式
     *
     * @param idNum
     * @return
     */
    public static boolean isIdNum(String idNum) {

        // 中国公民身份证格式：长度为15或18位，最后一位可以为字母
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        // 格式验证
        if (!idNumPattern.matcher(idNum).matches())
            return false;

        // 合法性验证

        int year = 0;
        int month = 0;
        int day = 0;

        if (idNum.length() == 15) {

            // 一代身份证

            System.out.println("一代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf("19" + birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));

            }

        } else if (idNum.length() == 18) {

            // 二代身份证

            System.out.println("二代身份证：" + idNum);

            // 提取身份证上的前6位以及出生年月日
            Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

            Matcher birthDateMather = birthDatePattern.matcher(idNum);

            if (birthDateMather.find()) {

                year = Integer.valueOf(birthDateMather.group(1));
                month = Integer.valueOf(birthDateMather.group(2));
                day = Integer.valueOf(birthDateMather.group(3));
            }

        }

        // 年份判断，100年前至今

        Calendar cal = Calendar.getInstance();

        // 当前年份
        int currentYear = cal.get(Calendar.YEAR);

        if (year <= currentYear - 100 || year > currentYear)
            return false;

        // 月份判断
        if (month < 1 || month > 12)
            return false;

        // 日期判断

        // 计算月份天数

        int dayCount = 31;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayCount = 31;
                break;
            case 2:
                // 2月份判断是否为闰年
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    dayCount = 29;
                    break;
                } else {
                    dayCount = 28;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                dayCount = 30;
                break;
        }

//        System.out.println(String.format("生日：%d年%d月%d日", year, month, day));
//
//        System.out.println(month + "月份有：" + dayCount + "天");

        if (day < 1 || day > dayCount)
            return false;

        return true;
    }

    /**
     * 检查是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {

        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 清理文件
     *
     * @param file
     * @throws IOException
     */
    public static void cleanDirectory(File file) throws IOException {

        if (!file.exists()) {
            return;
        }
        File[] contentFiles = file.listFiles();
        if (contentFiles != null) {
            for (File contentFile : contentFiles) {
                delete(contentFile);
            }
        }
    }


    private static void delete(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
        } else {
            cleanDirectory(file);
            deleteOrThrow(file);
        }
    }

    private static void deleteOrThrow(File file) throws IOException {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
            }
        }
    }

    /**
     * 检查是否网址
     *
     * @param content
     * @return
     */
    public static boolean isHttpAddress(String content) {
        return patternNet.matcher(content).matches();
    }

    /**
     * 验证是否邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        //  java验证邮箱格式：
        Matcher matcher = MyConfig.emialPattern.matcher(email);
        return matcher.matches();
    }


    /**
     * 保存用户信息
     */
    public static void saveUserModel(Context context, GUserInfo model) {

        UserModel userModel = UserModel.getAccount(context);
        userModel.username = model.getNick();
        userModel.email = model.getEmail();
        userModel.phone = model.getMobile();
        userModel.upic = model.getAvatar();
        userModel.uid = model.getUid();
        userModel.intro = model.getIntro();
        userModel.realname = model.getRealname();
        UserModel.saveAccount(context, userModel);

    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {


        String num = "[1]\\d{10}";//"[1]"代表第1位为数字1，  ，"\\d{10}"代表后面是可以是0～9的数字，有10位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext
     */
    public static void openKeybords(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            mEditText.requestFocus();
            imm.showSoftInput(mEditText, 0);
        }
    }

    /**
     * 获得一个唯一值
     *
     * @return
     */
    public static String getUrl() {

        StringBuilder builder = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        builder.append(UtilHelper.getSixRandom()).append(DeviceHelper.SerialNumber());
        return UtilHelper.getMD5Str(builder.toString());
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     *
     * @param mContext 上下文
     */
    public static boolean getKeybordState(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        return imm.isActive(mEditText);


    }

    public static int getScrollY(AbsListView listView) {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }


    public static int getGridScrollY(GridView gridView) {
        int pos, itemY = 0;
        View view;
        pos = gridView.getFirstVisiblePosition();
        view = gridView.getChildAt(0);
        if (view != null)
            itemY = view.getTop();
        return YFromPos(pos) - itemY;
    }

    private static int YFromPos(int pos) {
        int row = pos / 2;

        if (pos - row * 2 > 0)
            ++row;
        return row * 2;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        if (listAdapter != null) {
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                if (listItem != null) {
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    public static void setListViewHeightBasedOnChildren(GridView gridView, int numColumns) {
        // 获取listview的adapter
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目

            try {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0); // 计算子项View 的宽高

                if ((i + 1) % numColumns == 0) {
                    totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
                }

                if ((i + 1) == len && (i + 1) % numColumns != 0) {
                    totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
                }
            } catch (Exception e) {


            }

        }

        totalHeight += 40;

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }


    public static void setListViewHeightBasedOnChildren(GridView gridView, int numColumns, int spacelength) {
        // 获取listview的adapter
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目

            try {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0); // 计算子项View 的宽高

                if ((i + 1) % numColumns == 0) {
                    totalHeight += listItem.getMeasuredHeight() + spacelength; // 统计所有子项的总高度
                }

                if ((i + 1) == len && (i + 1) % numColumns != 0) {
                    totalHeight += listItem.getMeasuredHeight() + spacelength; // 统计所有子项的总高度
                }
            } catch (Exception e) {


            }

        }

        totalHeight += 40;

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    /**
     * @param str
     * @return 判断字符串是否是数字
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 获取屏幕的高度
    public static int getWindowHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenHeight = dm.heightPixels;
        return mScreenHeight;
    }

    //获取屏幕的宽度px
    public static int getWindowWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenHeight = dm.widthPixels;
        return mScreenHeight;
    }

    /**
     * 获取屏幕的宽度 返回的单位是dp
     *
     * @param context
     * @return
     */
    public static float getWindowWidth(Context context) {

        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels / displayMetrics.density;

    }

    /**
     * 获取屏幕的宽度 返回的单位是dp
     *
     * @param activity
     * @return
     */
    public static float getWindowWidthDp(Activity activity) {

        DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    /**
     * 获取屏幕的高度 返回的单位是dp
     *
     * @param activity
     * @return
     */
    public static float getWindowHeightDp(Activity activity) {

        DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    /**
     * 获取屏幕的高度 返回的单位是dp
     *
     * @param context
     * @return
     */
    public static float getWindowHeight(Context context) {

        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.heightPixels / displayMetrics.density;

    }

    /**
     * 获取屏幕的宽度 返回的单位是px
     *
     * @param context
     * @return
     */
    public static float getWindowWidthPx(Context context) {

        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        float result = displayMetrics.widthPixels / displayMetrics.density;
        return DensityUtils.dp2px(context, result);
    }


    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }


    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public static String getRandom() {
        int random = (int) (Math.random() * 9000 + 1000);
        return String.valueOf(random);
    }

    public static String getSixRandom() {
        int random = (int) (Math.random() * 900000 + 100000);
        return String.valueOf(random);
    }

    public static void getTotalHeightofListView(ListView listView) {
        if (listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /*
     * * MD5加密 03.
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        // 16位加密，从第9位到25位
        // return md5StrBuff.substring(8, 24).toString().toLowerCase();

        return md5StrBuff.toString().toLowerCase();
    }


    /**
     * SHA1加密
     *
     * @param decript
     * @return
     */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encryptDes(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
        String strs = Base64.encodeToString(bt, Base64.DEFAULT);


        //String strs = new BASE64Encoder().encode(bt);
        return strs;
    }


    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encryptDesQu(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
        String strs = Base64.encodeToString(bt, Base64.DEFAULT);
        String encode = URLEncoder.encode(strs);
        //String strs = new BASE64Encoder().encode(bt);
        return encode;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     */
    public static String decryptDes(String data, String key) {
        if (data == null)
            return null;
        byte[] buf = Base64.decode(data, Base64.DEFAULT);
        byte[] bt = new byte[0];
        try {
            bt = decryptDes(buf, key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bt);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decryptDes(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    public static <T> T parseObject(JSONObject json, Class<T> clazz) {

        if (json == null) {
            return null;
        }
        try {
            T obj = clazz.newInstance();

            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                String name = field.getName();
                String type = field.getType().getSimpleName();
//				MyLog.debug(name + ":" + type + ",int:"
//						+ int.class.getSimpleName() + ",String:"
//						+ Boolean.class.getSimpleName());
                String jsonName = name;
                if (name.equals("extend_s")) {
                    jsonName = "extends";
                }
                if (type.equals("List") && !json.isNull(jsonName)) {
                    Type fc = field.getGenericType();
                    if (fc != null && fc instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) fc;
                        Class genericClazz = (Class) pt
                                .getActualTypeArguments()[0];
                        List<Class> mlist = parseList(
                                json.getJSONArray(jsonName), genericClazz);
                        field.set(obj, mlist);
                    }
                } else {
                    if (json.has(name)) {
                        if (type.equals(String.class.getSimpleName())) {
                            field.set(obj, json.getString(name));
                        } else if (type.equals(int.class.getSimpleName())) {
                            field.set(obj, json.getInt(name));
                        } else if (type.equals(double.class.getSimpleName())) {
                            field.set(obj, json.getDouble(name));
                        } else if (type.equals(Boolean.class.getSimpleName())) {
                            field.set(obj, json.getBoolean(name));
                        } else if (type.equals(boolean.class.getSimpleName())) {
                            field.set(obj, json.getBoolean(name));
                        }
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            MyLog.debug(" parse Error: " + e.getMessage());
        }
        return null;
    }

    public static <T> List<T> parseList(JSONArray ja, Class<T> clazz) {

        if (ja == null) {
            return null;
        }
        try {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < ja.length(); ++i) {
                JSONObject jo = ja.getJSONObject(i);
                T o = parseObject(jo, clazz);
                list.add(o);
            }
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            MyLog.debug(" parseList Error: " + e.getMessage());
        }
        return null;
    }

    public static String gps2mStr(double lat_a, double lng_a, double lat_b,
                                  double lng_b) {

        if (lat_b < 0.1 || lng_b < 0.1 || lat_a < 0.1 || lng_a < 0.1) {
            return "";
        }
        double val = gps2m(lat_a, lng_a, lat_b, lng_b);
        return String.format("%.1f公里", val);
    }

    // 计算距离
    public static double gps2m(double lat_a, double lng_a, double lat_b,
                               double lng_b) {

        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000;
        return s / 1000;
    }

    // 判断字符串是否为空
    public static Boolean isEmptyOrNull(String s) {
        if (s == null || s.length() <= 0) {
            return true;
        }
        return false;
    }

    public static double ParseDouble(String str) {
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

    public static long ParseLong(String str) {
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

    public static int ParseInt(String str) {
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

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = TimestampToDateTime(sdate);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = fm.format(cal.getTime());
        String paramDate = fm.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        }
        // else if (days == 1) {
        // ftime = "昨天";
        // } else if (days == 2) {
        // ftime = "前天 ";
        // } else if (days > 2 && days < 31) {
        // ftime = days + "天前";
        // } else if (days >= 31 && days <= 2 * 31) {
        // ftime = "一个月前";
        // } else if (days > 2 * 31 && days <= 3 * 31) {
        // ftime = "2个月前";
        // } else if (days > 3 * 31 && days <= 4 * 31) {
        // ftime = "3个月前";
        // }

        else {
            SimpleDateFormat sfm = new SimpleDateFormat("MM-dd HH:mm");
            sfm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            ftime = sfm.format(time);
        }
        return ftime;
    }

    /**
     * 今年非近期时间显示 08-16 12:45
     * <p>
     * 非今年显示格式为2016-08-16 12:15
     *
     * @param sdate
     * @return
     */
    public static String forumFriendlyTime(String sdate) {
        Date time = TimestampToDateTime(sdate);

        if (time == null) {

            return "";
        }
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmy = new SimpleDateFormat("yyyy");
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        fmy.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = fm.format(cal.getTime());
        String paramDate = fm.format(time);
        int curYear = cal.get(Calendar.YEAR);
        int year = Integer.parseInt(fmy.format(time));
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (curYear > year) {

            ftime = getDateString(sdate);
        } else {
            SimpleDateFormat sfm = new SimpleDateFormat("MM-dd HH:mm");
            sfm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            ftime = sfm.format(time);
        }
        return ftime;
    }


    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String productFriendlyTime(String sdate) {
        Date time = TimestampToDateTime(sdate);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmy = new SimpleDateFormat("MM-dd");
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        fmy.setTimeZone(TimeZone.getTimeZone("GMT+08"));

        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);

        String result;
        int year = Integer.parseInt(yyyy.format(time));
        MyLog.debug("year :" + year);
        if (curYear != year) {
            result = fm.format(time);
        } else {
            result = fmy.format(time);
        }
        return result;
    }


    // 时间戳转日期格式
    public static String TimestampToDateStr(String time) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        long unixLong = ParseLong(time) * 1000;
        String date = fm.format(new Date(unixLong));
        return date;
    }

    // 时间戳转日期格式
    public static String TimestampToDate(String time) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        long unixLong = ParseLong(time) * 1000;
        String date = fm.format(new Date(unixLong));
        return date;
    }

    public static String TimestampToDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        long unixLong = Long.parseLong(time) * 1000;
        String date = format.format(new Date(unixLong));
        return date;
    }


    // 时间戳转日期格式
    public static String TimestampToFormater(String time, String format) {
        SimpleDateFormat fm = new SimpleDateFormat(format);
        fm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        long unixLong = ParseLong(time) * 1000;
        String date = fm.format(new Date(unixLong));
        return date;
    }

    // 时间戳转日期格式
    public static Date TimestampToDateTime(String time) {

        if (TextUtils.isEmpty(time)) {

            return null;
        }
        long unixLong;
        if (time.length() == 10 || time.length() == 9) {

            unixLong = ParseLong(time) * 1000;
        } else {
            unixLong = ParseLong(time);
        }
        return new Date(unixLong);
    }


    public static Calendar strToCalendar(String str) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // 日期转时间戳
    public static String DateToTimestamp(Date date) {
        long times = date.getTime() / 1000;
        return times + "";
    }

    // 日期转时间戳
    public static Date DateStringToTimestamp(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        try {
            date = format.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 时间字符串格式化
    public static String getDateString(String sdate) {
        Date date = TimestampToDateTime(sdate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String t = format.format(date);
        return t;
    }

    // 时间字符串格式化
    public static String getDateStringFrm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String t = format.format(date);
        return t;
    }

    // 字符串转换成日期
    public static Date StrToDate(String str) {

        MyLog.debug("str :" + str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    // 字符串转换成日期
    public static Date StrToDate2(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getMonthDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return format.format(new Date(Long.parseLong(time)));
    }

    // 字符串格式 yyyy-MM-dd 转换时间戳
    public static String yyyy_mm_dd_ToTimestamp(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long times = date.getTime();
        return times / 1000 + "";
    }

    // 获取文件夹大小
    public static long getFolderSize(File dir) {

        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getFolderSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    public static String getFolderSizeToString(File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        // double kiloByte = size / 1024;
        // if (kiloByte < 1) {
        // return size + "Byte";
        // }
        //
        // double megaByte = kiloByte / 1024;
        // if (megaByte < 1) {
        // BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
        // return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
        // .toPlainString() + "KB";
        // }

        size = size / (1024 * 1024);
        return String.format("%.2fM", size);

        // if (gigaByte < 1) {
        // BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
        // return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
        // .toPlainString() + "M";
        // }
        //
        // double teraBytes = gigaByte / 1024;
        // if (teraBytes < 1) {
        // BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
        // return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
        // .toPlainString() + "G";
        // }
        // BigDecimal result4 = new BigDecimal(teraBytes);
        // return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        // + "T";
    }

    /**
     * 删除指定目录下文件及目录 * * @param deleteThisPath * @param filepath * @return
     */
    public static void deleteFolderFile(File file, boolean deleteThisPath) {
        try {

            if (file.isDirectory()) {// 如果下面还有文件
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(files[i], true);
                }
            }
            if (deleteThisPath) {

                if (!file.isDirectory()) {// 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {
                        // 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String queryVideoThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Video.Media._ID};
        String selection = MediaStore.Video.Media.DATA + " = ? ";
        String[] selectionArgs = new String[]{path};

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int mediaId = -1;
        if (cursor.moveToFirst()) {
            int idxId = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            mediaId = cursor.getInt(idxId);
        }
        cursor.close();
        if (mediaId == -1) {
            return null;
        }

        uri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Video.Thumbnails.DATA};
        selection = MediaStore.Video.Thumbnails.VIDEO_ID + " =  ? ";
        selectionArgs = new String[]{String.valueOf(mediaId)};

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    private static Cursor query(Context context, Uri uri, String[] projection,
                                String selection, String[] selectionArgs) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs,
                null);
        return cursor;
    }

    /**
     * 数据库中是否存在某张表
     *
     * @param tabName
     * @param db
     * @return
     */
    public static boolean tableIsExist(String tabName, SQLiteDatabase db) {

        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {

            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }


    // 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("images".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return uri.getPath();
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) MyApplication.Instance
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 显示信息
     *
     * @return
     */
    public static void showToast(String msg) {
//		Toast.makeText(MyApplication.Instance, msg, Toast.LENGTH_SHORT).show();
//        SingleToast.showToast(msg);
        ToastUtils.show(msg);
//        ToastFactory.getInstance(MyApplication.Instance).makeTextShow(msg, Toast.LENGTH_SHORT);

    }

    // 反射获取资源
    public static int getResourceId(String name) {
        try {
            // 根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
            Field field = R.drawable.class.getField(name);
            // 取得并返回资源的id的字段(静态变量)的值，使用反射机制
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }


    // 解析标签
    public static CharSequence parseEmoji(String str, Context mContext) {

        if (TextUtils.isEmpty(str)) {

            return "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            // String s0 = matcher.group(0);
            String s1 = matcher.group(1);
            // str=str.replace(s0, " 哈 ");
            int id = getResourceId(s1);
            if (id == 0) {
                return builder;
            }
//			GifDrawable drawable = new GifDrawable();
            Drawable drawable = mContext.getResources().getDrawable(id);
            drawable.setBounds(0, 0, DensityUtils.dp2px(mContext, 20),
                    DensityUtils.dp2px(mContext, 20));// 这里设置图片的大小
            ImageSpan imageSpan = new ImageSpan(drawable,
                    ImageSpan.ALIGN_BOTTOM);
            builder.setSpan(imageSpan, matcher.start(), matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    // 转换标签
    public static CharSequence transferEmoji(String str, Context mContext) {

        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            // String s0 = matcher.group(0);
            String s1 = matcher.group(1);
            // str=str.replace(s0, " 哈 ");
            HashMap<String, String> emoji = EmojiUtils.getEmoji();
            String s = emoji.get(s1);
            str = str.replace(":" + s1 + ":", s);
        }
        return str;
    }


    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    public static boolean savePic(String sdpath, Bitmap b) {

        FileOutputStream fos = null;
        try {

            File f = new File(sdpath);
            if (f.exists()) {
                f.delete();
            }

            fos = new FileOutputStream(f);

            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                MyLog.debug("save complete");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.debug("save errer");
            return false;
        }
        return true;
    }

    public static void savePic(String sdpath, Bitmap b, int quality) {

        FileOutputStream fos = null;
        try {

            File f = new File(sdpath);
            if (f.exists()) {
                f.delete();
            }

            fos = new FileOutputStream(f);

            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                fos.flush();
                fos.close();
                MyLog.debug("save complete");
            }
        } catch (Exception e) {
            MyLog.debug(e.getMessage());
            MyLog.debug("save errer");
        }

    }


    /**
     * 获得请求的服务端数据的userAgent
     *
     * @return
     */
    public static String getUserAgent() {
        StringBuilder ua = new StringBuilder("youxiake.com");
        ua.append("/Android");// 手机系统平台
        ua.append("/" + Build.VERSION.RELEASE);// 手机系统版本
        ua.append("/" + Build.MODEL); // 手机型号
        return ua.toString();
    }

    public static float GetTextWidth(String text, float Size) {
        //第一个参数是要计算的字符串，第二个参数是字提大小
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(Size);
        return FontPaint.measureText(text);
    }

    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        return doc.toString();
    }

    public static Boolean hasGif(String str) {

        Matcher matcher = gifPattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static SpannableString TransformString(String title, String keyWord) {
        keyWord = escapeExprSpecialWord(keyWord);
        if (title == null || keyWord == null) {
            return null;
        }
        SpannableString mTitle = new SpannableString(title);
        Pattern p = Pattern.compile(keyWord);
        Matcher m = p.matcher(mTitle);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            mTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#FCBC00")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return mTitle;
    }

    public static SpannableString TransformStringStyle(String title, String keyWord) {

        if (title == null) {

            return null;
        }
        SpannableString mTitle = new SpannableString(title);
        Pattern p = Pattern.compile(keyWord);
        Matcher m = p.matcher(mTitle);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
//            mTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#FCBC00")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTitle.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return mTitle;
    }

    public static SpannableString TransformString(String color, String title, String keyWord) {
        SpannableString mTitle = new SpannableString(title);
        if (!TextUtils.isEmpty(keyWord) && !TextUtils.isEmpty(title)) {
            Pattern p = Pattern.compile(keyWord);
            Matcher m = p.matcher(mTitle);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                mTitle.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return mTitle;
        }
        return mTitle;
    }

    public static SpannableString transformColorString(String color, String title, String uname, String rename) {
        SpannableString mTitle = new SpannableString(title);
        Pattern p = Pattern.compile(uname);
        Matcher m = p.matcher(mTitle);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            mTitle.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (rename != null) {
            try {
                Pattern pp = Pattern.compile(rename);
                Matcher mm = pp.matcher(mTitle);
                while (mm.find()) {
                    int start = mm.start();
                    int end = mm.end();
                    mTitle.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return mTitle;
    }

    public static String[] returnImageUrlsFromHtml(String newContent) {
        List<String> imageSrcList = new ArrayList<String>();
        String htmlCode = newContent;
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            if (!src.contains("smiley/youxiaoxia")) {
                src = src + "?imageView2/0/w/1080";
                imageSrcList.add(src);
            }
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList", "资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }

    /**
     * 根据code的类型 进行操作
     *
     * @param code
     * @param js
     */
    public static void scanResult(final Context context, String result, String code, JSONObject js) {


//        // group_join 扫描加群
//        if (code != null && "group_join".equals(code)) {
//            final String groupId = js.optString("id");
//            if (!TextUtils.isEmpty(groupId)) {
//                RequestParams requestParams = new RequestParams();
//                requestParams.put("groupid", groupId);
//                ApiX1.getInstance().getImGroupInfo(context, requestParams, new GsonCallBack<GroupInfoModel>() {
//                    @Override
//                    public void onFailure(int statusCode, Throwable arg0) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(GsonResultModel<GroupInfoModel> resultModel, Object obj) {
//
//                        if (resultModel.isSucc()) {
//                            GroupInfoModel data = resultModel.getData();
//                            if (data != null) {
//                                UserModel model = UserModel.getAccount(context);
//                                if (data.getIn_group() == 1) {
//                                    if (model != null) {
//                                        RongIM.getInstance().startGroupChat(context,
//                                                groupId, data.getGroupname());
//                                    } else {
//                                        Intent intent = new Intent(context, LoginActivity.class);
//                                        context.startActivity(intent);
//                                    }
//                                } else {
//                                    if (model != null) {
//                                        Intent resultIntent = new Intent(context,
//                                                InviteGroupActivity.class);
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("targetId", groupId);
//                                        resultIntent.putExtras(bundle);
//                                        context.startActivity(resultIntent);
//                                    } else {
//                                        Intent intent = new Intent(context, LoginActivity.class);
//                                        context.startActivity(intent);
//                                    }
//                                }
//                            }
//
//                        } else {
//                            UtilHelper.showToast(resultModel.getMsg());
//                        }
//
//                    }
//                });
//
//            }
//
//        } else {
//            if (result.contains("youxiake.com/lines")) {
//                Pattern urlPattern = Pattern
//                        .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
//                String pid = null;
//                Matcher matcher = urlPattern.matcher(result);
//                while (matcher.find()) {
//                    if (matcher.groupCount() >= 3) {
//                        pid = matcher.group(2);
//                    }
//                }
//                if (!TextUtils.isEmpty(pid)) {
//                    Intent intent = new Intent(context, ProductDetailActivity.class);
//                    intent.putExtra("pid", pid);
//                    context.startActivity(intent);
//                    return;
//                }
//            }
//
//            if (result.contains("www.youxiake.com/visas/detail")) {
//                Pattern urlPattern = Pattern
//                        .compile("(^|&|\\?)+vid=+([^&]*)(&|$)");
//                String pid = null;
//                Matcher matcher = urlPattern.matcher(result);
//                while (matcher.find()) {
//                    if (matcher.groupCount() >= 3) {
//                        pid = matcher.group(2);
//                    }
//                }
//                if (!TextUtils.isEmpty(pid)) {
//                    Intent intent = new Intent(context, ProductDetailActivity.class);
//                    intent.putExtra("pid", pid);
//                    context.startActivity(intent);
//                    return;
//                }
//            }
        if (result.contains("getcoupon")) {
            String[] split = result.split(":");
            MyLog.debug("split : ======" + split[1]);
            UserManager.getSingleton().couponGet(split[1], 0);

        } else {
            boolean isContains = false;
            BaseConfigModel instance = BaseConfigModel.getInstance();

            if (instance != null) {
                List<String> qrcode_domain = instance.getQrcode_domain();
                if (qrcode_domain != null) {
                    for (int i = 0; i < qrcode_domain.size(); i++) {
                        if (result.contains(qrcode_domain.get(i))) {
                            YxkLinkUtil.handleWebUrl((BaseActivity) context, result);
                            isContains = true;
                            break;
                        }
                    }
                }
            }
            if (isContains) {
                return;
            }
        }


//
//            if (result.contains("youxiake")) {
//                YxkLinkUtil.handleUrl((BaseActivity) context, result);

//            if (result.contains("youxiake.com/lines")) {
//                Pattern urlPattern = Pattern
//                        .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
//                String pid = null;
//                Matcher matcher = urlPattern.matcher(result);
//                while (matcher.find()) {
//                    if (matcher.groupCount() >= 3) {
//                        pid = matcher.group(2);
//                    }
//                }
//                if (!TextUtils.isEmpty(pid)) {
//                    Intent intent = new Intent(context, ProductDetailActivity.class);
//                    intent.putExtra("pid", pid);
//                    context.startActivity(intent);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(Constant.TAG_CONTENT, result);
//                    Intent intent = new Intent(context, ScannerResultActivity.class);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            } else
//            if (result.contains("getcoupon")) {
//                String[] split = result.split(":");
//                MyLog.debug("split : ======" + split[1]);
//                UserManager.getSingleton().couponGet(split[1], 99);
//
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.TAG_CONTENT, result);
//                Intent intent = new Intent(context, ScannerResultActivity.class);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }

//        }
    }

    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = (beWidth < beHeight) ? beWidth : beHeight;
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

    @Deprecated //最新使用yxkLinkUtils中的方法
    public static boolean handleUrl(String url, Context mContext) {
        if (url != null) {
            MyLog.error("url :" + url);
//            外链
            if (url.indexOf("youxiake") == -1) {
                Intent intent = new Intent(mContext, MyWebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            } else {
                if (url.contains("thread-")) {
                    String[] split = url.split("-");
                    String tid = split[1];
                    if (!TextUtils.isEmpty(tid)) {
                        Intent intent = new Intent(mContext, CommunityForumDetailActivity.class);
                        intent.putExtra("tid", StringHelper.parseInt(tid));
                        mContext.startActivity(intent);
                    }
                } else if (url.contains("forum")) {
                    String[] split = url.split("=");
                    String[] split1 = split[2].split("&");
                    String tid = split1[0];
                    if (!TextUtils.isEmpty(tid)) {
                        Intent intent = new Intent(mContext, CommunityForumDetailActivity.class);
                        intent.putExtra("tid", StringHelper.parseInt(tid));
                        mContext.startActivity(intent);
                    }
                } else if (url.contains("bbs")) {

                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(url);
                    m.find();
                    String group = m.group();
                    MyLog.debug("group:------------" + group);
                    if (!TextUtils.isEmpty(group)) {
                        if (StringHelper.parseInt(group) > 1000000) {
                            Intent intent = new Intent(mContext, MyWebViewActivity.class);
                            intent.putExtra("url", url);
                            mContext.startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, CommunityForumDetailActivity.class);
                            intent.putExtra("tid", StringHelper.parseInt(group));
                            mContext.startActivity(intent);
                        }

                    }

                } else if (url.contains("film")) {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(url);
                    m.find();
                    String film_id = m.group();
                    if (!TextUtils.isEmpty(film_id)) {
                        Intent intent = new Intent(mContext, CommunityVideoPlayActivity.class);
                        intent.putExtra("film_id", StringHelper.parseInt(film_id));
                        mContext.startActivity(intent);
                    }
                } else if (url.contains("album")) {
                    String id = url.substring(url.lastIndexOf("/") + 1, url.length());
                    if (!TextUtils.isEmpty(id)) {
                        Intent intent = new Intent(mContext, CameraDetailActivity.class);
                        intent.putExtra("album_id", StringHelper.parseInt(id));
                        mContext.startActivity(intent);
                    }
                } else if (url.contains("lines.html")) {
                    Pattern urlPattern = Pattern
                            .compile("(^|&|\\?)+id=+([^&]*)(&|$)");
                    String pid = null;
                    Matcher matcher = urlPattern.matcher(url);
                    while (matcher.find()) {
                        if (matcher.groupCount() >= 3) {
                            pid = matcher.group(2);
                        }
                    }
                    if (!TextUtils.isEmpty(pid)) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("pid", pid);
                        mContext.startActivity(intent);
                    }
                } else if (url.contains("gonglue")) {
                    String[] split = url.split("=");
                    String new_id = split[1];
                    if (!TextUtils.isEmpty(new_id)) {
                        Intent intent = new Intent(mContext, CommunityStrategyDetailActivity.class);
                        intent.putExtra(Constant.TAG_Id, new_id);
                        mContext.startActivity(intent);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
