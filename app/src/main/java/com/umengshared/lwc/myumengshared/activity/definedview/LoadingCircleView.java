package com.umengshared.lwc.myumengshared.activity.definedview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;


/**
 * Loading 加载
 * <p>
 * Created by lingwancai on
 * 2018/9/4 15:52
 */
public class LoadingCircleView extends LinearLayout {
    private static final long ANIMATOR_DURATION = 650;
    private static final String TAG = "LoadingCircleView";
    private ImageView ivLoadingView;
    private boolean isStopAnimator = false;

    public LoadingCircleView(Context context) {
        this(context, null);
    }

    public LoadingCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoadinViewLayout(context);
    }

    /**
     * 加载组合布局
     *
     * @param context
     */
    private void initLoadinViewLayout(Context context) {
        //加载写好的布局
        inflate(getContext(), R.layout.ui_loading_circle_view, this);
        ivLoadingView = (ImageView) this.findViewById(R.id.iv_loading_view);
        post(new Runnable() {
            @Override
            public void run() {
                //等view绘制完再执行动画
                startRotateAniamator();
            }
        });
    }

    /**
     * 执行旋转动画
     */
    private void startRotateAniamator() {
        if (isStopAnimator) {
            MyLog.e(TAG, "动画停止 >>>");
            return;
        }
        MyLog.e(TAG, "startRotateAniamator >>>");
        ObjectAnimator mRotationAnimator = ObjectAnimator.ofFloat(ivLoadingView, "rotation", 0, 360);
        //匀速差值器
        mRotationAnimator.setInterpolator(new LinearInterpolator());
        mRotationAnimator.setDuration(ANIMATOR_DURATION);
        mRotationAnimator.start();

        mRotationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //结束加载 继续循环执行
                startRotateAniamator();
            }
        });

    }

    /**
     *
     * 停止动画，释放资源
     *
     * @param visibility
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.GONE);
        //清理动画
        ivLoadingView.clearAnimation();
        //移除子view
        ViewGroup parent = (ViewGroup) getParent();
        if (parent!=null){
            //移除当前的view
            parent.removeView(this);
            //移除自己的view
            removeAllViews();
        }
        isStopAnimator = true;
    }
}
