package com.cathay.testproject.retrofit.api

import com.cathay.testproject.retrofit.DomainPool
import com.momo.mobile.shoppingv2.android.retrofit.api.DataApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {

    companion object {
        fun pushDomainService(): DataApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(DomainPool.HOST_TEST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.setPushHttpLog())
                .build()

            return retrofit.create(DataApiService::class.java!!)
        }
    }

}