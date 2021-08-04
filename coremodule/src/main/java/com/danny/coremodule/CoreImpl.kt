package com.danny.coremodule

import android.content.Context

open class CoreImpl<V : CoreView> : CorePresenter<V> {

    protected var mView: V? = null
    protected var mContext: Context? = null

    override fun attachView(context: Context, view: V) {
        mContext = context
        mView = view
        mView!!.initView()
    }

    override fun detachView() {
        mView = null
    }
}