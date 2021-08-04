package com.sokhorn.degger.workinbackground


import com.google.gson.annotations.SerializedName

class FakeJsonModel : ArrayList<FakeJsonModel.FakeJsonModelItem>(){
    data class FakeJsonModelItem(
        @SerializedName("body")
        var body: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("title")
        var title: String = "",
        @SerializedName("userId")
        var userId: Int = 0
    )
}