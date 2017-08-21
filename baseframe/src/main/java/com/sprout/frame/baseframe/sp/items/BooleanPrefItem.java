package com.sprout.frame.baseframe.sp.items;

import com.sprout.frame.baseframe.sp.BaseSP;

/**
 * Create by Sprout at 2017/8/15
 */
public class BooleanPrefItem extends BasePrefItem<Boolean> {

    public BooleanPrefItem(BaseSP sp, String key, boolean defaultValue) {
        super(sp, key, defaultValue);
    }

    @Override
    public Boolean getValue() {
        Boolean r = sp.getBooleanItem(key, value);
        return r != null ? r : false;
    }

    @Override
    public void setValue(Boolean value) {
        sp.setBooleanItem(key, value);
    }
}
