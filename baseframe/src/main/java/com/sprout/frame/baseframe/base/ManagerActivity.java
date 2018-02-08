package com.sprout.frame.baseframe.base;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Sprout at 2017/8/15
 */
public class ManagerActivity {

    private static ManagerActivity instance = new ManagerActivity();
    private List<Activity> mLists = new ArrayList<>();

    private ManagerActivity() {
    }

    public synchronized static ManagerActivity getInstance() {
        return instance;
    }

    /**
     * 往集合中添加指定Activity
     *
     * @param pActivity 需要添加的Activity
     */
    public void addActivity(Activity pActivity) {
        if (pActivity != null) {
            mLists.add(pActivity);
        }
    }

    /**
     * 从集合中删除指定Activity
     *
     * @param pActivity 需要删除的Activity
     */
    public void removeActivity(Activity pActivity) {
        if (pActivity != null) {
            if (mLists.contains(pActivity)) {
                mLists.remove(pActivity);
                pActivity.finish();
                pActivity = null;
            }
        }
    }

    /**
     * 从集合中删除指定Activity
     *
     * @param simpleName 需要删除的Activity的简单名称
     */
    public void removeActivity(String simpleName) {
        if (!TextUtils.isEmpty(simpleName)) {
            for (Activity activity : mLists) {
                if (activity.getClass().getSimpleName().equals(simpleName)) {
                    mLists.remove(activity);
                    activity.finish();
                    activity = null;
                }
            }
        }
    }

    /**
     * 从集合中删除栈顶的Activity
     */
    public void popActivity() {
        Activity activity = mLists.get(mLists.size() - 1);
        removeActivity(activity);
    }

    /**
     * 获取集合中Activity的数量
     */
    public int getNum() {
        return mLists.size();
    }

    /**
     * 完全删除集合中的所有Activity
     */
    public void finishActivity() {
        if (mLists != null && mLists.size() >= 0) {
            for (Activity pActivity : mLists) {
                pActivity.finish();
                pActivity = null;
            }
        }
    }
}
