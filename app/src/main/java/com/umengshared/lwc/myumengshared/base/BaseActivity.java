package com.umengshared.lwc.myumengshared.base;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.ActvityCollecter;
import com.umengshared.lwc.myumengshared.Tools.Config;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.UmengSharedActivity;
import com.umengshared.lwc.myumengshared.activity.ZTNHMainActivity;
import com.umengshared.lwc.myumengshared.receiver.NetworkChangeEvent;
import com.umengshared.lwc.myumengshared.receiver.NetworkChangedReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * @Description: BaseActivity 基类
 * @Author: lingwancai
 * @Time: 2018/8/2 10:12
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IBaseView {

    private static final String TAG = "BaseActivity";
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    protected BaseActivity mActivity;

    protected boolean mCheckNetWork = true; //默认检查网络状态
    private NetworkChangedReceiver mNetworkChangedReceiver;
    private View mTipView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    public LocalBroadcastManager localBroadcast;
    private LocalForceOffLineBroadCast mLocalForceOffLineBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印活动名称便于查找
        MyLog.e(TAG, "" + this.getClass().getSimpleName());
        mActivity = this;
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setBaseView(bindLayout());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //设置statusbar
//        StatusBarUtil.setColor(this, Color.parseColor("#21aefe"));
        setStatusColor();
        initView(savedInstanceState, contentView);
        doBusiness();

        //监听网络变化 动态注册广播
        initNetWorkTipView();
        registerBroadcastReceiver();
        EventBus.getDefault().register(this);

        //activity 管理类
        ActvityCollecter.addActivity(this);
    }

    /**
     * 初始化网络布局
     */
    private void initNetWorkTipView() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        mTipView = layoutInflater.inflate(R.layout.layout_net_work_tipview, null);
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);//添加点击flags FLAG_NOT_TOUCH_MODAL
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        //status bar 与 title bar之和
        mLayoutParams.y = 100 + getStatusBarHeights();
        mTipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(Settings.ACTION_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                BaseActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * status bar高度
     *
     * @return
     */
    private int getStatusBarHeights() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        MyLog.e("dbw", "Status height:" + height);
        return height;
    }

    /**
     * 动态注册网络广播
     */
    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        mNetworkChangedReceiver = new NetworkChangedReceiver();
        registerReceiver(mNetworkChangedReceiver, intentFilter);
    }

    /**
     * @date 2018/3/15 9:44
     * @author lingwancai
     * @desc BaseActivity.java{设置statusbar颜色}
     */
    private void setStatusColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systemContent = (ViewGroup) findViewById(android.R.id.content);

            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            systemContent.getChildAt(0).setFitsSystemWindows(true);

            systemContent.addView(statusBarView, 0, lp);

        }
    }

    private int getStatusBarHeight() {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }


    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(contentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetworkChangedReceiver != null)
            unregisterReceiver(mNetworkChangedReceiver);
        EventBus.getDefault().unregister(this);
        ActvityCollecter.removeActivity(this);
    }

    /**
     * 网络回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        hasNetWork(event.mIsconnected);
    }

    /**
     * 是否显示 tipview网络提示
     *
     * @param mIsconnected
     */
    private void hasNetWork(Boolean mIsconnected) {
        if (isCheckNetWork()) {
            if (mIsconnected) {//
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
    }

    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    public boolean isCheckNetWork() {
        return mCheckNetWork;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //用户可见 注册强制下线广播
        registerForceOffLineBroadCast();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //不可见 取消强制线下广播
        if (mLocalForceOffLineBroadCast != null)
            localBroadcast.unregisterReceiver(mLocalForceOffLineBroadCast);
    }

    /**
     * 用户可见时注册强制下线广播
     */
    private void registerForceOffLineBroadCast() {
        //使用本地广播 获取本地广播管理实例
        localBroadcast = LocalBroadcastManager.getInstance(this);
        //注册本地广播监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.umengshared.lwc.braodcast.Local_Broadcast");
        mLocalForceOffLineBroadCast = new LocalForceOffLineBroadCast();
        localBroadcast.registerReceiver(mLocalForceOffLineBroadCast, intentFilter);

    }

    /**
     * 强制线下广播
     */
    public class LocalForceOffLineBroadCast extends BroadcastReceiver {

        private static final String TAG = "LocalForceOffLineBroadCast";

        @Override
        public void onReceive(final Context context, Intent intent) {
            MyLog.e(TAG, "强制线下广播");
            AlertDialog.Builder b = new AlertDialog.Builder(BaseActivity.this);
            b.setTitle("警告");
            b.setMessage("由于其他原因，您被强制下线！");
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActvityCollecter.clearAllActivity();
                    context.startActivity(new Intent(context, UmengSharedActivity.class));
                    dialog.dismiss();
                }
            });
            b.setCancelable(false);
            b.show();
        }
    }

    /**
     * 设置字体大小
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = getFontSize();
        MyLog.e(TAG, "getResources size=" + getFontSize());
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    public float getFontSize() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = preference.getFloat(Config.ITEM_MODE_FONT_TYPE, 1.0f);
        MyLog.e(TAG, "getFontSize size=" + fontSize);
        return fontSize == 1.0f ? mFontSize : fontSize;
    }

    public void setFontSize(float fontSize) {
        //存储变量
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        preference.edit().putFloat(Config.ITEM_MODE_FONT_TYPE, fontSize).apply();
    }

    private float mFontSize = 1.0f;


}