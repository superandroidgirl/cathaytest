package com.cathay.testproject.retrofit.api

import android.os.Build
import com.cathay.testproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClient {



    companion object {
        private val TIMEOUT_SECONDS = 5

        fun setPushHttpLog(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }

            val httpClient_builder = OkHttpClient.Builder()
            httpClient_builder.connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            httpClient_builder.writeTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            httpClient_builder.readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            httpClient_builder.addInterceptor(logging)
            httpClient_builder.addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("device", "Android")
                    .header("sdk", Integer.toString(Build.VERSION.SDK_INT))
                    .method(original.method(), original.body())
                    .build()

                chain.proceed(request)
            }

            return httpClient_builder.build()
        }
    }

}