package com.danny.coremodule

import android.content.Context

interface CorePresenter<in V : CoreView> {

    fun attachView(context: Context, view: V)

    fun detachView()
}