package com.youxiake.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.youxiake.app.MyConfig;
import com.youxiake.widget.ImageHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;


public class PhotoOperate {

    private Context context;

    public PhotoOperate(Context context) {
        this.context = context;
    }

    private File getTempFile(Context context) {
        File file = null;
        String fileName = "IMG_" + new Date().getTime()+"" +UtilHelper.getRandom();
        try {
            file = File.createTempFile(fileName, ".jpg", context.getCacheDir());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //String sdpath=  MyConfig.getYxkDir("cache")+fileName;
        //file = new File(sdpath);
        return file;
    }

    public File scal(Uri fileUri) throws Exception {
        String path = UtilHelper.getPath(context, fileUri);
        File outputFile = new File(path);
//        if (Global.isGif(path)) {
//            return outputFile;
//        }

        long fileSize = outputFile.length();
        final long fileMaxSize = MyConfig.FileUploadMaxSize;
        if (fileSize >= fileMaxSize) {
            MyLog.debug("fileSize >= fileMaxSize");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
//            double scale = Math.sqrt((float) fileSize / fileMaxSize);
//            options.outHeight = (int) (height / scale);
//            options.outWidth = (int) (width / scale);
//            options.inSampleSize = (int) (scale + 0.5);

            options.inSampleSize =  ImageHelper.calculateInSampleSize(options, 1200, 1200);

            MyLog.debug("options.inSampleSize :" + options.inSampleSize);
            
            options.inJustDecodeBounds = false;


            //MyLog.debug("options.inSampleSize:"+options.inSampleSize);

//            Bitmap bitmap =  BitmapFactory.decodeFile(path, options);
            Bitmap bitmap = loadBitmap(options,path,true);
                    outputFile = getTempFile(context);
            FileOutputStream fos = new FileOutputStream(outputFile);

            // 原始图片的宽高
            int height = options.outHeight;
            int width = options.outWidth;

            if(height > 3*width || width > 3 * height){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);//100代表不压缩
            }else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); //90代表压缩10%
            }
            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }

        } else {
            File tempFile = outputFile;
            outputFile = getTempFile(context);
            copyFileUsingFileChannels(tempFile, outputFile);
        }
        MyLog.error("File size" + outputFile.getTotalSpace()+","+path);
        return outputFile;
    }

    private static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /** 从给定的路径加载图片，并指定是否自动旋转方向*/
    public static Bitmap loadBitmap(BitmapFactory.Options options,String imgpath, boolean adjustOritation) {
        if (!adjustOritation) {
            return loadBitmap(imgpath,options);
        } else {
            Bitmap bm = loadBitmap(imgpath,options);
            int digree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imgpath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }

                MyLog.debug("digree :" +digree  +"   :,90 = 6: ");
            }

            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
            }
            return bm;
        }
    }

    /** 从给定路径加载图片*/
    public static Bitmap loadBitmap(String imgpath,BitmapFactory.Options options) {

        return BitmapFactory.decodeFile(imgpath,options);
    }

}
