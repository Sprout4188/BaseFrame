package com.sprout.frame.baseframe.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.sprout.frame.baseframe.base.App;
import com.sprout.frame.baseframe.database.annotation.Column;
import com.sprout.frame.baseframe.database.annotation.Table;
import com.sprout.frame.baseframe.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sprout on 2017/8/24
 */

public abstract class BaseDao<T> {

    private Class<T> clazz;
    private DBHelper helper;
    protected String tableName; //表名

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
        helper = createHelperAndTable();
    }

    /**
     * 创建SQLiteOpenHelper并建表
     */
    private DBHelper createHelperAndTable() {
        //获取表名
        tableName = clazz.getAnnotation(Table.class).name();
        String sql = getSql4CreateTable();
        return new DBHelper(App.getContext(), Constant_DB.DB_NAME, null, Constant_DB.DB_VERSION, tableName, sql);
    }

    /**
     * 组拼建表sql语句
     */
    @NonNull
    private String getSql4CreateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(tableName);
        sb.append(" ( ");

        boolean hasColumn = false;
        int primaryKeyCount = 0;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column == null) continue;

            //是否主键
            boolean isPrimaryKey = column.primaryKey();
            if (isPrimaryKey) primaryKeyCount++;
            //列名
            String columnName = column.name();
            sb.append(columnName);
            hasColumn = true;
            //判断主键类型合法性
            if (isPrimaryKey && field.getType() != int.class)
                throw new IllegalArgumentException("主键修饰的类型只能是int, 当前列名: " + field.getName() + "的类型为: " + field.getType().getSimpleName());

            if (i == fields.length - 1)
                sb.append(isPrimaryKey ? " integer primary key autoincrement" : " varchar(40)");
            else
                sb.append(isPrimaryKey ? " integer primary key autoincrement, " : " varchar(40), ");
        }
        if (primaryKeyCount > 1)
            throw new IllegalArgumentException("数据库表: " + tableName + " 最多只能有一个主键, 当前有: " + primaryKeyCount + " 个");
        if (!hasColumn) throw new IllegalArgumentException("数据库表: " + tableName + " 至少需要有一个列名");

        sb.append(")");
        LogUtil.debug("DB", "建表sql: " + sb.toString());
        return sb.toString();
    }

    /**
     * 获取数据库
     */
    public SQLiteDatabase getDB() {
        return helper.getWritableDatabase();
    }

    /**
     * 获取表中的数据总数
     */
    public long getCount() {
        SQLiteDatabase db = getDB();
        Cursor cursor = db.rawQuery("select count(*) from " + tableName, null);
        cursor.moveToFirst();
        long reslut = cursor.getLong(0);
        cursor.close();
        db.close();
        return reslut;
    }

    /**
     * 增(单行)
     *
     * @return 返回该表被影响的行数
     */
    public abstract long insert(T t);

    /**
     * 增(多行)
     *
     * @return 返回该表被影响的行数
     */
    public long insert(List<T> list) {
        if (list == null || list.size() == 0) return 0;
        long count = 0;
        for (T t : list) count += insert(t);
        return count;
    }

    /**
     * 删(单行)
     *
     * @return 返回该表被影响的行数
     */
    public abstract int delete(String rowID);

    /**
     * 删(多行)
     *
     * @return 返回该表被影响的行数
     */
    public int delete(String... rowID) {
        if (rowID == null || rowID.length == 0) return 0;
        int count = 0;
        for (String id : rowID) count += delete(id);
        return count;
    }

    /**
     * 删(所有)
     *
     * @return 返回该表被影响的行数
     */
    public int deleteAll() {
        SQLiteDatabase db = getDB();
        int count = db.delete(tableName, null, null);
        db.close();
        return count;
    }

    /**
     * 改(单行)
     *
     * @return 返回该表被影响的行数
     */
    public abstract int update(T t);

    /**
     * 改(多行)
     *
     * @return 返回该表被影响的行数
     */
    public int update(List<T> list) {
        if (list == null || list.size() == 0) return 0;
        int count = 0;
        for (T t : list) count += update(t);
        return count;
    }

    /**
     * 查(单行)
     */
    public abstract T query(String rowID);

    /**
     * 查(多行)
     */
    public List<T> query(String... rowID) {
        if (rowID == null || rowID.length == 0) return null;
        List<T> list = new ArrayList<>();
        for (String id : rowID) list.add(query(id));
        return list;
    }

    /**
     * 查(所有)
     */
    public abstract List<T> queryAll();
}
