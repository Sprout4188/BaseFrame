package com.sprout.frame.baseframe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/**
 * Created by Sprout on 2017/8/23
 */

public class DBHelper extends SQLiteOpenHelper {

    private String tableName;       //表名
    private String createTableSql;  //建表sql语句

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String tableName, String createTableSql) {
        super(context, name, factory, version);
        this.tableName = tableName;
        this.createTableSql = createTableSql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!TextUtils.isEmpty(createTableSql)) db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table " + tableName);
            db.execSQL(createTableSql);
        }
    }
}
