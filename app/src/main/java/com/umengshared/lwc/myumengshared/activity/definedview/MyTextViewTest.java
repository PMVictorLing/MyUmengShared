package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.umengshared.lwc.myumengshared.R;

/**
 * 自定义view练习,含义
 * <p>
 * Created by lingwancai on
 * 2018/8/28 15:47
 */
public class MyTextViewTest extends View {

    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    //会在实例化的时候调用
    // MyTextView myTextView = new MyTextView(this);
    public MyTextViewTest(Context context) {
        this(context,null);
    }


    //在布局layout中使用（调用）
    /* <com.umengshared.lwc.myumengshared.activity.definedview.MyTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="text practices"/>*/
    public MyTextViewTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    //在布局layout中使用（调用），布局中包含了style，自定义属性
    /* <com.umengshared.lwc.myumengshared.activity.definedview.MyTextView
    style="@style/default"
    android:text="text practices"/>*/
    public MyTextViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextViewTest,defStyleAttr,0);

        //处理错误 弄了半天--写法错误mTextColor = array.getColor(R.styleable.text_color_test,mTextColor);
        mText = array.getString(R.styleable.MyTextViewTest_my_text_test);
        mTextColor = array.getColor(R.styleable.MyTextViewTest_text_color_test,mTextColor);

        //回收
        array.recycle();
    }

    /**
     * 自定义view的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高都是由这个方法指定
        //指定控件的宽高，需要测量
        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    }

    /**
     * 用于绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(100, 100, 200, null);
    }

    /**
     * 处理跟用户交互的，事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下
                break;
            case MotionEvent.ACTION_MOVE://手指移动
                break;
            case MotionEvent.ACTION_UP://手指抬起
                break;
        }

        return super.onTouchEvent(event);

    }
}
