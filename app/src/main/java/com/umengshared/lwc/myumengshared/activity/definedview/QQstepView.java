package com.umengshared.lwc.myumengshared.activity.definedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.umengshared.lwc.myumengshared.R;

/**
 * 仿QQ运动步
 * <p>
 * Created by lingwancai on
 * 2018/8/29 12:32
 */
public class QQstepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLACK;
    private int mBordrWidth = 20;
    private int mStepTextSize;
    private int mStepTextColor;
    private Paint mOutPanter;

    //总共 当前的
    private int mStepMax ;
    private int mCurrntStep ;

    private Paint mInnerPanter;
    private Paint mTextPanter;


    public QQstepView(Context context) {
        this(context, null);
    }

    public QQstepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQstepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性，编写xxx.xml
        //3.在布局中使用
        //4.在自定义View中获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QQstepView);
        mOuterColor = a.getColor(R.styleable.QQstepView_outerColor, mOuterColor);
        mInnerColor = a.getColor(R.styleable.QQstepView_innerColor, mInnerColor);
        mStepTextSize = a.getDimensionPixelSize(R.styleable.QQstepView_stepTextSize, mStepTextSize);
        mBordrWidth = (int) a.getDimension(R.styleable.QQstepView_borderWidth, mBordrWidth);
        mStepTextColor = a.getColor(R.styleable.QQstepView_stepTextColor, mStepTextColor);

        a.recycle();
        mOutPanter = new Paint();
        mOutPanter.setAntiAlias(true);
        mOutPanter.setStrokeWidth(mBordrWidth);
        mOutPanter.setColor(mOuterColor);
        mOutPanter.setStrokeCap(Paint.Cap.ROUND);//圆弧
        mOutPanter.setStyle(Paint.Style.STROKE);//画笔空心

        mInnerPanter = new Paint();
        mInnerPanter.setAntiAlias(true);
        mInnerPanter.setStrokeWidth(mBordrWidth);
        mInnerPanter.setColor(mInnerColor);
        mInnerPanter.setStrokeCap(Paint.Cap.ROUND);//圆弧
        mInnerPanter.setStyle(Paint.Style.STROKE);//画笔空心

        mTextPanter = new Paint();
        mTextPanter.setAntiAlias(true);
        mTextPanter.setColor(mStepTextColor);
        mTextPanter.setTextSize(mStepTextSize);


        //5.onMeasure()
        //6.画外圆弧，内圆弧，文字
        //7.其他
    }

    /**
     * //5.onMeasure() 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能 是：wrap_content ，可能宽高不一致
        //获取模式

        //确保是正方形 宽度和高度不一致 取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设值
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);

    }

    //6.画外圆弧，内圆弧，文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.1 画外圆弧 分析：圆弧闭合了 思考：边缘没有显示完整  描边有宽度 而且圆弧边缘不是圆的
        // 另一种算法：研究下
        // RectF rectF = new RectF( mBordrWidth/2,  mBordrWidth/2,getWidth()- mBordrWidth/2, getHeight() -  mBordrWidth/2);
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBordrWidth / 2;
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 135, 270, false, mOutPanter);

        //6.2 画内圆弧  不能写死，百分比 是从外面设置的
        if (mStepMax == 0) return;
        float sweepAngle = (float) mCurrntStep / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInnerPanter);

        //6.3 画文字
        String stepText = mCurrntStep + "";
        Rect textBounds = new Rect();
        //相当于赋值给textBounds 与c语言类似
        mTextPanter.getTextBounds(stepText, 0, stepText.length(), textBounds);
        //dx = 控件的宽度的一半 - 文字宽度的一半
        int dx = getWidth() / 2 - textBounds.width() / 2;
        //基线 baseLine
        Paint.FontMetricsInt fontmetrics = mTextPanter.getFontMetricsInt();
        //字体的高度
        int dy = (fontmetrics.bottom - fontmetrics.top) - fontmetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPanter);
    }

    //7.其他 用方法设置数值
    public synchronized void setStepMax(int setMax){
        this.mStepMax = setMax;
    }

    public synchronized void setCurrentStepMax(int setCurrnt){
        this.mCurrntStep = setCurrnt;
        // 动起来，就要不断绘制 调用onDraw()方法
        invalidate();
    }
}
