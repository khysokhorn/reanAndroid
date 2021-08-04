package com.sokhorn.degger;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface DataHandler {
    void getSuccess(JSONObject jsonObject);
    default void getSuccessArray(JSONArray jsonObject){}
    void getError(VolleyError error);
}
