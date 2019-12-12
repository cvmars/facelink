package com.youxiake.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.youxiake.ui.pedometer.util.Logger;
import com.youxiake.ui.pedometer.util.Util;

import java.util.concurrent.atomic.AtomicInteger;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DbName = "youxiake.db";
    private static final String STEP_NAME = "YX_STEP";

    //升级版本 清风2019年9月25日升级
    public static final int newVersion = 13;
    public static final int oldVersion = 12;
    private static DBHelper instance;
    private static final AtomicInteger openCounter = new AtomicInteger();


    public static synchronized DBHelper getInstance(final Context c) {
        if (instance == null) {
            instance = new DBHelper(c.getApplicationContext());
        }
        openCounter.incrementAndGet();
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DbName, null, newVersion);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        MyLog.debug("onCreate db");
        db.execSQL("CREATE TABLE Product (Id integer PRIMARY KEY AUTOINCREMENT,pid text,product_name text, sub_name text,price text,people text,product_pic text,starttime text,addtime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE TABLE ProductRecord (Id integer PRIMARY KEY AUTOINCREMENT,pid text,thumb text,product_name text,place_label text,type_or_cat  text,price_label text,days text, sub_name text,price text,people text,product_pic text,starttime text,statisticsCode text,addtime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE TABLE SearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE CameraSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE NoticeChutuan (Id integer PRIMARY KEY AUTOINCREMENT,tour_id integer,title text,content text,timestamp text,is_read text,uid text)");
        db.execSQL("CREATE TABLE NoticeOrder (Id integer PRIMARY KEY AUTOINCREMENT,msg_id integer,oid text,title text,content text,timestamp text,is_read text,uid text)");
        db.execSQL("CREATE TABLE YX_STEP (date INTEGER, steps INTEGER)");
        db.execSQL("CREATE TABLE TAGNAME (Id integer PRIMARY KEY AUTOINCREMENT,tagName text)");
        db.execSQL("CREATE TABLE ForumSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,block_id text,tag text)");
        db.execSQL("CREATE TABLE FindSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");

        db.execSQL("CREATE TABLE NoticeOrderV2 (Id integer PRIMARY KEY AUTOINCREMENT," +
                "msg_id integer,oid text,title text,content text,timestamp text,is_read text,uid text,handler text,handler_url text,comment_id text)");
        db.execSQL("CREATE TABLE  if not exists CameraDestSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE  if not exists CityLocationRecord (Id integer PRIMARY KEY AUTOINCREMENT,name text, sitecode text, mddId text)");
    }

    //需要调用请修改v属性
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        MyLog.debug("onUpgrade db");

        db.execSQL("CREATE TABLE  if not exists Product (Id integer PRIMARY KEY AUTOINCREMENT,pid text,product_name text, sub_name text,price text,people text,product_pic text,starttime text,addtime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE TABLE  if not exists ProductRecord (Id integer PRIMARY KEY AUTOINCREMENT,pid text,thumb text,product_name text,place_label text,type_or_cat  text,price_label text,days text, sub_name text,price text,people text,product_pic text,starttime text,statisticsCode text,addtime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))");
        if (oldVersion < 13 && newVersion == 13 && !exitColumn(db, "ProductRecord")) {
             // 不存在这个字段
                db.execSQL("alter table ProductRecord add statisticsCode text");
        }
        db.execSQL("CREATE TABLE  if not exists SearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE  if not exists CameraSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE  if not exists NoticeChutuan (Id integer PRIMARY KEY AUTOINCREMENT,tour_id integer,title text,content text,timestamp text,is_read text,uid text)");
        db.execSQL("CREATE TABLE  if not exists NoticeOrder (Id integer PRIMARY KEY AUTOINCREMENT,msg_id integer,oid text,title text,content text,timestamp text,is_read text,uid text)");
        db.execSQL("CREATE TABLE  if not exists YX_STEP (date INTEGER, steps INTEGER)");
        db.execSQL("CREATE TABLE  if not exists TAGNAME (Id integer PRIMARY KEY AUTOINCREMENT,tagName text)");
        db.execSQL("CREATE TABLE  if not exists ForumSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,block_id text,tag text)");
        db.execSQL("CREATE TABLE  if not exists FindSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE  if not exists NoticeOrderV2 (Id integer PRIMARY KEY AUTOINCREMENT," +
                "msg_id integer,oid text,title text,content text,timestamp text,is_read text,uid text,handler text,handler_url text,comment_id text)");
        db.execSQL("CREATE TABLE  if not exists CameraDestSearchRecord (Id integer PRIMARY KEY AUTOINCREMENT,tag text)");
        db.execSQL("CREATE TABLE  if not exists CityLocationRecord (Id integer PRIMARY KEY AUTOINCREMENT,name text, sitecode text, mddId text)");
        setNullNotice(db);
    }


	/**
	 * 判断数据库表列存不存在
	 */
	public boolean exitColumn(SQLiteDatabase db,String tableName){
		boolean exits = true;
		String sql = "select * from "+ tableName + " LIMIT 0";
		Cursor cursor = db.rawQuery(sql, null);
        int columnId = cursor.getColumnIndex("statisticsCode");
		if(columnId == -1){
			exits = false;
		}
		return exits;
	}


    /**
     * 删除表的数据
     *
     * @param db
     */
    private void setNullNotice(SQLiteDatabase db) {

        try {
            db.execSQL("delete from NoticeChutuan");
            db.execSQL("delete from NoticeOrder");
        } catch (Exception e) {
            UtilHelper.showToast("数据库删除错误!");
        }
    }


    /**
     * =============================游侠运动数据操作=============================================
     *
     * */


    /**
     * Query the 'steps' table. Remember to close the cursor!
     *
     * @param columns       the colums
     * @param selection     the selection
     * @param selectionArgs the selction arguments
     * @param groupBy       the group by statement
     * @param having        the having statement
     * @param orderBy       the order by statement
     * @return the cursor
     */
    public Cursor query(final String[] columns, final String selection,
                        final String[] selectionArgs, final String groupBy, final String having,
                        final String orderBy, final String limit) {
        return getReadableDatabase()
                .query(STEP_NAME, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * Inserts a new entry push_nav_in the database, if there is no entry for the given
     * date yet. Steps should be the current number of steps and it's negative
     * value will be used as offset for the new date. Also adds 'steps' steps to
     * the previous day, if there is an entry for that date.
     * <p/>
     * This method does nothing if there is already an entry for 'date' - use
     * {@link #} push_nav_in this case.
     * <p/>
     * To restore data from a backup, use {@link #insertDayFromBackup}
     *
     * @param date  the date push_nav_in ms since 1970
     * @param steps the current step value to be used as negative offset for the
     *              new day; must be >= 0
     */
    public void insertNewDay(long date, int steps) {
        getWritableDatabase().beginTransaction();
        try {
            Cursor c = getReadableDatabase().query(STEP_NAME, new String[]{"date"}, "date = ?",
                    new String[]{String.valueOf(date)}, null, null, null);
            if (c.getCount() == 0 && steps >= 0) {

                // add 'steps' to yesterdays count
                addToLastEntry(steps);

                // add today
                ContentValues values = new ContentValues();
                values.put("date", date);
                // use the negative steps as offset
                values.put("steps", -steps);
                getWritableDatabase().insert(STEP_NAME, null, values);
            }
            c.close();

            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    /**
     * Adds the given number of steps to the last entry push_nav_in the database
     *
     * @param steps the number of steps to add. Must be > 0
     */
    public void addToLastEntry(int steps) {
        if (steps > 0) {
            getWritableDatabase().execSQL("UPDATE " + STEP_NAME + " SET steps = steps + " + steps +
                    " WHERE date = (SELECT MAX(date) FROM " + STEP_NAME + ")");
        }
    }

    /**
     * Inserts a new entry push_nav_in the database, if there is no entry for the given
     * date yet. Use this method for restoring data from a backup.
     * <p/>
     * This method does nothing if there is already an entry for 'date'.
     *
     * @param date  the date push_nav_in ms since 1970
     * @param steps the step value for 'date'; must be >= 0
     * @return true if a new entry was created, false if there was already an
     * entry for 'date'
     */
    public boolean insertDayFromBackup(long date, int steps) {
        getWritableDatabase().beginTransaction();
        boolean re;
        try {
            Cursor c = getReadableDatabase().query(STEP_NAME, new String[]{"date"}, "date = ?",
                    new String[]{String.valueOf(date)}, null, null, null);
            re = c.getCount() == 0 && steps >= 0;
            if (re) {
                ContentValues values = new ContentValues();
                values.put("date", date);
                values.put("steps", steps);
                getWritableDatabase().insert(STEP_NAME, null, values);
            }
            c.close();
            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
        return re;
    }

    /**
     * Writes the current steps database to the log
     */
    public void logState() {
        Cursor c = getReadableDatabase()
                .query(STEP_NAME, null, null, null, null, null, "date DESC", "5");
        Logger.log(c);
        c.close();
    }

    /**
     * Get the total of steps taken without today's value
     *
     * @return number of steps taken, ignoring today
     */
    public int getTotalWithoutToday() {
        Cursor c = getReadableDatabase()
                .query(STEP_NAME, new String[]{"SUM(steps)"}, "steps > 0 AND date > 0 AND date < ?",
                        new String[]{String.valueOf(Util.getToday())}, null, null, null);
        c.moveToFirst();
        int re = c.getInt(0);
        c.close();
        return re;
    }

    /**
     * Get the number of steps taken for a specific date.
     * <p/>
     * If date is CalendarUtil.getToday(), this method returns the offset which needs to
     * be added to the value returned by getCurrentSteps() to get todays steps.
     *
     * @param date the date push_nav_in millis since 1970
     * @return the steps taken on this date or Integer.MIN_VALUE if date doesn't
     * exist push_nav_in the database
     */
    public int getSteps(final long date) {
        Cursor c = getReadableDatabase().query(STEP_NAME, new String[]{"steps"}, "date = ?",
                new String[]{String.valueOf(date)}, null, null, null);
        c.moveToFirst();
        int re;
        if (c.getCount() == 0) re = Integer.MIN_VALUE;
        else re = c.getInt(0);
        c.close();
        return re;
    }

    /**
     * Get the number of steps taken between 'start' and 'end' date
     * <p/>
     * Note that todays entry might have a negative value, so take care of that
     * if 'end' >= CalendarUtil.getToday()!
     *
     * @param start start date push_nav_in ms since 1970 (steps for this date included)
     * @param end   end date push_nav_in ms since 1970 (steps for this date included)
     * @return the number of steps from 'start' to 'end'. Can be < 0 as todays
     * entry might have negative value
     */
    public int getSteps(final long start, final long end) {
        Cursor c = getReadableDatabase()
                .query(STEP_NAME, new String[]{"SUM(steps)"}, "date >= ? AND date <= ?",
                        new String[]{String.valueOf(start), String.valueOf(end)}, null, null, null);
        int re;
        if (c.getCount() == 0) {
            re = 0;
        } else {
            c.moveToFirst();
            re = c.getInt(0);
        }
        c.close();
        return re;
    }

    /**
     * Removes all entries with negative values.
     * <p/>
     * Only call this directly after boot, otherwise it might remove the current
     * day as the current offset is likely to be negative
     */
    public void removeNegativeEntries() {
        getWritableDatabase().delete(STEP_NAME, "steps < ?", new String[]{"0"});
    }

    /**
     * Removes invalid entries from the database.
     * <p/>
     * Currently, an invalid input is such with steps >= 200,000
     */
    public void removeInvalidEntries() {
        getWritableDatabase().delete(STEP_NAME, "steps >= ?", new String[]{"200000"});
    }

    /**
     * Get the number of 'valid' days (= days with a step value > 0).
     * <p/>
     * The current day is also added to this number, even if the value push_nav_in the
     * database might still be < 0.
     * <p/>
     * It is safe to divide by the return value as this will be at least 1 (and
     * not 0).
     *
     * @return the number of days with a step value > 0, return will be >= 1
     */
    public int getDays() {
        Cursor c = getReadableDatabase()
                .query(STEP_NAME, new String[]{"COUNT(*)"}, "steps > ? AND date < ? AND date > 0",
                        new String[]{String.valueOf(0), String.valueOf(Util.getToday())}, null,
                        null, null);
        c.moveToFirst();
        // todays is not counted yet
        int re = c.getInt(0) + 1;
        c.close();
        return re <= 0 ? 1 : re;
    }

    /**
     * Saves the current 'steps since boot' sensor value push_nav_in the database.
     *
     * @param steps since boot
     */
    public void saveCurrentSteps(int steps) {
        ContentValues values = new ContentValues();
        values.put("steps", steps);
        if (getWritableDatabase().update(STEP_NAME, values, "date = -1", null) == 0) {
            values.put("date", -1);
            getWritableDatabase().insert(STEP_NAME, null, values);
        }
    }

    /**
     * Reads the latest saved value for the 'steps since boot' sensor value.
     *
     * @return the current number of steps saved push_nav_in the database or 0 if there
     * is no entry
     */
    public int getCurrentSteps() {
        int re = getSteps(-1);
        return re == Integer.MIN_VALUE ? 0 : re;
    }


    /**
     * =============================游侠运动数据操作=============================================
     *
     * */
}
