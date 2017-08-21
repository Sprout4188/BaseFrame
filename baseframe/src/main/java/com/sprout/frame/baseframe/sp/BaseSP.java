package com.sprout.frame.baseframe.sp;


import android.content.Context;
import android.content.SharedPreferences;

import com.sprout.frame.baseframe.base.App;


/**
 * Create by Sprout at 2017/8/15
 */
public class BaseSP {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    protected BaseSP(String name) {
        pref = App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setStringItem(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String getStringItem(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    public void setIntItem(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public int getIntItem(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public void setLongItem(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public long getLongItem(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public void setBooleanItem(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public Boolean getBooleanItem(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public void setDoubleItem(String key, double value) {
        editor.putFloat(key, (float) value).commit();
    }

    public double getDoubleItem(String key, double defaultValue) {
        return pref.getFloat(key, (float) defaultValue);
    }

    /**
     * 清空本SP
     */
    public void clear() {
        editor.clear().commit();
    }
}
