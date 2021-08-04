package com.sokhorn.degger.model


import com.google.gson.annotations.SerializedName

data class ResModel(
    @SerializedName("content")
    var content: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("updated_at")
    var updatedAt: String
)