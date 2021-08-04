package com.danny.coremodule;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.jaredrummler.android.device.DeviceName;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Devices {
    static DeviceName.DeviceInfo mMarketName;
    public static String getDeviceModel() {
        String mModel;
        mModel = Build.MODEL;
        return mModel;
    }
    public static String getDeviceID(Context context) {
        String mModel;
        mModel = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return mModel;
    }
    public static void getDeviceInfo(Context context) {
        if(mMarketName == null) {
            DeviceName.with(context)
                    .request(new DeviceName.Callback() {
                        @Override
                        public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                            if (error == null && null != info) {
                                mMarketName = info;
                            }
                        }
                    });
        }
    }

    public static String getMarketName(){
        if(mMarketName != null) {
            return mMarketName.marketName;
        }else{
            return "";
        }
    }
    public static String getDeviceOSVersion() {
        String mOSVersion;
        mOSVersion = Build.VERSION.RELEASE;
        return mOSVersion;
    }
    public static int getDeviceSDKVersion() {
        int mSdkVersion;
        mSdkVersion = Build.VERSION.SDK_INT;
        return mSdkVersion;
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }
        return phrase.toString();
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0.0";
    }
    public static int getAppVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getDeviceBrand() {
        String mBrand = "UNKNOWN";
        mBrand = Build.BOARD;
        return mBrand;
    }
    public static String getDeviceProductName() {
        String mBrand = "UNKNOWN";
        mBrand = Build.PRODUCT;
        return mBrand;
    }
    /***
     * This Method user for get Application Hast Key in Base 64
     *
     * @param context
     */
    public static void getAppHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        }
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
