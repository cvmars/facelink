package com.youxiake.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.youxiake.model.CityModel;
import com.youxiake.model.GOrderNoticeModel;
import com.youxiake.model.GToursModel;
import com.youxiake.model.ProductDetailModel;
import com.youxiake.model.SearchTagModel;
import com.youxiake.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
        this.context = context;
    }


    // 添加搜索add
    public void addSearchTagModel(SearchTagModel model) {


        Cursor c = db.rawQuery("SELECT * FROM  SearchRecord where tag=? ", new String[]{model.tag});

        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO SearchRecord (tag) VALUES(?)", new String[]{model.tag});
    }


    /**
     * 添加标签进数据库
     *
     * @param tagName
     */
    public void addTagName(String tagName) {
        Cursor c = db.rawQuery("SELECT * FROM  TAGNAME where tagName=?", new String[]{tagName});
        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO TAGNAME (tagName) VALUES(?)", new String[]{tagName});
    }


    /**
     * 获取所有的tagName
     *
     * @return
     */
    public List<String> getTagNameList() {

        Cursor c = db.rawQuery("SELECT * FROM  TAGNAME  order BY Id desc",
                null);
        List<String> list = new ArrayList<>();

        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex("tagName"));
            list.add(result);
        }
        c.close();
        return list;
    }

    // 添加摄影搜索
    public void addCameraSearchTagModel(String keyword) {
        Cursor c = db.rawQuery("SELECT * FROM  CameraSearchRecord where tag=?", new String[]{keyword});

        if (c.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tag", keyword);
            db.delete("CameraSearchRecord", "tag=?", new String[]{keyword});
            db.insert("CameraSearchRecord", null, contentValues);
        } else {
            db.execSQL(" INSERT INTO CameraSearchRecord (tag) VALUES(?)", new String[]{keyword});
        }
        c.close();
    }

    // 显示摄影搜索
    public List<String> getCameraSearchTagModelList() {

        Cursor c = db.rawQuery("SELECT * FROM  CameraSearchRecord  order BY Id desc",
                null);
        List<String> list = new ArrayList<>();

        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex("tag"));
            list.add(result);
        }
        c.close();
        return list;
    }


    /**
     * 清除摄影记录
     */
    public void deteleCameraTag() {

        db.execSQL("delete from CameraSearchRecord");
    }

    // 显示搜索
    public List<SearchTagModel> getSearchTagModelList() {

        Cursor c = db.rawQuery("SELECT * FROM  SearchRecord  order BY Id desc",
                null);
        List<SearchTagModel> list = new ArrayList<SearchTagModel>();

        while (c.moveToNext()) {
            SearchTagModel model = new SearchTagModel();
            model.tag = c.getString(c.getColumnIndex("tag"));
            list.add(model);
        }
        c.close();
        return list;
    }

    public void clareTag() {
        db.execSQL("delete from SearchRecord");
    }

    public void addProduct(ProductDetailModel model) {
        Cursor c = db.rawQuery("SELECT * FROM  ProductRecord where pid=?", new String[]{model.pid});
        if (c.getCount() > 0) {
            db.delete("ProductRecord", "pid = ?", new String[]{model.pid});
            db.execSQL(
                    "INSERT INTO ProductRecord (pid,product_name, thumb,type_or_cat,place_label,price_label,days,statisticsCode)"
                            + " VALUES(?,?,?,?,?,?,?,?)", new String[]{model.pid,
                            model.product_name, model.thumb, model.type_or_cat,
                            model.place_label, model.price_label, model.days, model.statisticsCode});
        }else {
            db.execSQL(
                    "INSERT INTO ProductRecord (pid,product_name, thumb,type_or_cat,place_label,price_label,days,statisticsCode)"
                            + " VALUES(?,?,?,?,?,?,?,?)", new String[]{model.pid,
                            model.product_name, model.thumb, model.type_or_cat,
                            model.place_label, model.price_label, model.days, model.statisticsCode});
        }


    }

    // 读取本地 （浏览记录）
    public List<ProductDetailModel> getProductList(int page, int pageSize) {

        Cursor c = db.rawQuery("SELECT * FROM  ProductRecord order BY Id desc limit "
                + pageSize + " offset " + page * pageSize, null);
        List<ProductDetailModel> list = new ArrayList<ProductDetailModel>();
        while (c.moveToNext()) {
            ProductDetailModel model = new ProductDetailModel();
            model.pid = c.getString(c.getColumnIndex("pid"));
            model.product_name = c.getString(c.getColumnIndex("product_name"));
            model.type_or_cat = c.getString(c.getColumnIndex("type_or_cat"));
            model.thumb = c.getString(c.getColumnIndex("thumb"));
            model.place_label = c.getString(c.getColumnIndex("place_label"));
            model.people = c.getString(c.getColumnIndex("people"));
            model.days = c.getString(c.getColumnIndex("days"));
            model.price_label = c.getString(c.getColumnIndex("price_label"));
            model.statisticsCode = c.getString(c.getColumnIndex("statisticsCode"));
            list.add(model);
        }
        c.close();
        return list;
    }

    public String getPid() {

        String pid = "";
        Cursor c = db.rawQuery("SELECT * FROM  ProductRecord order BY Id desc limit " +
                +1, null);
        while (c.moveToNext()) {
            pid = c.getString(c.getColumnIndex("pid"));
            return pid;
        }

        return "";
    }

    /**
     * 将出团通知的数据添加到数据库
     *
     * @param data
     */
    public void addChutuanNoticeList(List<GToursModel.MessagesEntity> data) {

        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                GToursModel.MessagesEntity model = data.get(i);
                Cursor c = db.query("NoticeChutuan", new String[]{"tour_id,uid"}, "tour_id = ? and uid = ?", new String[]{model.getId() + "", uid}, null, null, null);
                if (!c.moveToFirst()) {
                    db.execSQL(
                            "INSERT INTO NoticeChutuan (tour_id,title,content,timestamp,is_read,uid)"
                                    + " VALUES(?,?,?,?,?,?)", new String[]{model.getId() + "", model.getTitle(),
                                    model.getContent(), model.getTimestamp(), model.getIs_read() + "", uid});
                }
                c.close();
            }
        }

    }

    /**
     * 将订单消息的数据添加到数据库
     *
     * @param data
     */
    public void addOrderNoticeList(List<GOrderNoticeModel.MessagesEntity> data) {
        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                GOrderNoticeModel.MessagesEntity model = data.get(i);
                Cursor c = db.query("NoticeOrder", new String[]{"msg_id", uid}, "msg_id = ? and uid = ?", new String[]{model.getId() + "", uid}, null, null, null);
                if (!c.moveToFirst()) {
                    db.execSQL(
                            "INSERT INTO NoticeOrder (msg_id,title,oid,content, timestamp,is_read,uid)"
                                    + " VALUES(?,?,?,?,?,?,?)", new String[]{model.getId() + "", model.getTitle(), model.getOid() + "",
                                    model.getContent(), model.getTimestamp(), model.getIs_read() + "", uid});
                }
                c.close();
            }

        }

    }


    /**
     * 将订单消息的数据添加到数据库
     *
     * @param data
     */
    public void addOrderNoticeListV2(List<GOrderNoticeModel.MessagesEntity> data) {
        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                GOrderNoticeModel.MessagesEntity model = data.get(i);
                Cursor c = db.query("NoticeOrderV2", new String[]{"msg_id", uid}, "msg_id = ? and uid = ?", new String[]{model.getId() + "", uid}, null, null, null);
                if (!c.moveToFirst()) {
                    db.execSQL(
                            "INSERT INTO NoticeOrderV2 (msg_id,title,oid,content, timestamp,is_read,uid,handler,handler_url,comment_id )"
                                    + " VALUES(?,?,?,?,?,?,?,?,?,?)", new String[]{model.getId() + "", model.getTitle(), model.getOid() + "",
                                    model.getContent(), model.getTimestamp(), model.getIs_read() + "", uid, model.getHandler(), model.getHandler_url(), model.getComment_id()});
                }
                c.close();
            }
        }
    }

    /**
     * 读取数据库中的出团通知
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<GToursModel.MessagesEntity> getNoticeChutuanList(int page, int pageSize) {
        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;

        List<GToursModel.MessagesEntity> list = new ArrayList<GToursModel.MessagesEntity>();

        if (TextUtils.isEmpty(uid)) {

            return list;
        }
        Cursor c = db.rawQuery("SELECT * FROM  NoticeChutuan where uid = " + uid + " order BY timestamp desc limit "
                + 0 + "," + page * pageSize, null);


        while (c.moveToNext()) {
            GToursModel.MessagesEntity model = new GToursModel.MessagesEntity();
            model.setTitle(c.getString(c.getColumnIndex("title")));
            model.setContent(c.getString(c.getColumnIndex("content")));
            model.setTimestamp(c.getString(c.getColumnIndex("timestamp")));
            model.setIs_read(c.getInt(c.getColumnIndex("is_read")));
            model.setId(c.getInt(c.getColumnIndex("tour_id")));
            db.execSQL("update NoticeChutuan   set is_read = 1 where tour_id =" + model.getId() + " and uid = " + uid);
            list.add(model);

        }
        c.close();
        return list;
    }

    /**
     * 读取数据库中的订单消息
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<GOrderNoticeModel.MessagesEntity> getNoticeOrderList(int page, int pageSize) {
        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;
        Cursor c = db.rawQuery("SELECT * FROM  NoticeOrder where uid = " + uid + " order BY timestamp desc limit "
                + 0 + "," + page * pageSize, null);

        List<GOrderNoticeModel.MessagesEntity> list = new ArrayList<GOrderNoticeModel.MessagesEntity>();

        while (c.moveToNext()) {
            GOrderNoticeModel.MessagesEntity model = new GOrderNoticeModel.MessagesEntity();
            model.setTitle(c.getString(c.getColumnIndex("title")));
            model.setContent(c.getString(c.getColumnIndex("content")));
            model.setTimestamp(c.getString(c.getColumnIndex("timestamp")));
            model.setOid(c.getString(c.getColumnIndex("oid")));
            model.setIs_read(c.getInt(c.getColumnIndex("is_read")));
            model.setId(c.getInt(c.getColumnIndex("msg_id")));
            db.execSQL("update NoticeOrder set is_read = 1 where msg_id =" + model.getId() + " and uid = " + uid);
            list.add(model);
        }
        c.close();
        return list;
    }


    /**
     * 读取数据库中的订单消息
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<GOrderNoticeModel.MessagesEntity> getNoticeOrderListV2(int page, int pageSize) {
        UserModel models = UserModel.getAccount(context);
        String uid = models.uid;

        List<GOrderNoticeModel.MessagesEntity> list = new ArrayList<GOrderNoticeModel.MessagesEntity>();

        if (TextUtils.isEmpty(uid)) {

            return list;
        }
        Cursor c = db.rawQuery("SELECT * FROM  NoticeOrderV2 where uid = " + uid + " order BY timestamp desc limit "
                + 0 + "," + page * pageSize, null);

        while (c.moveToNext()) {
            GOrderNoticeModel.MessagesEntity model = new GOrderNoticeModel.MessagesEntity();
            model.setTitle(c.getString(c.getColumnIndex("title")));
            model.setContent(c.getString(c.getColumnIndex("content")));
            model.setTimestamp(c.getString(c.getColumnIndex("timestamp")));
            model.setOid(c.getString(c.getColumnIndex("oid")));
            model.setIs_read(c.getInt(c.getColumnIndex("is_read")));
            model.setId(c.getInt(c.getColumnIndex("msg_id")));
            model.setHandler(c.getString(c.getColumnIndex("handler")));
            model.setHandler_url(c.getString(c.getColumnIndex("handler_url")));
            model.setComment_id(c.getString(c.getColumnIndex("comment_id")));
            db.execSQL("update NoticeOrderV2 set is_read = 1 where msg_id =" + model.getId() + " and uid = " + uid);
            list.add(model);
        }
        c.close();
        return list;
    }


    //判断数据库是否有该产品
    public boolean isHave(ProductDetailModel model) {

        Cursor c = db.query("ProductRecord", new String[]{"pid"}, "pid = ?", new String[]{model.pid}, null, null, null);
        if (c.moveToFirst()) {
            db.delete("ProductRecord", "pid = ?", new String[]{model.pid});
            addProduct(model);
            return true;
        }
        return false;
    }


    public void clearProduct() {
        db.execSQL("delete from ProductRecord");
    }

    // 添加社区帖子模块搜索
    public void addForumSearchRecord(SearchTagModel model) {
        Cursor c = db.rawQuery("SELECT * FROM  ForumSearchRecord where tag='"
                + model.tag + "' AND block_id = " + model.block_id + " ", null);

        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO ForumSearchRecord (block_id,tag) VALUES(?,?)", new String[]{model.block_id, model.tag});
    }

    // 获取社区帖子模块搜索记录
    public List<String> getForumSearchRecord(String block_id) {

        Cursor c = db.rawQuery("SELECT * FROM ForumSearchRecord where block_id='" + block_id + "' ORDER BY id desc",
                null);
        List<String> list = new ArrayList<>();

        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex("tag"));
            list.add(result);
        }
        c.close();
        return list;
    }

    //清空帖子模块记录
    public void clearForumSearchRecord() {
        db.execSQL("delete from ForumSearchRecord");
    }

    // 添加地址搜索
    public void addFindSearchTagModel(String keyword) {
        Cursor c = db.rawQuery("SELECT * FROM  FindSearchRecord where tag=?", new String[]{keyword});

        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO FindSearchRecord (tag) VALUES(?)", new String[]{keyword});
    }

    // 显示遇见地址搜索
    public List<String> getFindSearchTagModelList() {

        Cursor c = db.rawQuery("SELECT * FROM  FindSearchRecord  order BY Id desc",
                null);
        List<String> list = new ArrayList<>();

        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex("tag"));
            list.add(result);
        }
        c.close();
        return list;
    }


    /**
     * 清除遇见地址记录
     */
    public void deteleFindTag() {

        db.execSQL("delete from FindSearchRecord");
    }

    // 添加摄影目的地搜索
    public void addCameraDestSearch(String keyword) {
        Cursor c = db.rawQuery("SELECT * FROM  CameraDestSearchRecord where tag=?", new String[]{keyword});

        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO CameraDestSearchRecord (tag) VALUES(?)", new String[]{keyword});
    }

    // 显示摄影目的地搜索
    public List<String> getCameraDestSearchList() {

        Cursor c = db.rawQuery("SELECT * FROM  CameraDestSearchRecord  order BY Id desc",
                null);
        List<String> list = new ArrayList<>();

        while (c.moveToNext()) {
            String result = c.getString(c.getColumnIndex("tag"));
            list.add(result);
        }
        c.close();
        return list;
    }

    public void deleteCameraDest(String tag) {
        Cursor c = db.rawQuery("SELECT * FROM  CameraDestSearchRecord where tag='"
                + tag + "' ", null);
        if (c.getCount() > 0) {
            db.delete("CameraDestSearchRecord", "tag=?", new String[]{tag});
        }
        c.close();
    }

    /**
     * 删除所有摄影目的地记录
     */
    public void deteleDestCamera() {

        db.execSQL("delete from CameraDestSearchRecord");
    }


    // 添加搜索add
    public void addCityModel(CityModel.CityBean model) {


        Cursor c = db.rawQuery("SELECT * FROM  CityLocationRecord where name=? ", new String[]{model.name});

        if (c.getCount() > 0) {
            c.close();
            return;
        }
        db.execSQL(" INSERT INTO CityLocationRecord (name, sitecode, mddId) VALUES(?,?,?)", new String[]{model.name, model.sitecode, model.mddId});
    }

    // 显示搜索
    public List<CityModel.CityBean> getCityModelList() {

        Cursor c = db.rawQuery("SELECT * FROM  CityLocationRecord  order BY Id desc",
                null);
        List<CityModel.CityBean> list = new ArrayList<CityModel.CityBean>();

        while (c.moveToNext()) {
            CityModel.CityBean model = new CityModel.CityBean();
            model.name = c.getString(c.getColumnIndex("name"));
            model.sitecode = c.getString(c.getColumnIndex("sitecode"));
            model.mddId = c.getString(c.getColumnIndex("mddId"));
            list.add(model);
        }
        c.close();
        return list;
    }
}
