package com.example.kotlintestdemo

import android.app.Application

class MyApp : Application() {


    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (isMainProcess(this)) {
            init()
        }
    }

    private fun init() {
        System.setProperty("kotlinx.coroutines.debug", "on")
    }

}