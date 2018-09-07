package com.umengshared.lwc.myumengshared.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.base.BaseActivity;
import com.umengshared.lwc.myumengshared.umeng.ShareTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: SendActivity 分享页
 * @Author: lingwancai
 * @Time: 2018/8/2 8:55
 */

public class SendActivity extends BaseActivity {

    private static final int INT_REQUEST_CODE = 1001;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.iv_shared)
    ImageView ivShared;
    private View llLayout;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == INT_REQUEST_CODE) {
            ShareTools share = new ShareTools(SendActivity.this, llLayout, "http://www.baidu.com", "title测试");
            share.init();
        } else {
            MyToast.showToast("请申请权限");
        }

    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(SendActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(SendActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SendActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_send;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvTitleName.setText("分享");
        llLayout = findViewById(R.id.ll_layout);

        findViewById(R.id.iv_shared).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
//                        {
//                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
//                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.DOUBAN
//                        };
//                new ShareAction(SendActivity.this).setDisplayList( displaylist )
//                        .withText("呵呵")
//                        .withTitle("title")
//                        .withTargetUrl("http://www.baidu.com")
//                        .withMedia(new UMImage(SendActivity.this, R.mipmap.ic_launcher))
//                        .setListenerList(umShareListener)
//                        .open();

                //权限申请
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(SendActivity.this, mPermissionList, INT_REQUEST_CODE);
                } else {
                    ShareTools share = new ShareTools(SendActivity.this, llLayout, "http://www.baidu.com", "title");
                    share.init();
                }
            }
        });

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
