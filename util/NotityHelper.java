package com.youxiake.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.youxiake.R;
import com.youxiake.ui.main.MainActivity;

/**
 * Created by zsm on 16/5/23.
 */
public class NotityHelper {


    /** 加通知
     * @param mContext
     * @param title
     * @param context
     */
    public static void notityMe(Context mContext, String title, String context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(context);
        PendingIntent intent = PendingIntent.getActivity(mContext, 0,
                new Intent(mContext, MainActivity.class), 0);
        mBuilder.setContentIntent(intent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        //		// 设置点击清除通知
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(1, notification);
    }

    // 取消通知
    public static void cancelNotify(Context mContext) {
        final NotificationManager manager = (NotificationManager) mContext
                .getSystemService(mContext.NOTIFICATION_SERVICE);

        manager.cancel(1);
    }



   //状态栏出现消息
    public static void notifySimpleNotifycation(Context mContext, String ticker, String title,
                                         String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                mContext)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setContentIntent(
                        PendingIntent.getActivity(mContext, 0, new Intent(), 0))
                .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();

        NotificationManagerCompat.from(mContext).notify(1000, notification);
    }


    /** 取消通知
     * @param mContext
     */
    public static void cancellNotification(Context mContext) {
        NotificationManagerCompat.from(mContext).cancel(1000);
    }


}
