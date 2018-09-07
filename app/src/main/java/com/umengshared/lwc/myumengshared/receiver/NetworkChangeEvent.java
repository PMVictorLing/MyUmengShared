package com.umengshared.lwc.myumengshared.receiver;

/**
 * Created by lingwancai on
 * 2018/8/1 16:52
 */
public class NetworkChangeEvent {
    /**
     * 网络是否可用
     */
    public Boolean mIsconnected;

    public NetworkChangeEvent(Boolean isconnected) {
        this.mIsconnected = isconnected;
    }
}
