package com.cathay.testproject.retrofit.api


import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by iristai on 2019/02/08.
 */
object ObservableUtils {

    fun <T> schedulersHandling(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.retry(2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
