package com.umengshared.lwc.myumengshared.activity;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.base.BaseActivity;

import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: JavaCallJsActivity java调用js
 * @Author: lingwancai
 * @Time: 2018/7/17 17:29
 */

public class JavaCallJsActivity extends BaseActivity {

    private static final String TAG = "JavaCallJsActivity";
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.bt_click)
    Button btClick;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    private String startUrl = "";

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_java_call_js;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

        tvTitleName.setText("JavaCallJs");

        WebView webView = findViewById(R.id.web_view);

        WebSettings mwebSettings = webView.getSettings();
        // 设置与Js交互的权限
        mwebSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        mwebSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mwebSettings.setDomStorageEnabled(true);
        mwebSettings.setAllowFileAccess(true);
        mwebSettings.setBlockNetworkLoads(false);

        //js 调用 android（js与android交互）
        webView.addJavascriptInterface(new JStoAndroid(), "test");

//        webView.loadUrl("file:///android_asset/showjs.html");

        /**
         * webView 加载的时候  会跳到手机自带的浏览器
         * 给自己定义个WebViewClient目标网页仍然在当前webview中显示，而不是打开系统浏览器
         *
         */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                startUrl = url;
                MyLog.e(TAG, "onPageStarted startUrl=" + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*view.loadUrl(url);
                return true;*/
                //回退到原生goback(） 遭遇重定向 -- 永远返回不到原生的界面的尴尬
                MyLog.e(TAG, "shouldOverrideUrlLoading startUrl=" + url);
                if (startUrl != null && startUrl.equals(url)) {
                    view.loadUrl(url);
                } else {
                    //系统处理
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return true;
            }
        });
        webView.loadUrl(startUrl = "http://c.xingdianbao.com/help");

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(JavaCallJsActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });

    }

    @Override
    public void doBusiness() {

//        new AsyncTask<String,Integer,Integer>().execute();
//        new IntentService();
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @OnClick(R.id.bt_click)
    public void onClick() {
//        webView.loadUrl("javascript:javacalljs()");

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl("javascript:javacalljs()");
//            }
//        });
        // 通过Handler发送消息
        webView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                // 注意调用的JS方法名要对应上
                // 调用javascript的callJS()方法
//                webView.loadUrl("javascript:callJS()");

                //获取android版本
                int version = Build.VERSION.SDK_INT;
                //android 4.4
                if (version < 18) {
                    webView.loadUrl("javascript:callJSArg('android界面的参数12')");
                } else {
                    webView.evaluateJavascript("javascript:callJSArg('android界面的参数13')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            MyToast.showToast("回调value=" + value);

                        }
                    });
                }
            }

        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
