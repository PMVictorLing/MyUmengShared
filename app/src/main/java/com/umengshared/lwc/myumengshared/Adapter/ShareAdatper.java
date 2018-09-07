package com.umengshared.lwc.myumengshared.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umengshared.lwc.myumengshared.R;


/**
 * Created by Administrator on 2016/1/7.
 */
public class ShareAdatper extends BaseAdapter {

    private Context context;
    private String[] contentArray;
    private int[] iconArray={R.drawable.friend,R.drawable.wiexin,R.drawable.microblog
            ,R.drawable.interspace};
//    ,R.drawable.microblog
    public ShareAdatper(Context context, String[] contentArray) {
        this.context = context;
        this.contentArray = contentArray;
    }


    @Override
    public int getCount() {
        return contentArray.length;
    }

    @Override
    public Object getItem(int position) {
        return contentArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holder = null;
        if (convertView == null) {
            holder = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share, null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_share_icon);
            holder.tvContext = (TextView) convertView.findViewById(R.id.item_share_content);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }
        holder.ivIcon.setBackgroundResource(iconArray[position]);
        holder.tvContext.setText(contentArray[position]);
        return convertView;
    }

    class HolderView {
        ImageView ivIcon;
        TextView tvContext;
    }
}
