package com.example.kotlintestdemo

/**
 * Created by xiaojianjun on 2019-09-18.
 */
class PopularRepository {
    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()

}