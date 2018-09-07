package com.umengshared.lwc.myumengshared.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.umengshared.lwc.myumengshared.Tools.MyLog;

/**
 * @Description:  MyIntentService 自动停止和开启线程的服务
 * @Author:  lingwancai
 * @Time:  2018/7/31 17:02
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.e(TAG,"MyIntentService onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        MyLog.e(TAG,"MyIntentService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.e(TAG,"MyIntentService onDestroy");
    }
}
