package com.umengshared.lwc.myumengshared.Tools;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Activity 管理工具
 * <p>
 * Created by lingwancai on
 * 2018/8/2 09:56
 */
public class ActvityCollecter {

    private static ArrayList<AppCompatActivity> mList = new ArrayList<>();

    /**
     * 添加活动
     *
     * @param activity
     */
    public static void addActivity(AppCompatActivity activity) {
        mList.add(activity);
    }

    /**
     * 移除活动
     *
     * @param activity
     */
    public static void removeActivity(AppCompatActivity activity) {
        mList.remove(activity);
    }

    /**
     * 清除所有活动
     */
    public static void clearAllActivity() {
        for (AppCompatActivity appCompatActivity : mList) {
            if (!appCompatActivity.isFinishing()) {
                appCompatActivity.finish();
            }
        }
        mList.clear();
    }
}
