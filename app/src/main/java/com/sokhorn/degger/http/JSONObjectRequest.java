package com.sokhorn.degger.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public abstract class JSONObjectRequest extends BaseRequest<JSONObject> {
    private static final String TAG = "JSONObjectRequest12";

    public JSONObjectRequest(Context context) {
        super(context);
    }

    int code;

    public int getCode() {
        return code;
    }

    @Override
    protected Response<JSONObject> onParseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            try {
                Log.i("RESPONSE<<<", this.getClass().getSimpleName()
                        + " (" + getContext().getClass().getSimpleName() + ") "
                        + response.statusCode + " " + new JSONObject(jsonString).toString(1));
                code = response.statusCode;
            } catch (Exception e) {
                Log.d(TAG, "onParseNetworkResponse: " + e.getMessage());
                e.printStackTrace();
            }
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "onParseNetworkResponse: " + e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            Log.d(TAG, "onParseNetworkResponse: " + getVolleyError().getMessage());
            return Response.error(new ParseError(je));
        }
    }


    @Override
    public void execute(Response.Listener onResponseListener) {
        super.execute(onResponseListener);
    }


}
