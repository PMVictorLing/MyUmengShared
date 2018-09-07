package com.umengshared.lwc.myumengshared.http;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.base.BaseActivity;
import com.umengshared.lwc.myumengshared.http.mvp.bean.BaseBean;
import com.umengshared.lwc.myumengshared.http.mvp.bean.httputils.HttpCallbackListener;
import com.umengshared.lwc.myumengshared.http.mvp.bean.httputils.HttpUrlConnectionUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description: HttpUrlConnectionTestActivity android网络请求练习
 * @Author: lingwancai
 * @Time: 2018/7/31 9:43
 */

public class HttpUrlConnectionTestActivity extends BaseActivity {

    private static final String TAG = "HttpUrlConnectionTestActivity";
    @BindView(R.id.bt_getrequest)
    Button btGetrequest;
    @BindView(R.id.bt_postrequest)
    Button btPostrequest;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_other)
    ImageView ivOther;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_http_url_connection_test;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvTitleName.setText("HttpUrlConnection request");

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @OnClick({R.id.bt_getrequest, R.id.bt_postrequest, R.id.tv_content, R.id.iv_back, R.id.iv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_getrequest://模拟请求百度网址
//                sendHttpUrlConnectionGetRequest();
//                sendOkHttpGetRequest();

                //使用封装的http
                HttpUrlConnectionUtils.sendGetRequest("https://www.baidu.com", new HttpCallbackListener() {
                    @Override
                    public void onFinish(final String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvContent.setText(response.toString());
                            }
                        });

                    }

                    @Override
                    public void onFailer(Exception e) {
                        MyToast.showToast(e.getMessage());
                    }
                });
                break;
            case R.id.bt_postrequest://提交登陆信息
//                sendHttpUrlConnectPostRequest();
//                sendOkHttpPostRequest();

                //使用封装的http
                HttpUrlConnectionUtils.sendPostRequest("http://testsupport.csyjmall.com/api/index/login",
                        "user_name=admin&password=support", new HttpCallbackListener() {
                            @Override
                            public void onFinish(final String response) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvContent.setText(response.toString());
                                    }
                                });

                            }

                            @Override
                            public void onFailer(Exception e) {
                                MyToast.showToast(e.getMessage() + "");

                            }
                        });
                break;
            case R.id.tv_content:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_other:
                break;
        }
    }

    /**
     * @Description: okhttp post请求
     * @Author: lingwancai
     * @Time: 2018/7/31 12:32
     */
    private void sendOkHttpPostRequest() {
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder().add("user_name", "admin")
                        .add("password", "support").build();
                Request request = new Request.Builder().url("http://testsupport.csyjmall.com/api/index/login").post(body).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String responsedata = response.body().toString();
                    showHttpRequest(responsedata);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @Description: 利用Gson解析
     * @Author: lingwancai
     * @Time: 2018/7/31 13:58
     */
    private void parseJsonWithGson(String responsedata) {
        Gson gson = new Gson();
        BaseBean baseBean = gson.fromJson(responsedata, BaseBean.class);
        MyLog.e(TAG, baseBean.getMessage() + "");
    }

    /**
     * @Description: okhttp get请求
     * @Author: lingwancai
     * @Time: 2018/7/31 11:39
     */
    private void sendOkHttpGetRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okClient = new OkHttpClient();
                Request request = new Request.Builder().url("https://www.baidu.com").build();
                try {
                    Response response = okClient.newCall(request).execute();
                    String responsedata = response.body().toString();
                    showHttpRequest(responsedata);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @Description: HttpUrlConnection Post请求
     * @Author: lingwancai
     * @Time: 2018/7/31 10:19
     */
    private void sendHttpUrlConnectPostRequest() {
        //开启线程请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataOutputStream dataOutput = null;
                BufferedReader reader = null;
                HttpURLConnection connection = null;
                try {
                    MyLog.e(TAG, "start");
                    URL url = new URL("http://testsupport.csyjmall.com/api/index/login");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //写入数据到服务器
                    OutputStream out = connection.getOutputStream();
                    dataOutput = new DataOutputStream(out);
                    dataOutput.writeBytes("user_name=admin&password=support");

                    //获取相应数据
                    InputStream in = connection.getInputStream();
                    InputStreamReader input = new InputStreamReader(in);
                    reader = new BufferedReader(input);
                    StringBuilder respose = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        respose.append(line);
                    }
//                    showHttpRequest(respose.toString());
                    parseJsonWithGson(respose.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dataOutput != null) {
                        try {
                            dataOutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null) {
                        connection.disconnect();
                    }
                    MyLog.e(TAG, "end");
                }

            }
        }).start();
    }

    /**
     * @Description: 发送get请求
     * @Author: lingwancai
     * @Time: 2018/7/31 9:47
     */
    private void sendHttpUrlConnectionGetRequest() {
        //开启线程来发送请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyLog.e(TAG, "start");
                BufferedReader reader = null;
                HttpURLConnection connection = null;
                try {
                    URL getUrl = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) getUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //对输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //显示数据结果
                    showHttpRequest(response.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭读取流
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (connection != null) {
                        connection.disconnect();
                    }

                    MyLog.e(TAG, "close disconnect");

                }
            }
        }).start();
    }

    /**
     * @Description: 显示数据结果
     * @Author: lingwancai
     * @Time: 2018/7/31 10:03
     */
    private void showHttpRequest(final String contexts) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(contexts);
                MyLog.e(TAG, "show");
            }
        });

    }

}
