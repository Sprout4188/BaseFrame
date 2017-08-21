package com.sprout.frame.baseframe.sp;

import com.sprout.frame.baseframe.sp.items.StringPrefItem;

/**
 * Create by Sprout at 2017/8/15
 */
public class AppSP extends BaseSP {

    public static AppSP instance = new AppSP();

    protected AppSP() {
        super("sirui_app"); //定义SP文件名
    }

    //用户名
    public static final StringPrefItem username = new StringPrefItem(instance, "username", "");

    //密码
    public static final StringPrefItem password = new StringPrefItem(instance, "password", "");
}
