package com.umengshared.lwc.myumengshared.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.umengshared.lwc.myumengshared.Tools.Tool;

import org.greenrobot.eventbus.EventBus;

/**
 * @Description:  NetworkChangedReceiver 监听网络变化
 * @Author:  lingwancai
 * @Time:  2018/8/1 16:49
 */

public class NetworkChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        //判断当前网络是否可用
        boolean isConnected = Tool.isConnected(context);
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
}
