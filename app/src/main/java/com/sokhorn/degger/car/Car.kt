package com.sokhorn.degger.car

import android.util.Log
import javax.inject.Inject

// this how we can declare multi constructor in kotlin
class Car @Inject constructor(engine: Engine, wheels: Wheels) {
    private var engine: Engine? = engine

    fun drive() {
        engine?.startEngine()
        Log.d("==========>", "drive: car driving......")

    }
}