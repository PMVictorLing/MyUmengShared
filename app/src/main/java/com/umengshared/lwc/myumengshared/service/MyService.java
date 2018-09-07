package com.umengshared.lwc.myumengshared.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.activity.ZTNHMainActivity;

/**
 * @Description:  MyService 创建一个前台服务
 * @Author:  lingwancai
 * @Time:  2018/7/31 16:25
 */

public class MyService extends Service {
    private static final String TAG = "myservice";
    private static final String PUSH_CHANNEL_ID = "push_channel_id_1001";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.e(TAG,"MyService oncreate");
        Intent intent = new Intent(this,ZTNHMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this,PUSH_CHANNEL_ID)
                .setContentTitle("this is content titel")
                .setContentText("content")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setChannelId(PUSH_CHANNEL_ID)
                .build();
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.e(TAG,"MyService onStartCommand");


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.e(TAG,"MyService onDestroy");

    }

    /**
     * 8.0 通知适配
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            //分组（可选）
            //groupId要唯一
            String groupId = "group_001";
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, "广告");

            //创建group
            notificationManager.createNotificationChannelGroup(group);

            //channelId要唯一
            String channelId = "channel_001";

            NotificationChannel adChannel = new NotificationChannel(channelId,
                    "推广信息", NotificationManager.IMPORTANCE_DEFAULT);
            //补充channel的含义（可选）
            adChannel.setDescription("推广信息");
            //将渠道添加进组（先创建组才能添加）
            adChannel.setGroup(groupId);
            //创建channel
            notificationManager.createNotificationChannel(adChannel);

            //创建通知时，标记你的渠道id
            Notification notification = new Notification.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("一条新通知")
                    .setContentText("这是一条测试消息")
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);

        }
    }
}
