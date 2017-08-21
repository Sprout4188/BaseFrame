package com.sprout.frame.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.sprout.frame.baseframe.base.BasePermissionActivity;
import com.sprout.frame.baseframe.entity.LoginEntity;
import com.sprout.frame.baseframe.event.NetworkStatusChangeEvent;
import com.sprout.frame.baseframe.http.HttpResult;
import com.sprout.frame.baseframe.http.InterceptorUtil;
import com.sprout.frame.baseframe.httpAction.LoginAction;
import com.sprout.frame.baseframe.lifecycle.EventBusLifecycle;
import com.sprout.frame.baseframe.utils.LogUtil;
import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;
import com.sprout.frame.baseframe.widgets.progressbar.CircleProgressBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class MainActivity extends BasePermissionActivity {

    @BindView(R.id.bt2)
    Button bt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        addLifecycle(new EventBusLifecycle(this));
        setTitle("还车");
        bt2.setText("我是改变后的BT");
    }

    @OnClick(R.id.bt)
    public void haha() {
        new LoginAction()
                .addPara("18523641110", "123456")
                .addInterceptor(InterceptorUtil.buildCircleProgressbar(new CircleProgressBar(this, "加载中")))
                .onSuccess(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult result) {
                        LoginEntity entity = (LoginEntity) result.getEntity();
                        String[] permissions = new String[]{P_AUDIO, P_CAMERA, P_CONTACTS_GET};
                        queryPermissions(permissions, new OnPermissionResult() {
                            @Override
                            public void onPermissionResult(boolean isPermit) {
                                if (isPermit) {
                                    LogUtil.debug("申请权限成功");
                                } else {
                                    LogUtil.debug("申请权限失败");
                                }
                            }
                        });
                    }
                }).onFailToast(this).execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetChange(NetworkStatusChangeEvent event) {
        Toasty.warning(this, "hahahaha").show();
    }
}
