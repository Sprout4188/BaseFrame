package com.sprout.frame.baseframe.widgets.progressbar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.sprout.frame.baseframe.utils.ActivityUtil;


/**
 * Create by Sprout at 2017/8/15
 */
public abstract class BaseProgressbar {

    public static boolean isShowing = false;
    /**
     * 对应界面的进度条布局
     */
    protected View baseView;
    protected Activity mActivity;

    public BaseProgressbar(Activity mActivity) {
        this.mActivity = mActivity;
        baseView = LayoutInflater.from(mActivity).inflate(loadXml(), null);
    }

    /**
     * 显示进度条
     */
    public void show() {
        isShowing = true;
        ActivityUtil.getRootView(mActivity).addView(baseView);
    }

    /**
     * 隐藏进度条
     */
    public void disMiss() {
        isShowing = false;
        ActivityUtil.getRootView(mActivity).removeView(baseView);
        onDismiss();
    }

    /**
     * 加载进度条布局
     */
    public abstract int loadXml();

    /**
     * 进度条消失时的回调
     */
    public abstract void onDismiss();
}
