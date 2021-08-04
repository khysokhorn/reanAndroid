package com.sokhorn.degger.workinbackground

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.sokhorn.degger.R
import com.sokhorn.degger.databinding.ActivityWorkInBackgroundBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WorkInBackgroundActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityWorkInBackgroundBinding
    var textDisplay = ""
    var num = 0
    private val TAG = "WorkInBackgroundActivit"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_in_background)

//        lifecycleScope.launch(Dispatchers.Main) {
//            textDisplay += world()
//            Log.d(TAG, "world: OnCreate inside thread are in ${Thread.currentThread().name}")
//            binding.tvDisplay.text = textDisplay
//        }

        Log.d(TAG, "world: OnCreate inside thread are in ${Thread.currentThread().name}")
        binding.tvDisplay.text = textDisplay

        textDisplay = "Hello"
        binding.tvDisplay.text = textDisplay
        Log.d(TAG, "world: thread onCreate are in ${Thread.currentThread().name}")

        binding.btnShowDialog.setOnClickListener {
            num++;
            binding.tvNum.text = num.toString()
        }
    }

    private suspend fun world(): String {
        var str = ""
       delay(3000)
        lifecycleScope.launch (Dispatchers.IO){

        }

        return str
//        var str = ""
//        delay(9000L)
//        withContext(Dispatchers.IO) {
//            Log.d(TAG, " ==> world: world thread are in ${Thread.currentThread().name}")
//            str += " World from GOD"
//        }
//        return str
    }
}