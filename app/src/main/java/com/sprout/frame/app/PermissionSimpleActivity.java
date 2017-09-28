package com.sprout.frame.app;

import android.os.Bundle;

import com.sprout.frame.baseframe.base.BasePermissionActivity;
import com.sprout.frame.baseframe.utils.LogUtil;

import butterknife.OnClick;

/**
 * 动态权限管理示例
 */
public class PermissionSimpleActivity extends BasePermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_permission_simple);
        setTitle("动态权限管理示例");
    }

    @OnClick(R.id.btRequestPermisson)
    public void click() {
        String[] permissions = new String[]{P_AUDIO, P_CAMERA, P_CONTACTS_GET};
        requestPermissions(permissions, new OnPermissionResult() {
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
}
