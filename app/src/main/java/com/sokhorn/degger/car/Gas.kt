package com.sokhorn.degger.car

import android.util.Log
import javax.inject.Inject

class Gas @Inject constructor() {
    fun gasDescription() {
        Log.d("==========>", "gasDescription: this car is started with gas....")
    }
}