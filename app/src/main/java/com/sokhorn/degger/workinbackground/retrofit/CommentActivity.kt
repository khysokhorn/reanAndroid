package com.sokhorn.degger.workinbackground.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sokhorn.degger.R
import com.sokhorn.degger.workinbackground.retrofit.model.CommentModel
import com.sokhorn.degger.workinbackground.retrofit.network.CommentReceiver
import kotlinx.coroutines.*

class CommentActivity : AppCompatActivity() {
    private val commentReceiver = CommentReceiver()

    private val TAG = "CommentActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        fetchComment()
    }

    private fun fetchComment() {
        val commentJob = Job()
        val errorHandler = CoroutineExceptionHandler() { _, throwable ->
            throwable.printStackTrace()
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        val scope = CoroutineScope(commentJob + Dispatchers.Main)
        scope.launch(errorHandler) {

            val comment = commentReceiver.getComment()
            renderDataOut(comment)
        }
    }
    private fun renderDataOut(comRes: List<CommentModel>) {
        Log.d(TAG, "renderDataOut: comment list $comRes")
    }
}