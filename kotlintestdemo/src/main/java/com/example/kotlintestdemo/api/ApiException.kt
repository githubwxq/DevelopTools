package com.example.kotlintestdemo.api

import java.lang.RuntimeException

class ApiException(var code:Int,override var message:String):RuntimeException()