package com.sprout.frame.baseframe.sp.items;

import com.sprout.frame.baseframe.sp.BaseSP;

/**
 * Create by Sprout at 2017/8/15
 */
public class IntPrefItem extends BasePrefItem<Integer> {

    public IntPrefItem(BaseSP sp, String key, int defaultValue) {
        super(sp, key, defaultValue);
    }

    @Override
    public Integer getValue() {
        return sp.getIntItem(key, value);
    }

    @Override
    public void setValue(Integer value) {
        sp.setIntItem(key, value);
    }
}
