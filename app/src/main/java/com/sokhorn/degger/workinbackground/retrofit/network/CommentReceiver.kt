package com.sokhorn.degger.workinbackground.retrofit.network

import com.sokhorn.degger.workinbackground.retrofit.model.CommentModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentReceiver : CommentInterface {
    private lateinit var commentInterface: CommentInterface

    init {
        val retro = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        commentInterface = retro.create(commentInterface::class.java)
    }

    override suspend fun getComment(): List<CommentModel> {
        // get data here
        return commentInterface.getComment()
    }

}