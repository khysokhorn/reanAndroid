package com.sokhorn.degger.workinbackground.retrofit.network

import com.sokhorn.degger.workinbackground.retrofit.model.CommentModel
import retrofit2.http.GET

interface CommentInterface {
    @GET("/comments")
    suspend fun getComment(): List<CommentModel>
}