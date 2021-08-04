package com.danny.coremodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Danny on 2/13/2018.
 */

public class InternetBroadcastReceiver extends BroadcastReceiver {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;
    private static final String CONNECT_TO_WIFI = "WIFI";
    private static final String CONNECT_TO_MOBILE = "MOBILE";
    private static final String NOT_CONNECT = "NOT_CONNECT";
    public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private OnInternetConnectionChangeListener onInternetConnectionChangeListener;

    public void setOnInternetConnectionChangeListener(OnInternetConnectionChangeListener onInternetConnectionChangeListener) {
        this.onInternetConnectionChangeListener = onInternetConnectionChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = getConnectivityStatusString(context);
        Log.i("CONNECTION_CHANGE", "STATUS" + status);
        if (status.equals(NOT_CONNECT)) {
            Log.i("CONNECTION_CHANGE ", "INTERNET DISCONNECT");
            //  check !isConnected , cuz don't want to call back redundant
            if (onInternetConnectionChangeListener != null) {
                onInternetConnectionChangeListener.onDisconnected();
            }
        } else {
            Log.i("CONNECTION_CHANGE ", "INTERNET CONNECTED");
            //  check !isConnected , cuz don't want to call back redundant
            if (onInternetConnectionChangeListener != null) {
                onInternetConnectionChangeListener.onConnected();
            }
        }
    }

    public static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {

        int conn = getConnectivityStatus(context);

        String status = null;
        if (conn == TYPE_WIFI) {
            //status = "Wifi enabled";
            status = CONNECT_TO_WIFI;
        } else if (conn == TYPE_MOBILE) {
            //status = "Mobile data enabled";
            System.out.println(CONNECT_TO_MOBILE);
            status = getNetworkClass(context);
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NOT_CONNECT;
        }

        return status;
    }

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; //not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "UNKNOWN";
            }
        }
        return "UNKNOWN";
    }

    public interface OnInternetConnectionChangeListener {
        void onDisconnected();

        void onConnected();
    }
}
