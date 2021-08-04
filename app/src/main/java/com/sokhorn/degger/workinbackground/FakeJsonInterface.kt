package com.sokhorn.degger.workinbackground

import androidx.lifecycle.MutableLiveData
import com.android.volley.Response

interface FakeJsonInterface {
    fun getFakeJson(): MutableLiveData<FakeJsonModel>
    suspend fun getFakeJson1(): Response<FakeJsonModel>? {
        return null
    }
}