package com.youxiake.util;

import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.youxiake.service.UploadService;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Cvmars on 2016/8/22.
 */

public class QiuManage {

    private static QiuManage instance;

    private UploadManager manager;

    private QiuManage(){
        manager = new UploadManager();
    }

    public static QiuManage getInstance(){

        if(instance!=null){
            return instance;
        }else{
            return  new QiuManage();
        }
    }


    /**
     * 遇见上传文件
     * @param mPicFile
     * @param upLoadToken
     */
    public void findUpLoadPic(File mPicFile, final UploadService.UploadInfo imgInfo ,
                              String upLoadToken, final ProgressCallBack callback){


        manager.put(mPicFile,  imgInfo.UploadPath, upLoadToken, new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()){
                    MyLog.debug("--key--"+key+",--ResponseInfo--"+info+"--response--"+response);

                    callback.onCallback(response,imgInfo);

                }else{
//                    MyLog.error("--key--"+key+",--ResponseInfo--"+info+"--response--"+response);
                    callback.onFailure();
                }
            }
        },new UploadOptions(null, null, true,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        MyLog.debug(key + ": " + percent);
                    }
                },null));
    }

    public void CameraUpLoadPic(File mPicFile, final UploadService.UploadInfo imgInfo , String upLoadToken, final ProgressCallBack callback) {
        manager.put(mPicFile, imgInfo.UploadPath, upLoadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()){
                    MyLog.debug("--key--"+key+",--ResponseInfo--"+info+"--response--"+response+"--infopath--"+imgInfo.UploadPath+"--index--"+imgInfo.index);

                    callback.onCallback(response, imgInfo);

                }else{
                    MyLog.error("--key--"+key+",--ResponseInfo--"+info+"--response--"+response);
                    callback.onFailure();
                }
            }
        },  new UploadOptions(null, null, true,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        MyLog.debug(key + ": " + percent);
                    }
                },null));
    }

    /**
     * 游记上传文件
     * @param mPicFile
     * @param uploadInfo
     * @param upLoadToken
     */

    public void TravelsUpLoadPic(File mPicFile, UploadService.UploadInfo uploadInfo , String upLoadToken, final TravelsCallBack callback) {

        manager.put(mPicFile, uploadInfo.Path, upLoadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()){

                    callback.onCallback(response);

                }else{
                    MyLog.error("--key--"+key+",--ResponseInfo--"+info+"--response--"+response);
                    callback.onFailure();
                }
            }

        }, new UploadOptions(null, null, true,
                new UpProgressHandler(){
                    public void progress(String key, double percent){
                        MyLog.debug(key + ": " + percent);
                        callback.onProgress(percent);
                    }
                }, null));

    }

    public interface CallBack {

        public void onCallback(JSONObject response);

        public void onFailure();



    }
    public interface TravelsCallBack {

        public void onCallback(JSONObject response);

        public void onFailure();

        public void onProgress(double percent);


    }
    public interface ProgressCallBack {

        public void onCallback(JSONObject response, UploadService.UploadInfo info);

        public void onFailure();

    }
}
