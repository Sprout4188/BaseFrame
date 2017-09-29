package com.sprout.frame.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sprout.frame.baseframe.base.BaseActivity;
import com.sprout.frame.baseframe.event.NetworkStatusChangeEvent;
import com.sprout.frame.baseframe.http.HttpAction;
import com.sprout.frame.baseframe.lifecycle.EventBusLifecycle;
import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        // 在应用入口Activity中配置BaseURL
        HttpAction.setHost(BuildConfig.API_URL);
        addLifecycle(new EventBusLifecycle(this));
        setTitle("示例入口");
    }

    @OnClick({R.id.btTestNet, R.id.btTestDB, R.id.btTestPermission, R.id.btCapture, R.id.btSelect})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btTestNet:            //请求网络示例
                go(this, NetSimpleActivity.class);
                break;
            case R.id.btTestDB:             //数据库示例
                go(this, DBSimpleActivity.class);
                break;
            case R.id.btTestPermission:     //动态权限示例
                go(this, PermissionSimpleActivity.class);
                break;
            case R.id.btCapture:            //拍照示例
                go(this, CaptureSimpleActivity.class);
                break;
            case R.id.btSelect:             //选择照片示例
                go(this, CaptureSimpleActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetChange(NetworkStatusChangeEvent event) {
        Toasty.warning(this, "hahahaha").show();
    }

    public void go(Activity from, Class to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        startActivity(intent);
    }
}
