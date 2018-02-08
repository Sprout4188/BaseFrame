package com.sprout.frame.baseframe.sp;


import com.sprout.frame.baseframe.sp.items.LongPrefItem;
import com.sprout.frame.baseframe.sp.items.StringPrefItem;

/**
 * Create by Sprout at 2017/8/15
 */
public class CommonSP extends BaseSP {

    public static CommonSP instance = new CommonSP();

    private CommonSP() {
        super("sirui_common");  //定义SP文件名
    }

    //用于保存用户消息的流水号
    public static final LongPrefItem serialNumber = new LongPrefItem(instance, "messageSerialNumber", 1);

    //保存当前订单id
    public static final StringPrefItem orderId = new StringPrefItem(instance, "orderId", "");

    @Override
    public void clear() {
        super.clear();
        serialNumber.setValue(0L);
        orderId.setValue("");
    }
}