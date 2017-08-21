package com.sprout.frame.baseframe.dataModel;

/**
 * Create by Sprout at 2017/8/15
 */
public class Customer {

    public static Customer instance = new Customer();

    public static void clear(){
        instance = new Customer();
    }

    public String input1;
    public String input2;
    public boolean isLogin;    //是否登录
    public String headUrl;     //头像URL
}
