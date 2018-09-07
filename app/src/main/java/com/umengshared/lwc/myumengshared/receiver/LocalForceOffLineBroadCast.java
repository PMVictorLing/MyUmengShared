/*
package com.umengshared.lwc.myumengshared.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.umengshared.lwc.myumengshared.Tools.ActvityCollecter;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.UmengSharedActivity;

*/
/**
 * 强制线下广播
 *//*

public class LocalForceOffLineBroadCast extends BroadcastReceiver {

    private static final String TAG = "LocalForceOffLineBroadCast";

    @Override
    public void onReceive(final Context context, Intent intent) {
        MyLog.e(TAG,"强制线下广播");
        AlertDialog.Builder b = new AlertDialog.Builder(context);//context存在问题，需解决
        b.setTitle("警告");
        b.setMessage("由于其他原因，您被强制下线！");
        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActvityCollecter.clearAllActivity();
                context.startActivity(new Intent(context,UmengSharedActivity.class));
                dialog.dismiss();
            }
        });
        b.setCancelable(false);
        b.show();
    }
}
*/
