package com.example.mvvm.data.api

import android.util.Log
import com.example.mvvm.BuildConfig
import com.example.mvvm.data.service.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        private const val timeout = 10L
    }

    private val interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder().url(
            originalHttpUrl
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
        )
        chain.proceed(requestBuilder.build())
    }

    private fun logger(): Interceptor {
        val logger = HttpLoggingInterceptor {
            Log.d("API", it)
        }
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    private val defaultHttpClient: OkHttpClient = OkHttpClient.Builder()
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(logger())
            .addInterceptor(interceptor)
            .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(defaultHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val apiService: ApiService = builder.create(ApiService::class.java)

}