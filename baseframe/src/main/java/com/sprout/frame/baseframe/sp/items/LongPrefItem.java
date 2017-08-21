package com.sprout.frame.baseframe.sp.items;

import com.sprout.frame.baseframe.sp.BaseSP;

/**
 * Create by Sprout at 2017/8/15
 */
public class LongPrefItem extends BasePrefItem<Long> {

    public LongPrefItem(BaseSP sp, String key, long defaultValue) {
        super(sp, key, defaultValue);
    }

    @Override
    public Long getValue() {
        return sp.getLongItem(key, value);
    }

    @Override
    public void setValue(Long value) {
        sp.setLongItem(key, value);
    }
}
