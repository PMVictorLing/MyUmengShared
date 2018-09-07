package com.umengshared.lwc.myumengshared.umeng;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.umengshared.lwc.myumengshared.Adapter.ShareAdatper;
import com.umengshared.lwc.myumengshared.R;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;
import com.umengshared.lwc.myumengshared.Tools.Tool;

/**
 * @Description:  ShareTools
 * @Author:  lingwancai
 * @Time:  2018/7/30 16:12
 */

public class ShareTools implements View.OnClickListener {
    private static final String TAG="ShareTools";
    private Activity activity;
    private View view;
    private PopupWindow popupWindow;
    private String[] contentArray, packageArray;
    private String text;
    private String content;

    public ShareTools(Activity activity, View view,String text,String content) {
        MyLog.e(TAG, "text=" + text + "==content=" + content);
        this.activity = activity;
        this.view = view;
        this.text=text;
        this.content=content;
    }

    public void init() {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_share, null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        gridView.setOnItemClickListener(OnitemClick);
        view.findViewById(R.id.share_cancel).setOnClickListener(this);
        contentArray = activity.getResources().getStringArray(R.array.share_array);
        packageArray = activity.getResources().getStringArray(R.array.share_packager_name);
        gridView.setAdapter(new ShareAdatper(activity, contentArray));
        popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Tool.setWindow(activity, 1.0f);
            }
        });
        Tool.setWindow(activity, 0.5f);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.lucency_bg));
        popupWindow.showAtLocation(this.view, Gravity.BOTTOM, 0, 0);
    }

    AdapterView.OnItemClickListener OnitemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (Tool.checkInstallation(packageArray[position])) {
                Share share;
                if (Tool.isNull(text)){
                    //分享APP
                    share = new Share(activity, "http://www.baidu.com", null,"");
                }else {
                    //分享文章
                    share = new Share(activity, text, null,content);
                }

                MyLog.e("bug","text ="+text);
                switch (position) {
                    case 0://朋友圈
                        share.initWxCircle();
                        break;
                    case 1://微信好友
                        share.initWX();
                        break;
                    case 2://新浪微博
//                        share.initQzone();
                        share.initSina();
                        break;
                    case 3://QQ 空间
//                        share.initencent();//腾讯微博
                        share.initQzone();
                        break;
//                    case 4://QQ空间
////                        share.initQQ();
//                        share.initQzone();
//                        break;
                }
//                Tools.share(activity, packageArray[position], text);
            } else {
                MyToast.showToast(String.format("未检测到%s，请先安装应用", contentArray[position]));
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (popupWindow != null && popupWindow.isShowing()) {
            Tool.setWindow(activity, 1.0f);
            popupWindow.dismiss();
        }
    }
}
