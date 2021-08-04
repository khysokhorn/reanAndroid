package com.sokhorn.degger.request

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyRequest(private val context: Context) {
    private val _baseUrl = "http://192.168.43.106:8000/api"
    fun post(jsonObject: JSONObject) {

        val jsonRequest = JsonObjectRequest(Request.Method.POST, "$_baseUrl/posts", jsonObject,
            {
                // after add data to our local database we need to show notification to user
                Log.d("", "post: jsobdata ")
//                val resModel = Gson().fromJson(it.toString(), ResModel::class.java)
//                val np =
//                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                val notification = NotificationCompat.Builder(context, "first").apply {
//                    setContentTitle(resModel.title)
//                    setContentText(resModel.content)
//                }.build()
//                np.notify(System.currentTimeMillis().toInt(), notification)
            },
            {
                Log.d("TAG", "post:  hey there are some error here with ${it.stackTrace}")
            }
        )
        Volley.newRequestQueue(context).add(jsonRequest)
    }
}