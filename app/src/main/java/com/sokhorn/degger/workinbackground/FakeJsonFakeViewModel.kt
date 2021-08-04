package com.sokhorn.degger.workinbackground

import android.content.Context
import androidx.lifecycle.MutableLiveData

class FakeJsonFakeViewModel(context: Context) :
    FakeJsonInterface {
    private val repository = Repository(context)

    override fun getFakeJson(): MutableLiveData<FakeJsonModel> {
        return repository.getFakeJson()
    }
}