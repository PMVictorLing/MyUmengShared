package com.umengshared.lwc.myumengshared.fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lingwancai on
 * 2018/8/2 11:45
 */
public class TestBean implements Serializable{
    @SerializedName("le-1")
    public float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }



}
