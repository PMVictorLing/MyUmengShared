package com.umengshared.lwc.myumengshared.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.UmengSharedActivity;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

/**
 * @Description:  SplashScreenActivity 启动页
 * @Author:  lingwancai
 * @Time:  2018/8/22 14:09
 */

public class SplashScreenActivity extends BaseActivity {


    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://进入主页
                    startActivity(new Intent(SplashScreenActivity.this,UmengSharedActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mhandler.sendEmptyMessage(1);
            }
        },3000);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //使用沉浸式模式 Android 4.4及以上系统才支持沉浸式模式
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
