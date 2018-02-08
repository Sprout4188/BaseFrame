package com.sprout.frame.baseframe.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.hwangjr.rxbus.RxBus;
import com.sprout.frame.baseframe.event.NetworkStatusChangeEvent;

/**
 * Created by Sprout on 2017/7/22
 */
public class NetUtil {

    /**
     * 无网络
     */
    public static final int TYPE_NO_NETWORK = 0;

    /**
     * WIFI
     */
    public static final int TYPE_WIFI = 1;

    /**
     * 数据流量
     */
    public static final int TYPE_MOBILE = 2;

    /**
     * 数据流量-2G
     */
    public static final int TYPE_MOBILE_2G = 20;

    /**
     * 数据流量-3G
     */
    public static final int TYPE_MOBILE_3G = 21;

    /**
     * 数据流量-4G
     */
    public static final int TYPE_MOBILE_4G = 22;

    private static volatile NetUtil mInstance;

    private Context mContext;

    private NetUtil(Context context) {
        this.mContext = context;
    }

    public static void initial(Context context) {
        mInstance = null;
        mInstance = new NetUtil(context);

        //动态注册网络变化的广播接收者
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(new NetworkChangeListenerReceiver(), filter);
    }

    public static NetUtil getInstance() {
        return mInstance;
    }

    /**
     * 获取手机网络连接类型(分为: 无网络, WiFi, 数据流量)
     */
    public int getNetworkType() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        int networkType = TYPE_NO_NETWORK;
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                networkType = TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                networkType = TYPE_MOBILE;
            }
        }
        return networkType;
    }

    /**
     * 获取手机网络连接类型(分为: 无网络, WiFi, 数据流量, 2G, 3G, 4G)
     */
    public int getAccurateNetworkType() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        int networkType = TYPE_NO_NETWORK;
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                networkType = TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                int _networkType = networkInfo.getSubtype();
                switch (_networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        networkType = TYPE_MOBILE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        networkType = TYPE_MOBILE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        networkType = TYPE_MOBILE_4G;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName
                                .equalsIgnoreCase("WCDMA") || _strSubTypeName
                                .equalsIgnoreCase("CDMA2000")) {
                            networkType = TYPE_MOBILE_3G;
                        } else {
                            networkType = TYPE_MOBILE;
                        }
                        break;
                }
            }
        }
        return networkType;
    }

    /**
     * 判断当前网络是否已连接
     */
    public boolean isNetworkConnected() {
        return getNetworkType() != NetUtil.TYPE_NO_NETWORK;
    }


    /**
     * 判断GPS是否开启
     * @return true 表示开启
     */
    public boolean isGPSOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位精度高（适用于在室外和空旷的地方定位, 速度慢）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        // 通过WLAN或移动网络(3G/2G)定位（也称AGPS，辅助GPS定位。适用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位, 精度低）
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        return gps || network;
        return gps;
    }

    /*网络改变的广播接收者*/
    public static class NetworkChangeListenerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //发送Eventbus
            RxBus.get().post(new NetworkStatusChangeEvent(NetUtil.getInstance().isNetworkConnected()));
        }
    }
}
