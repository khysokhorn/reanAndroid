package com.sokhorn.degger.http;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public abstract class JSONArrayRequest extends BaseRequest<JSONArray> {
    public JSONArrayRequest(Context context) {
        super(context);
    }

    @Override
    protected Response<JSONArray> onParseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

//            try {
//                Log.i("RESPONSE<<<", this.getClass().getSimpleName()
//                        + " (" + getContext().getClass().getSimpleName() + ") "
//                        + response.statusCode + " " + new JSONObject(jsonString).toString(1));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            return Response.success(new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public void execute(Response.Listener onResponseListener) {
        super.execute(onResponseListener);
    }
}
