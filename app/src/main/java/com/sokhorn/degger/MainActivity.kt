package com.sokhorn.degger

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.sokhorn.degger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var videoPlayer: SimpleExoPlayer? = null
    private var sampleUrl =
        "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializePlayer()
    }


    private fun buildMediaSource(): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(sampleUrl))
    }

    private fun initializePlayer() {
        videoPlayer = SimpleExoPlayer.Builder(this).build()
        binding.videoPlayerView.player = videoPlayer
        buildMediaSource().let {
            videoPlayer?.prepare(it)
        }
    }

    override fun onResume() {
        super.onResume()
        videoPlayer?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        videoPlayer?.playWhenReady = false
        if (isFinishing) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        videoPlayer?.release()
    }
}


//val carComponent = DaggerAppComponent.create()
//
//// dagger wth take this activity and do instance for us
//carComponent.injection(this) // take this mainActivity to register for dagger to create an instance
//
//val gas = Gas()
//val engine = Engine(gas)
//val wheels = Wheels()
//val car = Car(engine, wheels)
//
//// passing data to worker
//val inputData = Data.Builder()
//    .putString(Constant.usrData, "String userData")
//    .putString(Constant.otherUserData, "OtherUserData")
//    .build()
//
//// work in oneTime
//val uploadRequest = OneTimeWorkRequestBuilder<UserUploadWorker>()
//    .setInputData(inputData)
//    .build()
//// start run app with background services
//val instance = WorkManager.getInstance(this)
//
//instance.enqueue(uploadRequest)
//
////instance.enqueue(uploadRequest)
////  when we do this we will get null because data not processing yet but we force to get it
//// output data
//instance.getWorkInfoByIdLiveData(uploadRequest.id)
//.observe(this, { workerInfo ->
//    val userData =
//        if (workerInfo != null && workerInfo.state == WorkInfo.State.SUCCEEDED) {
//            // data from our process in do work
//            workerInfo.outputData.getString(Constant.usrData)
//        } else if (workerInfo.state == WorkInfo.State.RUNNING) {
//            "Please wait"
//        } else if (workerInfo.state == WorkInfo.State.BLOCKED) {
//            "Block"
//        } else if (workerInfo.state == WorkInfo.State.CANCELLED) {
//            "Canceled"
//        } else if (workerInfo.state == WorkInfo.State.ENQUEUED) {
//            "We are processing on your data "
//        } else if (workerInfo.state == WorkInfo.State.FAILED) {
//            "Failed to update."
//        } else {
//            "Something went wrong."
//        }
//    Log.d("TAG", "onCreate: user data $userData")
//    Toast.makeText(this, userData, Toast.LENGTH_LONG).show()
//})
//
//
//car.drive()
//engine.startEngine()


// work in PeriodicWorkRequest
// val periodicWorkRequest = PeriodicWorkRequestBuilder<UserUploadWorker>(
//     1, TimeUnit.MINUTES
// ).build()

// // register to to periodic time
// WorkManager.getInstance(this).enqueue(periodicWorkRequest)