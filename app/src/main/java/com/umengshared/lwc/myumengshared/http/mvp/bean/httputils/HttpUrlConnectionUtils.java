package com.umengshared.lwc.myumengshared.http.mvp.bean.httputils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 原生网络请求
 * <p>
 * Created by lingwancai on
 * 2018/7/31 14:38
 */
public class HttpUrlConnectionUtils {

    /**
     * @Description: HttpUrlConnectionUtils get请求
     * @Author: lingwancai
     * @Time: 2018/7/31 14:40
     */
    public static void sendGetRequest(final String sendUrl, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(sendUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    //读取数据
                    InputStream in = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    bufferedReader = new BufferedReader(reader);
                    StringBuilder builderString = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        builderString.append(line);
                    }
                    //成功回调
                    if (listener != null) {
                        listener.onFinish(builderString.toString());
                    }
                } catch (Exception e) {
                    //失败回调
                    if (listener != null) {
                        listener.onFailer(e);
                    }
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
        }).start();
    }


    /**
     * post 请求
     *
     * @param sendUrl
     * @param listener
     */
    public static void sendPostRequest(final String sendUrl, final String params, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                DataOutputStream dataOutput = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(sendUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    //写入数据到服务器
                    OutputStream out = connection.getOutputStream();
                    dataOutput = new DataOutputStream(out);
                    dataOutput.writeBytes(params);

                    //获取相应数据
                    InputStream in = connection.getInputStream();
                    InputStreamReader input = new InputStreamReader(in);
                    reader = new BufferedReader(input);
                    StringBuilder respose = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        respose.append(line);
                    }
                    //成功回调
                    if (listener != null) {
                        listener.onFinish(respose.toString());
                    }
                } catch (Exception e) {
                    //失败回调
                    if (listener != null) {
                        listener.onFailer(e);
                    }
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

                }
            }
        }).start();
    }
}
