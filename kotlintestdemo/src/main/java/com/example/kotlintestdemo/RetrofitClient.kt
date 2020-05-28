package com.example.kotlintestdemo

import com.example.kotlintestdemo.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {


    private val okHttpClient=OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .build();


    private val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}