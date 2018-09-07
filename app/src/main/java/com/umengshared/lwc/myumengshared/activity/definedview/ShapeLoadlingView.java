package com.umengshared.lwc.myumengshared.activity.definedview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.umengshared.lwc.myumengshared.R;

/**
 * Created by lingwancai on
 * 2018/8/30 17:06
 */
public class ShapeLoadlingView extends View {
    private Paint mPaint;
    private Shape mCureentShape;
    private ObjectAnimator mRectRoteAnimation;
    private ObjectAnimator mDefaultRoteAnimation;
    private int mHeight;
    private int mWidth;

    public ShapeLoadlingView(Context context) {
        this(context, null);
    }

    public ShapeLoadlingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeLoadlingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCureentShape = Shape.Circle;
        /*initRoteAnimation();*/

    }

    private void initRoteAnimation() {
        mRectRoteAnimation = ObjectAnimator.ofFloat(this, "rotation", 0, -120);
        mDefaultRoteAnimation = ObjectAnimator.ofFloat(this, "rotation", 0, 180);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCureentShape) {
            case Circle:
                drawCircle(canvas);
                break;
            case Triangle:
                drawTriangle(canvas);
                break;
            case Square:
                drawRect(canvas);
                break;
        }
    }

    /**
     * 改变形状
     */
    public void exChange() {
        switch (mCureentShape) {
            case Circle:
                mCureentShape = Shape.Square;
                break;
            case Square:
                mCureentShape = Shape.Triangle;
                break;
            case Triangle:
                mCureentShape = Shape.Circle;
                break;
        }
        //不断重新绘制
        invalidate();
    }

    public enum Shape {
        Circle, Square, Triangle
    }

    /**
     * 正方形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.re_bg));
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }

    /**
     * 三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.blue));
        Path path = new Path();
        path.moveTo(mWidth / 2, 0);
        path.lineTo(0, mHeight);
        path.lineTo(mWidth, mHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.green));
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
    }

    public Shape getCurrentShape(){
        return mCureentShape;
    }
}
