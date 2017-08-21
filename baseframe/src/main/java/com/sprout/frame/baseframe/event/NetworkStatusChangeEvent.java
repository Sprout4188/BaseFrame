package com.sprout.frame.baseframe.event;

/**
 * Create by Sprout at 2017/8/15
 */
public class NetworkStatusChangeEvent {

    public boolean isConnect = false;   //是否是连接

    private NetworkStatusChangeEvent(){
        isConnect = true;
    }

    public NetworkStatusChangeEvent(boolean isConnect ){
        this.isConnect = isConnect;
    }
}
