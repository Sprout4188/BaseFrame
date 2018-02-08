package com.sprout.frame.baseframe.sp;

import com.sprout.frame.baseframe.sp.items.StringPrefItem;

/**
 * Create by Sprout at 2017/8/15
 * 永久SP, 即退出登录时不会清空, 除非卸载
 */
public class ForeverInfoSP extends BaseSP {

    public static ForeverInfoSP instance = new ForeverInfoSP();

    private ForeverInfoSP() {
        super("sirui_forever_info"); //定义SP文件名
    }

    //UUID
    public static final StringPrefItem uuid = new StringPrefItem(instance, "uuid", "");

    @Override
    public void clear() {
        super.clear();
        uuid.setValue("");
    }
}
