package com.umengshared.lwc.myumengshared.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.umengshared.lwc.myumengshared.Tools.MyLog;
import com.umengshared.lwc.myumengshared.Tools.MyToast;

/**
 * 打开应用时创建 db数据库
 * <p>
 * Created by lingwancai on
 * 2018/8/9 15:08
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDataBaseHelper";

    //创建一张book表
    public static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "bookname text" + ")";

    //种类表
    public static final String CREATE_CATEGER = "create table Categer ("+"" +
            "id integer primary key autoincrement,"
            +"name text," +
            "decription text"+")";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGER);
        MyLog.e(TAG, "数据创建成功");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Categer");
        onCreate(db);
        MyLog.e(TAG, "数据库升级成功");

    }
}
