package com.example.kotlintestdemo.bean

import com.example.kotlintestdemo.RetrofitClient

class PopularRepository {
    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()

}