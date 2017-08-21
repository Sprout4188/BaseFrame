package com.sprout.frame.baseframe.lifecycle;

import com.sprout.frame.baseframe.utils.EventBusUtil;

/**
 * Create by Sprout at 2017/8/15
 */
public class EventBusLifecycle implements ILifecycle {

    private Object target;

    public EventBusLifecycle(Object obj){
        target = obj;
        EventBusUtil.reg(target);
    }
    @Override
    public void onCreate() {}

    @Override
    public void onShow() {}

    @Override
    public void onHide() {}

    @Override
    public void onDestory() {
        EventBusUtil.unReg(target);
    }
}
