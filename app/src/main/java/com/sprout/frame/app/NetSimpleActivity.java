package com.sprout.frame.app;

import android.os.Bundle;

import com.sprout.frame.baseframe.base.BaseActivity;
import com.sprout.frame.baseframe.entity.LoginEntity;
import com.sprout.frame.baseframe.http.HttpResult;
import com.sprout.frame.baseframe.http.InterceptorUtil;
import com.sprout.frame.baseframe.httpAction.LoginAction;
import com.sprout.frame.baseframe.widgets.progressbar.CircleProgressBar;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 网络请求示例
 */
public class NetSimpleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_net_simple);
        setTitle("网络请求示例");
    }

    @OnClick(R.id.btRequestNet)
    public void clickBt1() {
        new LoginAction()
                .addPara("18523641110", "123456")
                .addInterceptor(InterceptorUtil.buildCircleProgressbar(new CircleProgressBar(this, "加载中")))
                .onSuccess(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult result) {
                        LoginEntity entity = (LoginEntity) result.getEntity();
                        /**
                         * 特别注意, 如果要在本回调中去更新界面, 要做非空判断.
                         * 防止网络较差的情况下, 回调返回时, 用户已经退出界面了,
                         * 这时再去更新界面, 会出现空指针异常崩溃
                         */
                    }
                })
                .onFail(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult result) {
                        /**
                         * 特别注意, 如果要在本回调中去更新界面, 要做非空判断.
                         * 防止网络较差的情况下, 回调返回时, 用户已经退出界面了,
                         * 这时再去更新界面, 会出现空指针异常崩溃
                         */
                    }
                })
                .onFailToast(this).execute();
    }
}
