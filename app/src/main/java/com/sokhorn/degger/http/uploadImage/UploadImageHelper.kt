//package com.sokhorn.degger.http.uploadImage
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.util.Log
//import android.widget.Toast
//import com.android.volley.Response
//import com.android.volley.toolbox.Volley
//import com.sunfix.cuscen.DataHandler
//import com.sunfix.cuscen.common.Common
//import com.sunfix.cuscen.http.VolleyMultipartRequest
//import com.sunfix.cuscen.managers.UrlListRealmManager
//import com.sunfix.cuscen.tools.SharePreferenceTools
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.ByteArrayOutputStream
//
//class UploadImageHelper(private var context: Context) {
//    private var host = ""
//
//    private fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
//        return byteArrayOutputStream.toByteArray()
//    }
//
//    fun uploadBitmap(
//        bitmap: Bitmap,
//        imageNameUrl: String,
//        index: Int,
//        dataHandler: DataHandler
//    ) {
//        val model =
//            UrlListRealmManager().getUrlListModel(SharePreferenceTools(context).getPrefernces(Common.ShareRef.ApiID))
//        if (model != null) {
//            host = (model.type + model.url).replace("\\s".toRegex(), "")
//        }
////
////        "$host/api/InspectionApp/UploadPicture?FileName=$imageNameUrl",
//        val volleyMultipartRequest: VolleyMultipartRequest = object : VolleyMultipartRequest(
//            context, Method.POST,
//            "$host/api/InspectionApp/UploadPicture?Token=${imageNameUrl}&Filename=${imageNameUrl}_$index",
//            Response.Listener { response ->
//                try {
//                    val obj = JSONObject(String(response.data))
//                    Log.d("d", obj.toString())
////                    Toast.makeText(
////                        context,
////                        obj.getString("result"),
////                        Toast.LENGTH_SHORT
////                    ).show()
//                    dataHandler.getSuccess(obj)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                    Log.d("code", response.statusCode.toString() + "")
//                }
//            },
//
//            Response.ErrorListener { error ->
//                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
//                Log.e("GotError", "" + error.message)
//                dataHandler.getError(error)
//            }) {
//
//            // pass image
//            override fun getByteData(): Map<String, DataPart> {
//                val params: MutableMap<String, DataPart> = HashMap()
//                val imageName = System.currentTimeMillis()
//                params["formFile"] = DataPart("$imageName", getFileDataFromDrawable(bitmap))
//                return params
//            }
//
//            // pass param token
//            override fun getParams(): MutableMap<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["fileName"] = "${imageNameUrl}_$index"
//                params["token"] = imageNameUrl
//                return params
//            }
//
//        }
//
//        //adding the request to volley
//        Volley.newRequestQueue(context).add(volleyMultipartRequest)
//    }
//}