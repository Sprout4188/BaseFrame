package com.sprout.frame.baseframe.lifecycle;

import android.view.View;

import com.sprout.frame.baseframe.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by Sprout at 2017/8/15
 */
public class ButterKnifeLifecycle implements ILifecycle {

    private Object target;
    private BaseActivity activity;
    private View view;
    private Unbinder unbinder;

    public ButterKnifeLifecycle(BaseActivity target) {
        this.activity = target;
        unbinder = ButterKnife.bind(activity);
    }

    public ButterKnifeLifecycle(Object obj, View view) {
        target = obj;
        this.view = view;
        unbinder = ButterKnife.bind(target, view);
    }

    @Override
    public void onCreate() {}

    @Override
    public void onShow() {}

    @Override
    public void onHide() {}

    @Override
    public void onDestory() {
        unbinder.unbind();
    }
}
