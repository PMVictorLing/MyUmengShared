package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义listview 解决显示不全的问题
 * Created by lingwancai on
 * 2018/8/28 17:02
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //解决显示不全的问题 32位的值
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //heightMeasureSpec 拿到32位的值 30位是：Integer.MAX_VALUE 2位是改变后的模式：MeasureSpec.AT_MOST
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
