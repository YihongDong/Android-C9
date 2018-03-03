package com.example.me.notificationsample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
public static final String ACTION_MYRECEIVER= "com.example.me.notificationsample.ACTION_MYRECEIVER";
private static int NOID=35;
public static String KEY_NUMBER="key_number";
private static final int NUMBER=829;


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("音乐播放")
                .setContentText("点击停止")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        // Creates an explicit intent for an Activity in your app
        Intent notifyIntent =
                new Intent(context, MyService.class);
// Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.putExtra(KEY_NUMBER,NUMBER);
// Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(notifyPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// 发布通知
        mNotificationManager.notify(NOID, mBuilder.build());
    }
}
