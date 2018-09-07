package com.umengshared.lwc.myumengshared.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;

import java.util.ArrayList;


/**
 * @Description: PopupwindownUtils
 * @Author: lingwancai
 * @Time: 2018/8/20 16:35
 */

public class PopupwindownUtils {

    /**
     * 弹框工具
     *
     * @param context
     * @param contentView
     * @param listener
     * @return
     */
    public static PopupWindow showPops(final Context context, final Activity activity, View contentView, final OnPopupWindowListener listener, final ArrayList<String> listData,
                                      int causeTab) {
        final PopupWindow mPopupWindow = new PopupWindow(context);
        //设置宽度
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mPopupWindow.setWidth(metrics.widthPixels);
        // 设置布局文件
        mPopupWindow.setContentView(LayoutInflater.from(context).inflate(R.layout.item_order_popupwindow, null));
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_order_enter);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        // 设置pop关闭监听，用于改变背景透明度
        setBackgroundAlpha(activity, 0.5f);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                setBackgroundAlpha(activity, 1.0f);
            }
        });

        View mContentView = mPopupWindow.getContentView();
        ListView mlistView = (ListView) mContentView.findViewById(R.id.ry_listview);
        final ArrayAdapter mArrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listData);
        mlistView.setAdapter(mArrayAdapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.OnClickListener(listData.get(position));
                mArrayAdapter.notifyDataSetChanged();
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
            }
        });

        ((TextView) mContentView.findViewById(R.id.tv_order_title)).setText("请选择:");
        //取消
        mContentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
            }
        });
        return mPopupWindow;
    }

    public OnPopupWindowListener listener;

    public interface OnPopupWindowListener {
        void OnClickListener(Object obj);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = (context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (context).getWindow().setAttributes(lp);
    }

}
