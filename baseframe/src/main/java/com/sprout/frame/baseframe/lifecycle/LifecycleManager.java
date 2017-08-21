package com.sprout.frame.baseframe.lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Sprout at 2017/8/15
 */
public class LifecycleManager implements ILifecycle {

    List<ILifecycle> list = new ArrayList<>();

    public void add(ILifecycle lifecycle){
        list.add(lifecycle);
    }

    @Override
    public void onCreate() {
        for(ILifecycle lifecycle:list){
            lifecycle.onCreate();
        }
    }

    @Override
    public void onShow() {
        for(ILifecycle lifecycle:list){
            lifecycle.onShow();
        }
    }

    @Override
    public void onHide() {
        for(ILifecycle lifecycle:list){
            lifecycle.onHide();
        }
    }

    @Override
    public void onDestory() {
        for(ILifecycle lifecycle:list){
            lifecycle.onDestory();
        }
    }
}
