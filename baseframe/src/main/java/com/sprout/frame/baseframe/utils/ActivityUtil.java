package com.sprout.frame.baseframe.utils;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * Create by Sprout at 2017/8/15
 */
public class ActivityUtil {

    /**
     * 获取Activity的DecorView
     */
    public static ViewGroup getRootView(Activity activity ){
        return (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }
}
