package com.umengshared.lwc.myumengshared;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.Tools.Tool;
import com.umengshared.lwc.myumengshared.activity.SendActivity;
import com.umengshared.lwc.myumengshared.activity.ZTNHMainActivity;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

import butterknife.BindView;

/**
 * @Description: UmengSharedActivity 登陆页
 * @Author: lingwancai
 * @Time: 2018/8/2 10:41
 */

public class UmengSharedActivity extends BaseActivity {

    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_box)
    CheckBox cbBox;
    @BindView(R.id.bt_weixin_login)
    Button btWeixinLogin;

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_umeng_shared;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        final SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        ivUser = (ImageView) findViewById(R.id.iv_user);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UmengSharedActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_weixin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tool.isNull(etAccount.getText().toString()) || Tool.isNull(etPassword.getText().toString())) {
                    MyToast.showToast("账号或密码不能为空！");
                    return;
                }
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                if (cbBox.isChecked()) {
                    editor.putBoolean("ischecked",true);
                    editor.putString("user_name",etAccount.getText().toString());
                    editor.putString("user_password",etPassword.getText().toString());
                    editor.apply();
                } else {
                    editor.putBoolean("ischecked",false);
                    editor.putString("user_name","");
                    editor.putString("user_password","");
                    editor.apply();
                }
                startActivity(new Intent(UmengSharedActivity.this, ZTNHMainActivity.class));
                MyToast.showToast("登录成功!");
                finish();
            }
        });

        //设置透明导航栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //记住账号
        boolean ischecked = mSharedPreferences.getBoolean("ischecked",false);
        if (ischecked){
            etAccount.setText(mSharedPreferences.getString("user_name",""));
            etPassword.setText(mSharedPreferences.getString("user_password",""));
            cbBox.setChecked(true);
        } else {
            etAccount.setText("");
            etPassword.setText("");
            cbBox.setChecked(false);
        }


    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
