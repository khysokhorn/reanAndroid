package com.sokhorn.degger.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.sokhorn.degger.Constant
import kotlinx.coroutines.delay

class UserUploadWorker(
    context: Context, workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    private val TAG = "UserUploadWorker"
    override fun doWork(): Result {
        // get data from activity
        val usrData = inputData.getString(Constant.usrData)
        val otherData = inputData.getString(Constant.otherUserData)
        Log.d(TAG, "doWork: user data $usrData with other $otherData ")
        // update data from background

        val outPutData = workDataOf(Constant.usrData to usrData + "have updated with worker class")
        Log.d(TAG, "doWork: $outPutData")

        return Result.success(outPutData)
    }

}