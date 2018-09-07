package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;

/**
 * 玩转textview 颜色
 * <p>
 * Created by lingwancai on
 * 2018/8/30 09:02
 */
public class ChangeTextView extends TextView {
    private int mOrginColor = Color.BLACK;
    private int mChangerColor = Color.RED;

    //实现一个文字两种颜色
    // 绘制不变色的画笔
    private Paint mOriginPaint;

    //绘制变色的画笔
    private Paint mChangerPaint;

    //当前进度 开始设置为0
    private float mCurrentProgress = 0.0f;

    //文字变色，从左到右  从右到左 定义一个枚举
    private Direction mDirection = Direction.LEFT_TO_RIGHT;//默认值

    public enum Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public ChangeTextView(Context context) {
        this(context, null);
    }

    public ChangeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChangeTextView);
        mOrginColor = array.getColor(R.styleable.ChangeTextView_originColor, mOrginColor);
        mChangerColor = array.getColor(R.styleable.ChangeTextView_changerColor, mChangerColor);

        mOriginPaint = initPanter(mOrginColor);
        mChangerPaint = initPanter(mChangerColor);
        //回收资源
        array.recycle();


    }

    /**
     * 根据颜色获取画笔
     *
     * @param color
     */
    private Paint initPanter(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    //一个文字两种颜色

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);  注释父类ondrow 自己来画

        //绘制不变色的***********
        //裁剪 区域
        //根据进度把中间值算出来
        int middle = (int) (mCurrentProgress * getWidth());

       /* //画布保存
        canvas.save();
        Rect clip = new Rect(0, 0, middle, getHeight());
        canvas.clipRect(clip);

        String text = getText().toString();
        //获取字体宽度
        Rect bounds = new Rect();
        mOriginPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;

        //基线 baseline
        Paint.FontMetricsInt fontMetrics = mOriginPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseline = getHeight() / 2 + dy;

        canvas.drawText(text, x, baseline, mOriginPaint);
        //画布释放
        canvas.restore();

        canvas.save();
        //绘制变色的
        clip = new Rect(middle, 0, getWidth(), getHeight());
        canvas.clipRect(clip);
        canvas.drawText(text, x, baseline, mChangerPaint);
        canvas.restore();*/

        //上面代码冗余问题，整合一下
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            //从左变到右 左边是红色右边是黑色
            //变色
            drawText(canvas, mChangerPaint, 0, middle);
            //不变色
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            //右边是红色 左边是黑色
            //变色
            drawText(canvas, mChangerPaint, getWidth() - middle, getWidth());
            //不变色
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }


    }

    public void drawText(Canvas canvas, Paint paint, int start, int end) {
        //画布保存
        canvas.save();
        Rect clip = new Rect(start, 0, end, getHeight());
        canvas.clipRect(clip);

        String text = getText().toString();
        //获取字体宽度
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;

        //基线 baseline
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseline = getHeight() / 2 + dy;

        canvas.drawText(text, x, baseline, paint);
        //画布释放
        canvas.restore();
    }

    public void setmDirection(Direction direction) {
        this.mDirection = direction;
    }

    public void setmCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        //
        invalidate();
    }

    public void setOrginColor(int changerColor){
        this.mOrginColor = changerColor;
    }

    public void setmChangerColor(int changerColor){
        this.mChangerColor = changerColor;
    }
}
