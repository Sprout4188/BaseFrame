package com.sprout.frame.baseframe.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Create by Sprout at 2017/8/15
 */
public class EventBusUtil {

    /**
     * 发送事件
     */
    public static void post(Object obj){
        EventBus.getDefault().post( obj );
    }

    /**
     * 注册
     */
    public static void reg(Object obj){
        EventBus.getDefault().register( obj );
    }

    /**
     * 反注册
     */
    public static void unReg(Object obj){
        EventBus.getDefault().unregister( obj );
    }

    /**
     * 判断是否已经注册了Eventbus
     */
    public static boolean isReg( Object obj  ){
        return EventBus.getDefault().isRegistered( obj );
    }
}
