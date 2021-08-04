package com.sokhorn.degger.http;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<T> extends DRequest<T> {

    public abstract String getFunctionName();

    //    private String host = ""; //="http://dev.ecloudview.com:82";
    //    private AppSharePreference tools;
//    private AppShareSetting setting;
    String token;
//    SettingModel settingModel;

    public BaseRequest(Context context) {
        super(context);
//        setting = new AppShareSetting(context);
//        tools = new AppSharePreference(context);
//        token = tools.getTokenString();

//        settingModel = setting.getSetting();
//        UrlListModel model = new UrlListRealmManager().getUrlListModel(new SharePreferenceTools(context).getPrefernces(Common.ShareRef.ApiID));
//        if (model != null) {
//            host = (model.getType() + model.getUrl()).replaceAll("\\s", "");
//        }
    }

    @Override
    public String getBaseUrl() {
        return getFunctionName().replaceAll("\\s+", "");
    }

    @Override
    public Map<String, String> onCreateHeader(Map header) {
        HashMap<String, String> mheader = getHeader();
        mheader.put("Content-Type", "application/json; charset=utf-8");
        //  Log.d("HEADER:>>>", mheader.toString());
        return mheader;
    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>();
        //header.put("Authorization", "bearer " + token);
        header.put("Content-Type", "application/x-www-form-urlencoded");

        return header;
    }

    @Override
    public void onError(VolleyError error) {
        super.onError(error);
    }

}
