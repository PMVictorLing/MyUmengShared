package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.umengshared.lwc.myumengshared.R;

/**
 * Created by lingwancai on
 * 2018/7/12 15:38
 */
public class CircleView extends ImageView {

    private int mColor;
    private Paint mPaint;

    //java 实例化时调用
    public CircleView(Context context) {
        this(context,null);
    }

    //xml 引用时调用
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //加载自定义属性集
        TypedArray aypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        //解析属性集合中的 color
        //第二个参数 为默认值
        mColor = aypedArray.getColor(R.styleable.CircleView_circle_backgroud_color,Color.RED);

        //解析后释放资源
        aypedArray.recycle();

        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @Description: CircleView
     * @Author: lingwancai
     * @Time: 2018/7/12 15:44
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint();
//        mPaint.setColor(Color.BLUE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(5f);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取传入的padding值
        int paddingLeft = getPaddingLeft();
        int paddingbottom = getPaddingBottom();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();

        //获取组件宽高 考虑四个方向的padding值
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingbottom;

        //半径 = 宽、高最小值的一半
        int r = Math.min(width, height) / 2;

        //圆心 在控件的中央
        canvas.drawCircle(getWidth() / 2, getHeight()/ 2, r, mPaint);
    }
}
