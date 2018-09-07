package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 字母索引
 * Created by lingwancai on
 * 2018/8/31 15:33
 */
public class LetterSideBar extends View {

    //26个字母
    public static String[] mLetter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    private Paint mPaint;

    //当前触摸位置的字母
    private String mCurrentTouchletter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //自定义属性，颜色 字体大小 先写死
        mPaint.setTextSize(sp2px(12));//设置的是像素
        mPaint.setColor(Color.BLUE);
    }

    /**
     * sp 转 px
     *
     * @param sp
     * @return
     */
    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算指定高度 = 左右的padding+ 字母的高度（取决于画笔大小）
        int textWidth = (int) mPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        //高度可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //画26个字母 x绘制在最中介 = 宽度/2 - 文字/2
//        int x = getPaddingLeft();

        int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop())
                / mLetter.length;
        for (int i = 0; i < mLetter.length; i++) {

            //首先要知道每个字母的中心位置
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            //基线都是基于中心位置算的 ? 基线如何算
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (int) ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
            int baseLine = letterCenterY + dy;
            int textWidth = (int) mPaint.measureText(mLetter[i]);

            // x绘制在最中介 = 宽度/2 - 文字/2
            int x = getWidth() / 2 - textWidth / 2;

            //当前字母 高亮 用两个画笔（最好） 改变颜色
            if (mLetter[i].equals(mCurrentTouchletter)) {
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetter[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(mLetter[i], x, baseLine, mPaint);
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸字母 获取当前的位置
                float currentMoveY = event.getY();// 有可能是 -1
                //位置 = currentMoveY / 字母高度，通过位置获取字母
                int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop())
                        / mLetter.length;
                int currentPosition = (int) (currentMoveY / itemHeight);

                if (currentPosition < 0)
                    currentPosition = 0;

                if (currentPosition > mLetter.length - 1)
                    currentPosition = mLetter.length - 1;

                //优化---如果上次的与现在的一样就不去重绘 以后处理***********


                mCurrentTouchletter = mLetter[currentPosition];

                //触摸时回调 显示
                if (mLetterListerner != null) {
                    mLetterListerner.touch(mCurrentTouchletter, true);
                }

                //重新绘制
                invalidate();


                break;

            case MotionEvent.ACTION_UP://抬起消失--延时消失
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mLetterListerner != null) {
                            mLetterListerner.touch(mCurrentTouchletter, false);
                        }
                    }
                }, 300);

                break;
        }
        return true;
    }

    //回到监听
    private LetterTouchListener mLetterListerner;

    public void setOnLetterTouchListener(LetterTouchListener s) {
        this.mLetterListerner = s;
    }

    public interface LetterTouchListener {
        void touch(String letter, boolean isUp);
    }
}
