package com.sprout.frame.baseframe.httpAction;

import com.google.gson.Gson;
import com.sprout.frame.baseframe.datamodel.Customer;
import com.sprout.frame.baseframe.entity.LoginEntity;
import com.sprout.frame.baseframe.global.Api;
import com.sprout.frame.baseframe.http.HttpAction;
import com.sprout.frame.baseframe.http.HttpResult;
import com.sprout.frame.baseframe.sp.AppSP;
import com.sprout.frame.baseframe.utils.coder.CoderUtil;

/**
 * Create by Sprout at 2017/8/15
 * 登录
 */
public class LoginAction extends HttpAction<LoginEntity> {

    private String encryUsername;
    private String encryPassword;

    public LoginAction() {
        super(Api.API_LOGIN);
    }

    public LoginAction para(String username, String password) {
        //加密用户名和密码
        encryUsername = CoderUtil.encode(username);
        encryPassword = CoderUtil.encode(password);
        add("username", encryUsername);
        add("password", encryPassword);
        return this;
    }

    @Override
    public LoginEntity decodeModel(String response, HttpResult<LoginEntity> result, Gson gson) {
        if (result.isSucc()) {
            //将加密后的用户名和密码存在SP中
            AppSP.username.setValue(encryUsername);
            AppSP.password.setValue(encryPassword);

            //将加密后的用户名和密码作为input1和input2存储在数据模型Customer中, 用于session保活, 原理如下:
            //本来第一次请求网络时, 服务器创建一个session会话, 返回一个cookie
            //手机收到响应后持久化存储该cookie, 以后每次请求都上传该cookie, 即可保证session不会被销毁

            //第一次请求网络时, 上传input1和input2, 服务器创建一个session会话, 并与input1和input2关联, 即相当于token
            //当在指定时间内手机端没有新的请求时, 则服务器将session挂起, 当下次请求时, 服务器先从挂起的session列表查询是否
            //有与input1和input2关联的session, 若有则激活, 若无才重新创建session
            Customer.instance.isLogin = true;
            Customer.instance.input1 = encryUsername;
            Customer.instance.input2 = encryPassword;
        }
        return gson.fromJson(response, LoginEntity.class);
    }
}
