package com.sokhorn.degger.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sokhorn.degger.DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetDataHandler {
    private Context context;
    private static final String TAG = "GetDataHandler";

    public GetDataHandler(Context context) {
        this.context = context;
    }

    public void get(String endPoint, DataHandler dataHandler) {
        new JSONObjectRequest(context) {
            @Override
            public String getFunctionName() {
                return endPoint;
            }

            @Override
            public int getMethod() {
                return Request.Method.GET;
            }
        }.setOnErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    Log.e(TAG, "\n\n***************************************\n\n");
                    Log.e("GetDataHandler",
                            " url" + endPoint
                                    + "====>" + "onResponse: response getData " + error.networkResponse);
                    Log.e(TAG, "\n\n***************************************\n\n");

                    dataHandler.getError(error);
                }
            }
        }).execute((Response.Listener<JSONObject>) response -> {
            Log.w(TAG, "\n\n***************************************\n\n");
            Log.d("GetDataHandler",
                    " url" + endPoint
                            + "====>" + "onResponse: response getData " + response);
            Log.w(TAG, "\n\n***************************************\n\n");

            if (response != null) {
                dataHandler.getSuccess(response);
            }
        });
    }

    public void post(String endPoint, String jsonBody, DataHandler dataHandler) {
        new JSONObjectRequest(context) {
            @Override
            public String onGetBodyRequest() {
                // body to push
                return jsonBody.toString();
            }

            @Override

            public String getFunctionName() {
                return endPoint;
            }

            @Override
            public int getMethod() {
                return Request.Method.POST;
            }
        }.setOnErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    dataHandler.getError(error);
                }
            }
        }).execute(new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: data123 " + response);
                if (response != null) {
                    dataHandler.getSuccess(response);
                }
            }
        });
    }

    public void getJsonArray(String endPoint, DataHandler dataHandler) {
        new JSONArrayRequest(context) {
            @Override
            public String getFunctionName() {
                return endPoint;
            }

            @Override
            public int getMethod() {
                return Request.Method.GET;
            }
        }.setOnErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    Log.e(TAG, "\n\n***************************************\n\n");
                    Log.e("GetDataHandler",
                            " url" + endPoint
                                    + "====>" + "onResponse: response getData " + error.networkResponse);
                    Log.e(TAG, "\n\n***************************************\n\n");

                    dataHandler.getError(error);
                }
            }
        }).execute((Response.Listener<JSONArray>) response -> {
            Log.w(TAG, "\n\n***************************************\n\n");
            Log.d("GetDataHandler",
                    " url" + endPoint
                            + "====>" + "onResponse: response getData " + response);
            Log.w(TAG, "\n\n***************************************\n\n");

            if (response != null) {
                dataHandler.getSuccessArray(response);
            }

        });
    }

}
