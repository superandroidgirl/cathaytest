package com.cathay.testproject.retrofit

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CompositeDisposableHelper {

    private val mCompositeDisposable = CompositeDisposable()

    fun add(d: Disposable) {
        mCompositeDisposable.add(d)
    }

    fun clear() {
        mCompositeDisposable.clear()
    }

    fun hasCompositeDisposables(): Boolean {
        return mCompositeDisposable.size() > 0
    }

    fun remove(d: Disposable) {
        mCompositeDisposable.remove(d)
    }
}
