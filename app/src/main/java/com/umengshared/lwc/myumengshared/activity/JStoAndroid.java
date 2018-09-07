package com.umengshared.lwc.myumengshared.activity;

import android.webkit.JavascriptInterface;

import com.umengshared.lwc.myumengshared.Tools.MyToast;

/**
 * Created by lingwancai on
 * 2018/7/19 14:27
 *  定义一个与JS对象映射关系的Android类
 */
public class JStoAndroid extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void showTest(String msg){
        MyToast.showToast(msg);
    }
}
