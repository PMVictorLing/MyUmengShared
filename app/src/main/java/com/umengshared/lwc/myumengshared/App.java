package com.umengshared.lwc.myumengshared;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umengshared.lwc.myumengshared.Tools.Tool;
import com.umengshared.lwc.myumengshared.crashtools.CrashHandler;
import com.umengshared.lwc.myumengshared.db.MyDataBaseHelper;

import static com.umengshared.lwc.myumengshared.Tools.Config.ITEM_MODE_TYPE;

public class App extends Application {

    private static App app;
    private MyDataBaseHelper mMyDataBaseHelper;

    public static App getInstance() {
        return app;
    }

    //各个平台的配置，建议放在全局Application或者程序入口
   /* {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin(Tool.WX_APPID, Tool.WX_APPSECRET);
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
//        PlatformConfig.setSinaWeibo("730961453", "b308ec73aead583d794a1c3572902c3a");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone(Tool.QQ_APPID, Tool.QQ_APPKEY);
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");

    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
//        Bugly.init(this, "99c00cb8bc", true);

        //友盟配置
        UMConfigure.init(this, "57de35a467e58e2669001604"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin(Tool.WX_APPID, Tool.WX_APPSECRET);
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("730961453", "b308ec73aead583d794a1c3572902c3a", "http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone(Tool.QQ_APPID, Tool.QQ_APPKEY);
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965", "5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy", "h7p2pjbzkkxt02a");

        setModeNight();

        //创建数据库 version 1 -- 2
        mMyDataBaseHelper = new MyDataBaseHelper(this,"BookStore.db",null,2);
        mMyDataBaseHelper.getWritableDatabase();

        //初始化异常处理器
        CrashHandler mCrashHandler = CrashHandler.getInstanse();
        mCrashHandler.init(this);
    }

    /**
     * 设置夜间模式
     */
    private void setModeNight() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getBoolean(ITEM_MODE_TYPE,false);
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
//        MultiDex.install(base);


        // 安装tinker
//        Beta.installTinker();
    }


}
