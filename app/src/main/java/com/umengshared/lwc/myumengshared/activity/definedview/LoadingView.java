package com.umengshared.lwc.myumengshared.activity.definedview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;

/**
 * 58加载动画
 * <p>
 * Created by lingwancai on
 * 2018/8/31 09:35
 */
public class LoadingView extends LinearLayout {

    public static final String TAG = "LoadingView";
    //动画执行时间
    private static final long ANIMATOR_DURATION = 350;
    //阴影
    private View mShapeShadowView;
    //形状
    private ShapeLoadlingView mShapeLoadingView;
    private int mTranslationDistance;

    //停止动画
    private boolean isStopAnimator = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2Px(80);
        //初始化加载布局
        initLayout();

    }

    /**
     * dp 转 px
     *
     * @param i
     * @return
     */
    private int dip2Px(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
    }

    /**
     * 初始化加载布局
     */
    private void initLayout() {
        //1.加载 写好的组合控件 ui_shape_view
        //1.1 实例化View
//        View loadview = inflate(getContext(), R.layout.ui_shape_view, null);
//        1.2 添加到该View中
//        addView(loadview); 看讲解源码找插件换肤
        //一句话搞定
        inflate(getContext(), R.layout.ui_shape_view, this);//this 代表是父类，加载到父类中
        mShapeLoadingView = (ShapeLoadlingView) this.findViewById(R.id.shape_loading_view);
        mShapeShadowView = this.findViewById(R.id.shape_shadow_view);

        //进来是下落动画 --- 优化性能
//        startFallAnimator(); --没有优化之前的--意思是在onCreate()方法中执行，布局文件解析 反射创建实例
        post(new Runnable() {
            @Override
            public void run() {
                //onResume 之后View绘制流程执行完毕之后，参考（View的绘制流程源码-分析一章）
                startFallAnimator();
            }
        });
    }

    /**
     * 进来是下落动画
     */
    private void startFallAnimator() {
        if (isStopAnimator) return;
        MyLog.e(TAG, "startFallAnimator >>>");
        //下落位移动画
        //动画作用的对象
        //mShapeLoadingView.setTranslationY(); 不记得可以打点调用
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "translationY", 0, mTranslationDistance);
        translationAnimator.setDuration(ANIMATOR_DURATION);
//        translationAnimator.start();

        //配合中间阴影动画 缩小
        ObjectAnimator scalxAnimator = ObjectAnimator.ofFloat(mShapeShadowView, "scaleX", 1f, 0.3f);
        scalxAnimator.setDuration(ANIMATOR_DURATION);
//        scalxAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        //下抛 加速插播器
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scalxAnimator);

        //下落完后执行上抛，监听动画完毕
        //是一种思想， 在adapter中实现一个自己想要的方法 如：万能BannerView写过
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
                //上抛动画
                startUpAnimator();
                //改变形状
                mShapeLoadingView.exChange();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //开始旋转
                startRotationAnimator();
            }
        });
        //启动
        animatorSet.start();

    }

    /**
     * 上抛的时候需旋转
     */
    private void startRotationAnimator() {
//        mShapeLoadingView.setRotation();
        ObjectAnimator rotationAnimator = null;
        switch (mShapeLoadingView.getCurrentShape()) {
            case Circle:
            case Square://180
                rotationAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "rotation", 0, 180);
                break;
            case Triangle://120
                rotationAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "rotation", 0, -120);
                break;
        }
        rotationAnimator.setDuration(ANIMATOR_DURATION);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();

    }

    /**
     * 开始执行上抛动画
     */
    private void startUpAnimator() {
        if (isStopAnimator) return;
        MyLog.e(TAG, "startUpAnimator >>>");
        //上抛位移动画
        //动画作用的对象
        //mShapeLoadingView.setTranslationY(); 不记得可以打点调用
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "translationY", mTranslationDistance, 0);
        translationAnimator.setDuration(ANIMATOR_DURATION);
//        translationAnimator.start();

        //配合中间阴影动画 放大
        ObjectAnimator scalxAnimator = ObjectAnimator.ofFloat(mShapeShadowView, "scaleX", 0.3f, 1f);
        scalxAnimator.setDuration(ANIMATOR_DURATION);
//        scalxAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        //差值器 上抛减速
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scalxAnimator);

        //下落完后执行上抛，监听动画完毕
        //是一种思想， 在adapter中实现一个自己想要的方法 如：万能BannerView写过
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
                //下落动画
                startFallAnimator();
            }
        });

        //启动
        animatorSet.start();
    }

    /**
     * 不可见就移除view 动画
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.GONE);
        MyLog.e(TAG, "setVisibility >>>");
        //不要再去播放和计算，少走一些系统的源码（View的绘制流程）
        //清理动画
        mShapeLoadingView.clearAnimation();
        mShapeShadowView.clearAnimation();
        //把LoadingView从父类布局移除
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            //从父类布局移除
            parent.removeView(this);
            //移除自己所有的view
            removeAllViews();
        }
        isStopAnimator = true;

    }
}
