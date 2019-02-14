package com.cathay.testproject.retrofit

import android.support.v4.app.Fragment
import io.reactivex.disposables.Disposable

open class RetrofitFragment: Fragment() {

    private val mCompositeDisposableHelper = CompositeDisposableHelper()

    fun addCompositeDisposable(d: Disposable) = mCompositeDisposableHelper.add(d)

    fun clearCompositeDisposable() = mCompositeDisposableHelper.clear()

    fun hasCompositeDisposable() = mCompositeDisposableHelper.hasCompositeDisposables()

    override fun onStop() {
        super.onStop()
        mCompositeDisposableHelper.clear()
    }
}