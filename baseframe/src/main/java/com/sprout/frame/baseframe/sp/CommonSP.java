package com.sprout.frame.baseframe.sp;


import com.sprout.frame.baseframe.sp.items.LongPrefItem;
import com.sprout.frame.baseframe.sp.items.StringPrefItem;

/**
 * Create by Sprout at 2017/8/15
 */
public class CommonSP extends BaseSP {

    public static CommonSP instance = new CommonSP();

    CommonSP() {
        super("sirui_common");  //定义SP文件名
    }

    public static final LongPrefItem WatchDogLastExecuteTime = new LongPrefItem(instance, "lastExecuteTime", 0);

    //用于保存当前用户uuid
    public static final StringPrefItem uuid = new StringPrefItem(instance, "phoneid", "");

    //用于保存用户消息的流水号
    public static final LongPrefItem serialNumber = new LongPrefItem(instance, "messageSerialNumber", 1);

    //用于保存客服电话
    public static final StringPrefItem customerServicePhone = new StringPrefItem(instance, "custServicePhone", "");

    //保存当前订单id
    public static final StringPrefItem orderId = new StringPrefItem(instance, "orderId", "");
}