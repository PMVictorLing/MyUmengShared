package com.umengshared.lwc.myumengshared.wigets;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 正方形imageview
 * Created by lingwancai on
 * 2018/8/22 10:28
 */
public class SquareImage extends android.support.v7.widget.AppCompatImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);//宽高设置一样
    }
}
