package com.umengshared.lwc.myumengshared.crashtools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import com.umengshared.lwc.myumengshared.Tools.Config;
import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常捕获类
 * <p>
 * Created by lingwancai on
 * 2018/8/16 09:30
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    //crash存储路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashHandlerLogtrace/logs/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";
    private Thread.UncaughtExceptionHandler mDefaultUncaughtException;
    private Context mContext;


    /**
     * 单利获取对象
     */
    private CrashHandler() {
    }

    private static CrashHandler mCrashHandler = new CrashHandler();

    public static CrashHandler getInstanse() {
        return mCrashHandler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mDefaultUncaughtException = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是关键的方法，当程序中有未捕获的异常，系统会自动调用uncaughtException（）方法
     * Thread为出现未捕获异常的线程，Throwable为未捕获的异常，通过它我们就可以得到异常信息
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //导出异常信息到sd卡中
        dumpExceptionToSDCard(e);
        //上传到服务器
        uploadExceptionToServer();

        e.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则由自己结束程序
        if (mDefaultUncaughtException != null) {
            mDefaultUncaughtException.uncaughtException(t, e);
        } else {
            MyToast.showToast("程序出现异常了！");
            Process.killProcess(Process.myPid());
        }

    }

    /**
     * 上传到服务器
     */
    private void uploadExceptionToServer() {
        MyLog.e(TAG, "upload Exception To Server");
    }

    /**
     * 导出异常信息到sd卡中
     *
     * @param e
     */
    private void dumpExceptionToSDCard(Throwable e) {
        //如果SD卡不存在或无法使用，则无法把信息导入到SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (Config.DEBUG) {
                MyLog.e(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }


        try {
            //创建crash目录
            File dir = new File(PATH);
            if (!dir.exists()) {
                //dir.mkdir();错误
                dir.mkdirs();
            }
            //创建crash文件
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            e.printStackTrace(pw);
            pw.close();
        } catch (IOException e1) {
            MyLog.e(TAG, "dump crash info failed");
            e1.printStackTrace();
        }
    }

    /**
     * 打印手设备信息
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            //app 版本
            pw.print("App Version: ");
            pw.print(pi.versionName);
            pw.print("_");
            pw.println(pi.versionCode);

            //android 版本
            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);

            //手机制造商
            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);

            //手机型号
            pw.print("Model: ");
            pw.println(Build.MODEL);

            //CPU 架构
            pw.print("CPU ABI: ");
            pw.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            MyLog.e(TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

}
