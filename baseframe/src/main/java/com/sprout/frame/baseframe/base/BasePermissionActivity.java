/**
 *
 */
package com.sprout.frame.baseframe.base;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.sprout.frame.baseframe.utils.ActivityUtil;
import com.sprout.frame.baseframe.widgets.nicetoast.Toasty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sprout on 2017/8/17<Br/>
 * 实现6.0(API 23)的运行时权限检测, 需申请动态权限的Activity可继承本类<P>
 * 注: 不管普通权限还是动态权限都需要在AndroidManifest.xml中注册
 */
public abstract class BasePermissionActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSON_REQUESTCODE = 66;

    //日历分组
    protected static final String P_CALENDAR_READ = Manifest.permission.READ_CALENDAR;
    protected static final String P_CALENDAR_WRITE = Manifest.permission.WRITE_CALENDAR;
    //相机分组
    protected static final String P_CAMERA = Manifest.permission.CAMERA;
    //联系人组
    protected static final String P_CONTACTS_READ = Manifest.permission.READ_CONTACTS;
    protected static final String P_CONTACTS_WRITE = Manifest.permission.WRITE_CONTACTS;
    protected static final String P_CONTACTS_GET = Manifest.permission.GET_ACCOUNTS;
    //定位分组
    protected static final String P_LOCATION_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    protected static final String P_LOCATION_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    //录音分组
    protected static final String P_AUDIO = Manifest.permission.RECORD_AUDIO;
    //电话分组
    protected static final String P_PHONE_READ_STATE = Manifest.permission.READ_PHONE_STATE;
    protected static final String P_PHONE_READ_LOG = Manifest.permission.READ_CALL_LOG;
    protected static final String P_PHONE_WRITE_LOG = Manifest.permission.WRITE_CALL_LOG;
    protected static final String P_PHONE_CALL = Manifest.permission.CALL_PHONE;
    protected static final String P_PHONE_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    protected static final String P_PHONE_SIP = Manifest.permission.USE_SIP;
    protected static final String P_PHONE_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    //传感器组
    protected static final String P_SENSORS = Manifest.permission.BODY_SENSORS;
    //短信分组
    protected static final String P_SMS_SEND = Manifest.permission.SEND_SMS;
    protected static final String P_SMS_READ = Manifest.permission.READ_SMS;
    protected static final String P_SMS_RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    protected static final String P_SMS_RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    protected static final String P_SMS_RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    //存储器组
    protected static final String P_STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    protected static final String P_STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    //判断是否是第一次申请权限，若是则弹框提示, 若不是则Toast提示
    private boolean isFirstCheck = true;
    private OnPermissionResult resultCallback;

    /**
     * 检测并申请权限
     *
     * @param permissions 待申请的权限
     * @param callback    申请成功与否的回调接口
     */
    protected void requestPermissions(String[] permissions, OnPermissionResult callback) {
        if (permissions == null || permissions.length == 0)
            throw new IllegalArgumentException("The permission array hasn't elements");
        resultCallback = callback;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions(permissions);
        } else {
            if (resultCallback != null) resultCallback.onPermissionResult(true);
        }
    }

    /**
     * 检测是否拥有指定动态权限, 并申请
     */
    private void checkPermissions(String... permissions) {
        List<String> denyPermisses = findDeniedPermissions(permissions);
        if (denyPermisses != null && denyPermisses.size() > 0) {
            if (isFirstCheck) {
                ActivityCompat.requestPermissions(this, denyPermisses.toArray(new String[denyPermisses.size()]), PERMISSON_REQUESTCODE);
            } else {
                toastInfo(denyPermisses);
                if (resultCallback != null) resultCallback.onPermissionResult(false);
            }
        } else {
            if (resultCallback != null) resultCallback.onPermissionResult(true);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> denyPermisses = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                denyPermisses.add(perm);
            }
        }
        return denyPermisses;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                //若还有部分动态权限未批准, 则弹出提示对话框
                isFirstCheck = false;
                if (resultCallback != null) resultCallback.onPermissionResult(false);
                showMissingPermissionDialog();
            } else {
                if (resultCallback != null) resultCallback.onPermissionResult(true);
            }
        } else {
            if (resultCallback != null) resultCallback.onPermissionResult(false);
        }
    }

    /**
     * 检测是否所有的动态权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示").setMessage("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityUtil.startAppSettings(BasePermissionActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
    }

    protected interface OnPermissionResult {

        /**
         * 权限申请成功与否的回调
         *
         * @param isPermit 只要待申请的权限中有一个被拒绝则为false
         */
        void onPermissionResult(boolean isPermit);
    }

    /**
     * 根据不同的权限分组给出不同的提示
     */
    private void toastInfo(List<String> permisses) {
        for (String str : permisses) {
            switch (str) {
                case P_CALENDAR_READ:
                case P_CALENDAR_WRITE:
                    Toasty.info(this, "请在系统\"设置\"中打开-日历-权限。").show();
                    break;
                case P_CAMERA:
                    Toasty.info(this, "请在系统\"设置\"中打开-相机-权限。").show();
                    break;
                case P_CONTACTS_READ:
                case P_CONTACTS_WRITE:
                case P_CONTACTS_GET:
                    Toasty.info(this, "请在系统\"设置\"中打开-联系人-权限。").show();
                    break;
                case P_LOCATION_FINE:
                case P_LOCATION_COARSE:
                    Toasty.info(this, "请在系统\"设置\"中打开-定位-权限。").show();
                    break;
                case P_AUDIO:
                    Toasty.info(this, "请在系统\"设置\"中打开-录音-权限。").show();
                    break;
                case P_PHONE_READ_STATE:
                case P_PHONE_READ_LOG:
                case P_PHONE_WRITE_LOG:
                case P_PHONE_CALL:
                case P_PHONE_VOICEMAIL:
                case P_PHONE_SIP:
                case P_PHONE_OUTGOING_CALLS:
                    Toasty.info(this, "请在系统\"设置\"中打开-电话-权限。").show();
                    break;
                case P_SENSORS:
                    Toasty.info(this, "请在系统\"设置\"中打开-传感器-权限。").show();
                    break;
                case P_SMS_SEND:
                case P_SMS_READ:
                case P_SMS_RECEIVE_SMS:
                case P_SMS_RECEIVE_WAP_PUSH:
                case P_SMS_RECEIVE_MMS:
                    Toasty.info(this, "请在系统\"设置\"中打开-短信-权限。").show();
                    break;
                case P_STORAGE_READ:
                case P_STORAGE_WRITE:
                    Toasty.info(this, "请在系统\"设置\"中打开-存储-权限。").show();
                    break;
            }
        }
    }
}
