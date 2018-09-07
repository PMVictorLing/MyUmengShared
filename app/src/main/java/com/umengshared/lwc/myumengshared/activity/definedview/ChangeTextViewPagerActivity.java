package com.umengshared.lwc.myumengshared.activity.definedview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.base.BaseActivity;
import com.umengshared.lwc.myumengshared.fragment.ChangeTextViewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeTextViewPagerActivity extends BaseActivity {

    @BindView(R.id.ll_header_layout)
    LinearLayout llHeaderLayout;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;

    private String[] items = new String[]{"直播", "关注", "段子", "体育", "科学", "生活","圈子","自然","直播", "关注", "段子", "体育", "科学", "生活","圈子","自然"};
    private ArrayList mIndicators = new ArrayList();

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_change_text_view_pager;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

        //初始化指示器
        initIndicator();
        //
        initViewPager();
    }

    private void initViewPager() {
        vpPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ChangeTextViewFragment.newInstance(items[position], "");
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //position 代表当前页
                //positionOffset 代码百分比 0 - 1 的值

                //从左到右
                ChangeTextView left = (ChangeTextView) mIndicators.get(position);
                left.setmDirection(ChangeTextView.Direction.LEFT_TO_RIGHT);
                left.setmCurrentProgress(1 - positionOffset);

                if (mIndicators.size() == (position + 1)) {//处理下标越界
                    return;
                }
                ChangeTextView right = (ChangeTextView) mIndicators.get(position + 1);
                right.setmDirection(ChangeTextView.Direction.RIGHT_TO_LEFT);
                right.setmCurrentProgress(positionOffset);


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            //动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ChangeTextView changeTextView = new ChangeTextView(this);
            changeTextView.setText(items[i]);
            changeTextView.setmChangerColor(Color.RED);
            changeTextView.setTextSize(20);
            changeTextView.setLayoutParams(params);
            //加入到Linearlayout容器中
            llHeaderLayout.addView(changeTextView);
            //加入到集合
            mIndicators.add(changeTextView);

        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

}
