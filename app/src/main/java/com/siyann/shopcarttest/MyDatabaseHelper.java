package com.siyann.shopcarttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by szjdj on 2017-05-02.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    /**
     * 创建数据库
     */
    public static final String CREATE_SHOP="create table vetegable("
            +"id Integer primary key autoincrement,"
            +"imageurl text"
            +"ve_name text,"
            +"ve_content text"
            +"ve_price real)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOP);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
