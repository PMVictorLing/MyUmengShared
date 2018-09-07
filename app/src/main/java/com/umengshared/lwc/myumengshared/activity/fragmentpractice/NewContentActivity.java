package com.umengshared.lwc.myumengshared.activity.fragmentpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

public class NewContentActivity extends BaseActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_new_n;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {
        NewsContentFragment fragment = (NewsContentFragment) this.getSupportFragmentManager().findFragmentById(R.id.id_news_content_fragment);
        fragment.onfresh(getIntent().getStringExtra("newtitle")+"",getIntent().getStringExtra("newscontent")+"");

    }

    public static void actionStartActivit(Context context,String title,String content){
        Intent intent = new Intent(context,NewContentActivity.class);
        intent.putExtra("newtitle",title);
        intent.putExtra("newscontent",content);
        context.startActivity(intent);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
