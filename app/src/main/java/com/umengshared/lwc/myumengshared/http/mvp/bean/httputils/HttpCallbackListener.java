package com.umengshared.lwc.myumengshared.http.mvp.bean.httputils;

/**
 *
 * 网络回调监听
 *
 * Created by lingwancai on
 * 2018/7/31 14:57
 */
public interface HttpCallbackListener {

    /**
     * 成功
     *
     * @param response
     */
    void onFinish(String response);

    /**
     * 失败
     *
     * @param e
     */
    void onFailer(Exception e);
}
