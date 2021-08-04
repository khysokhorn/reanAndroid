package com.sokhorn.degger.car

import android.util.Log
import javax.inject.Inject

class Engine@Inject constructor(private val gas: Gas) {
    fun startEngine() {
        gas.gasDescription()
        Log.d("==========>", "startEngine: car start......")
    }
}