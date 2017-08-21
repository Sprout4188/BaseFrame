package com.sprout.frame.baseframe.lifecycle;

/**
 * Create by Sprout at 2017/8/15
 */
public interface ILifecycle {

    /**
     * Activity走onCreate()时回调
     */
    void onCreate();

    /**
     * Activity走onResume()时回调
     */
    void onShow();

    /**
     * Activity走onPause()时回调
     */
    void onHide();

    /**
     * Activity走onDestory()时回调
     */
    void onDestory();
}
