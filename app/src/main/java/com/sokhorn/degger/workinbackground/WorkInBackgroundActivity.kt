package com.sokhorn.degger.workinbackground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.sokhorn.degger.R
import com.sokhorn.degger.databinding.ActivityWorkInBackgroundBinding
import com.sokhorn.degger.dialog.Dialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WorkInBackgroundActivity : AppCompatActivity() {

    private lateinit var fakeJsonFakeViewModel: FakeJsonFakeViewModel
    private lateinit var dialog: Dialog
    private lateinit var binding: ActivityWorkInBackgroundBinding
    private val TAG = "WorkInBackgroundActivit"
    private var num = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_in_background)
        fakeJsonFakeViewModel = FakeJsonFakeViewModel(this)

        getData()

        binding.toolbar.setNavigationOnClickListener {

        }

        binding.btnShowDialog.setOnClickListener {
            lifecycleScope.launch {
                while (true) {
                    Log.d(
                        TAG,
                        "onCreate: hey, yo I am still working in this ${Thread.currentThread()}"
                    )
                    delay(1000L)
                }
            }

        }


    }

    private suspend fun networkCall(): String {
        delay(3000)
        return "This is answer one"
    }

    private suspend fun networkCall2(): String {
        delay(3000)
        return "This is answer two"
    }

    // get data using kotlin coroutines
    private fun getData() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000L)
            fakeJsonFakeViewModel.getFakeJson().observe(this@WorkInBackgroundActivity, Observer {
                run {
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.tvDisplay.text = it[0].title
                    }
                }
            })
        }

    }


}


//        val job = GlobalScope.launch {
//            repeat(5) {
//                Log.d(TAG, "onCreate: coroutine running...")
//                delay(1000L)
//            }
//        }
//
//        runBlocking {
//            job.join()
//            Log.d(TAG, "onCreate: Main thread are running...")
//        }
//
////        Thread.sleep(3000L)
//        runBlocking {
//            delay(3000L) // it will sleep main thread for 3s
//            launch(Dispatchers.IO) {
//                delay(3000L)
//                Log.d(TAG, "onCreate: finish coroutines 1")
//            }
//            launch(Dispatchers.IO) {
//                delay(4000L)
//                Log.d(TAG, "onCreate: finish coroutines 2")
//            }
//        }
//        dialog = Dialog(this)
//        GlobalScope.launch(Dispatchers.IO) {
//            val net1 = networkCall()
//            val net2 = networkCall2()
//            // display when network call finished
//            withContext(Dispatchers.Main) {
//                binding.tvNum.text = net1
//            }
//            Log.d(TAG, "onCreate: Hello  net 1 $net1 and net2 are $net2")
//            Log.d(TAG, "onCreate: Hello coroutines from thread ${Thread.currentThread().name}")
//        }
//        Log.d(TAG, "onCreate: Hello from thread ${Thread.currentThread().name}")
//        binding.btnShowDialog.setOnClickListener {
//            num++
//            binding.tvNum.text = num.toString()
//        }