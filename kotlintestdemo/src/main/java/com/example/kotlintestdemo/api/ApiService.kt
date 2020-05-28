package com.example.kotlintestdemo.api

import com.example.kotlintestdemo.bean.Article
import retrofit2.http.GET

interface ApiService{

    companion object {
       const val BASE_URL="https://www.wanandroid.com"
    }



    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>


}