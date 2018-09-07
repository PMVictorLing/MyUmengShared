package com.umengshared.lwc.myumengshared.activity.definedview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 仿6.0 QQ侧滑菜单
 */

public class MenuMainActivity extends BaseActivity {

    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_click_test)
    TextView tvClickTest;
    @BindView(R.id.qq_sliding_menu)
    QQSlidingMenu qqSlidingMenu;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_menu_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @OnClick({R.id.iv_other, R.id.tv_exit, R.id.iv_back, R.id.tv_click_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_other:
                break;
            case R.id.tv_exit:
                break;
            case R.id.iv_back://打开菜单
                qqSlidingMenu.openMenu();
                break;
            case R.id.tv_click_test://测试点击
                MyToast.showToast("测试点击");
                break;
        }
    }

}
