package com.sokhorn.degger.workinbackground.retrofit.model


import com.google.gson.annotations.SerializedName

class CommentModel : ArrayList<CommentModel.CommentModelItem>(){
    data class CommentModelItem(
        @SerializedName("body")
        var body: String = "",
        @SerializedName("email")
        var email: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        var name: String = "",
        @SerializedName("postId")
        var postId: Int = 0
    )
}