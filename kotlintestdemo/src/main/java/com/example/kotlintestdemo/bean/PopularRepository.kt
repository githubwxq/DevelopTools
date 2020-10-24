package com.example.kotlintestdemo.bean

import com.example.kotlintestdemo.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PopularRepository {
    suspend fun getTopArticleList() = RetrofitClient.apiService.getTopArticleList().apiData()


    suspend fun getMydata():String= withContext(Dispatchers.IO){
        println("getMydatabefore"+Thread.currentThread().name)
        delay(2000)
        println("getMydataafter"+Thread.currentThread().name)
          "获取到型数据"
    }
}