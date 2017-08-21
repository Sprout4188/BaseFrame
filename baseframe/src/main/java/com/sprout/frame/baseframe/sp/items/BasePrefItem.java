package com.sprout.frame.baseframe.sp.items;

import com.sprout.frame.baseframe.sp.BaseSP;

/**
 * Create by Sprout at 2017/8/15
 */
public abstract class BasePrefItem<T> {

    protected boolean loaded = false;
    protected T value = null;
    protected String key = null;
    protected BaseSP sp = null;

    protected BasePrefItem(BaseSP sp, String key, T defaultValue) {
        this.sp = sp;
        this.key = key;
        value = defaultValue;
        value = getValue();
    }

    public abstract T getValue();

    public abstract void setValue(T value);
}
