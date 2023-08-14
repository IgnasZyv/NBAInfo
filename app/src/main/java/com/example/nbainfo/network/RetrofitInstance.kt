package com.example.nbainfo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private const val BASE_URL = "https://www.balldontlie.io/api/v1/"

    // create the logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // build the retrofit instance with the base url and the gson converter
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // add the logging interceptor to the http client
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()
    }

    // create the api service
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}