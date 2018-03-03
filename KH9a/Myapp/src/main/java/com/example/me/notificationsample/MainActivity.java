package com.example.me.notificationsample;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.BitmapFactory;

import static com.example.me.notificationsample.MyReceiver.KEY_NUMBER;

/*
  Android O 对Notification作了重新设计，引入了Ntification Channel。本例针对Android O之前的系统，请保证
  targetSdkVersion<26
 */

public class MainActivity extends AppCompatActivity {
    private int noID=0;//notification ID
    private static final String MY_ACTION = "com.example.me.notificationsample.action.MY_ACTION";
    // 定义一个Button对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 为按钮设置单击监听器
    }

    public void stop(View v){
        Intent intentSV = new Intent(this, MyService.class);
        stopService(intentSV);
    }

    public void restart(View v){
        Intent intentSV = new Intent(this, MyService.class);
        startService(intentSV);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setContentTitle("音乐播放")
                .setContentText("点击停止")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.btn_star_big_on))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.shorttone))
                .build();
// 发布通知
        mNotificationManager.notify(noID, notification);
    }

    BroadcastReceiver  br;
    @Override
    protected void onStart() {
        super.onStart();
        Intent intentSV = new Intent(this, MyService.class);
        startService(intentSV);
        br=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                //如果无网络连接activeInfo为null
                //也可获取网络的类型
                if(isOnline()){ //网络连接
                    Toast.makeText(context, "网络连接",Toast.LENGTH_SHORT).show();
                }else { //网络断开
                    Toast.makeText(context, "网络断开，请用户停止音乐",Toast.LENGTH_SHORT).show();
                }
            }
    };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE" );
        this.registerReceiver(br,filter);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context. CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ( null!=networkInfo) {
            boolean wifiConnected = networkInfo.getType() == ConnectivityManager. TYPE_WIFI ;
            boolean mobileConnected = networkInfo.getType() == ConnectivityManager. TYPE_MOBILE ;
            Log. d ( "isOnline", "Wifi connected: " + wifiConnected);
            Log. d ( "isOnline", "Mobile connected: " + mobileConnected);
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

//    public  void onreceive(Context context,Intent intent)
//    {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setContentTitle("音乐播放")
//                .setContentText("点击停止")
//                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.btn_star_big_on))
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.shorttone));
//// Creates an explicit intent for an Activity in your app
//        Intent notifyIntent = new Intent(context, MyService.class);
//// Sets the Activity to start in a new, empty task
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notifyIntent.putExtra(KEY_NUMBER,NUMBER);
//// Creates the PendingIntent
//        PendingIntent notifyPendingIntent =
//                PendingIntent.getActivity(
//                        context,
//                        0,
//                        notifyIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(notifyPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager)
//                        context.getSystemService(Context.NOTIFICATION_SERVICE);
//// 发布通知
//        mNotificationManager.notify(noID, mBuilder.build());
//    }

}
