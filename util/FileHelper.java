package com.youxiake.util;

import android.content.Context;

import com.youxiake.app.MyConfig;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zsm on 16/5/25.
 */
public class FileHelper {


    /** 删除缓存
     * @param ctx
     * @param filename
     */
    public static void delObject(Context ctx, String filename) {

        File file = new File(ctx.getFilesDir(), filename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }


    /** 获取缓存对象
     * @param ctx
     * @param filename
     * @param <T>
     * @return
     */
    public static <T> T getObject(Context ctx, String filename) {

        T model = null;
        File file = new File(ctx.getFilesDir(), filename);
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        ctx.openFileInput(filename));
                model = (T) ois.readObject();
                ois.close();
            } catch (Exception e) {
                MyLog.debug("filename  error:" + e.getMessage());
            }
        }
        return model;
    }


    /** 缓存对象
     * @param ctx
     * @param data
     * @param filename
     * @param <T>
     */
    public static <T> void saveObject(Context ctx, T data, String filename) {
        File file = new File(ctx.getFilesDir(), filename);
        if (file.exists()) {
            file.delete();
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(
                    filename, Context.MODE_PRIVATE));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            MyLog.debug("filename saveCache error:" + e.getMessage());
        }
    }


}
