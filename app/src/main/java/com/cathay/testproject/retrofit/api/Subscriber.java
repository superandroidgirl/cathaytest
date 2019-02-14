package com.cathay.testproject.retrofit.api;

import android.util.Log;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;


public abstract class Subscriber<T> extends DisposableObserver<T> {

    private static final String TAG = Subscriber.class.getSimpleName();

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable != null && throwable.getMessage() != null) {
            Log.e(TAG, throwable.getMessage());

        }
    }
}
