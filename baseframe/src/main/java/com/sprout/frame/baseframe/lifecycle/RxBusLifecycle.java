package com.sprout.frame.baseframe.lifecycle;

import com.hwangjr.rxbus.RxBus;
import com.sprout.frame.baseframe.base.BaseActivity;

/**
 * Create by Sprout at 2017/8/15
 */
public class RxBusLifecycle implements ILifecycle {
    private BaseActivity bActivity;

    public RxBusLifecycle(BaseActivity target) {
        bActivity = target;
        RxBus.get().register(target);
    }

    @Override
    public void onCreate() {}

    @Override
    public void onShow() {}

    @Override
    public void onHide() {}

    @Override
    public void onDestory() {
        RxBus.get().unregister(bActivity);
    }
}
