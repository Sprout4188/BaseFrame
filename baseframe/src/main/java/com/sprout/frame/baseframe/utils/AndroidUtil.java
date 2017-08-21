package com.sprout.frame.baseframe.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.sprout.frame.baseframe.sp.CommonSP;

import java.util.UUID;

/**
 * Create by Sprout at 2017/8/15
 */
public class AndroidUtil {

    /**
     * 手机系统SDK版本号, 如16
     */
    public static int SDK_VERSION_CODE = 0;
    /**
     * build.gradle中定义的app版本号, 如versionCode 3
     */
    public static int APP_VERSION_CODE = 0;
    /**
     * build.gradle中定义的app版本名, 如versionName "1.0"
     */
    public static String APP_VERSION_NAME = null;

    public static void initial(Context context) {
        SDK_VERSION_CODE = getSDKVersionCode();
        APP_VERSION_CODE = getAppVersionCode(context);
        APP_VERSION_NAME = getAppVersionName(context);
    }

    /**
     * 获取手机系统SDK版本号, 如16
     */
    private static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取build.gradle中定义的app版本名, 如versionName "1.0"
     */
    private static String getAppVersionName(Context mContext) {
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取build.gradle中定义的app版本号, 如versionCode 3
     */
    private static int getAppVersionCode(Context mContext) {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取IMEI UUID
     */
    public static String getUUID() {
        String uuid = CommonSP.uuid.getValue();
        if (TextUtils.isEmpty(uuid)) {  //没有设置过
            uuid = UUID.randomUUID().toString();
            CommonSP.uuid.setValue(uuid);
        }
        return uuid;
    }

    /**
     * 获取当前进程名
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningProcess : am.getRunningAppProcesses()) {
            if (runningProcess.pid == pid) {
                return runningProcess.processName;
            }
        }
        return null;
    }

    /**
     * 是否装载SD卡
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回SD卡路径
     */
    public static String getSdcardDir() {
        return hasSdcard() ? Environment.getExternalStorageDirectory().toString() : "";
    }


    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值
     */
    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
