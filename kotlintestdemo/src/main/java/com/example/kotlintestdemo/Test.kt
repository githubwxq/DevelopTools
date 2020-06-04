package com.example.kotlintestdemo

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class Test constructor(var num:Int){

    init {
        num=1000;
        print("number=$num")
    }

}

suspend fun dddd(url: String) = withContext(Dispatchers.IO) {
    url
}


fun main(){
    var  test=Test(11111);

//      runBlocking {
//        val elapsedTime = measureTimeMillis {
//            val value1 = intValue1()
//            println("hello world111")
//            val value2 = intValue2()
//            println("hello world222")
//
//            println("the result is ${value1 + value2}")
//        }
//        println("the elapsedTime is $elapsedTime")
//    }

//    runBlocking {
//        println("hello block 携程开始")
//        val elapsedTime = measureTimeMillis {
//            println("hello world111")
//            val value1 = async { intValue1() }
//            println("hello world2222")
//            val value2 = async { intValue2() }
//
//
//            println("the async is ${value1.await() + value2.await()}")
//        }
//        println("the asyncelapsedTime is $elapsedTime")
//
//      var aa=  async { dddd("1111") }
//
//        aa.await()
//
//        var ddd:String=dddd("1111")
//    }
//    println("hello block 携程结束")
//
//
//





//    runBlocking {
//
//        val elapsedTime = measureTimeMillis {
//
//            val intValue1 = async(start = CoroutineStart.LAZY) { intValue1() }
//            val intValue2 = async(start = CoroutineStart.LAZY) { intValue2() }
//
//            println("hello world")
//
//            val result1 = intValue1.await()
//            val result2 = intValue2.await()
//
//            println("the result is : ${result1 + result2}")
//        }
//
//        println("elapsedTime = $elapsedTime")
//
////        hello world
////        intValue1main
////        delay 后intValue1main
////                intValue2main
////        delay 后intValue2main
////                the result is : 3
////        elapsedTime = 3021
//
//    }





//    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
//    val sum2 = { x: Int, y: Int -> {x + y} }
//
//    sum2(11,22)



}


private suspend fun intValue1(): Int {
    println("intValue1"+Thread.currentThread().name)

    delay(1000)
    println(" delay 后intValue1"+Thread.currentThread().name)
    return 1
}

private suspend fun intValue2(): Int {
    println("intValue2"+Thread.currentThread().name)
    delay(2000)
    println(" delay 后intValue2"+Thread.currentThread().name)
    return 2
}