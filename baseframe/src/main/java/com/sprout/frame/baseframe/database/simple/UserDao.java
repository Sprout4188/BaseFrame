package com.sprout.frame.baseframe.database.simple;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sprout.frame.baseframe.database.BaseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sprout on 2017/8/23
 */

public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    /**
     * 分页查找数据
     * @param offset    跳过多少条数据
     * @param maxResult 每页多少条数据
     */
    public List<User> getScrollData(String orderby, int offset, int maxResult) {
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("select * from " + tableName + " order by ? asc limit ?,?", new String[]{orderby, String.valueOf(offset), String.valueOf(maxResult)});

        List<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            int uid2 = cursor.getInt(cursor.getColumnIndex("_id"));
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            String uaddress = cursor.getString(cursor.getColumnIndex("addr"));
            User user = new User();
            user.setUid(uid2);
            user.setUname(uname);
            user.setUaddress(uaddress);
            users.add(user);
        }
        cursor.close();
        db.close();
        return users;
    }

    @Override
    public long insert(User user) {
        SQLiteDatabase db = getDB();
        ContentValues values = new ContentValues();
        values.put("_id", user.getUid());
        values.put("name", user.getUname());
        values.put("addr", user.getUaddress());
        long count = db.insert(tableName, null, values);
        db.close();
        return count;
    }

    @Override
    public int delete(String rowID) {
        SQLiteDatabase db = getDB();
        int count = db.delete(tableName, "_id = ?", new String[]{rowID});
        db.close();
        return count;
    }

    @Override
    public int update(User user) {
        SQLiteDatabase db = getDB();
        ContentValues values = new ContentValues();
        values.put("name", user.getUname());
        int count = db.update(tableName, values, "_id = ?", new String[]{String.valueOf(user.getUid())});
        db.close();
        return count;
    }

    @Override
    public User query(String rowID) {
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("select * from " + tableName + " where _id = ?", new String[]{rowID});
        if (cursor.moveToFirst()) {
            int uid = cursor.getInt(cursor.getColumnIndex("_id"));
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            String uaddress = cursor.getString(cursor.getColumnIndex("addr"));
            User user = new User();
            user.setUid(uid);
            user.setUname(uname);
            user.setUaddress(uaddress);
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public List<User> queryAll() {
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);

        List<User> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int uid = cursor.getInt(cursor.getColumnIndex("_id"));
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            String uaddress = cursor.getString(cursor.getColumnIndex("addr"));
            User user = new User();
            user.setUid(uid);
            user.setUname(uname);
            user.setUaddress(uaddress);
            list.add(user);
        }
        cursor.close();
        db.close();
        return list;
    }
}
