package com.sokhorn.degger.workinbackground

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sokhorn.degger.DataHandler
import com.sokhorn.degger.http.GetDataHandler
import org.json.JSONArray
import org.json.JSONObject

class Repository(private val context: Context) : FakeJsonInterface {
    private val TAG = "Repository"
    override fun getFakeJson(): MutableLiveData<FakeJsonModel> {
        val liveData = MutableLiveData<FakeJsonModel>()
        GetDataHandler(context).getJsonArray(
            "https://jsonplaceholder.typicode.com/posts",
            object : DataHandler {
                override fun getSuccess(jsonObject: JSONObject?) {
                    Log.d("Repository", "getSuccess: ")
//                    liveData.value =
//                        Gson().fromJson(jsonObject.toString(), FakeJsonModel::class.java)
                }

                override fun getSuccessArray(jsonObject: JSONArray?) {
                    super.getSuccessArray(jsonObject)
                    Log.d(TAG, "getSuccessArray: json array $jsonObject")
                    liveData.value =
                        Gson().fromJson(jsonObject.toString(), FakeJsonModel::class.java)

                }

                override fun getError(error: VolleyError?) {
                    Log.d(TAG, "getError: error with ${error?.networkResponse}")
                }
            })
        return liveData
    }
}