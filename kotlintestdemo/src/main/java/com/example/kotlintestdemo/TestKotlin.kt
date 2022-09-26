package com.example.kotlintestapplication

import com.example.kotlintestdemo.Mime
import com.example.kotlintestdemo.Test2
import kotlinx.coroutines.*
import java.io.File
import java.lang.Math.random
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread
import com.example.kotlintestapplication.IntPredicate as Runable

typealias alias = (String,(Int,Int) -> String) -> String
typealias alias2 = () -> Unit
//fun main(){
//
//
//
////    print("1111111")
//    val stringlist= ArrayList<String>()
////    stringlist.add("11111")
////    stringlist.add("22222")
////    stringlist.add("33333")
//
//
////    for (i in 0..10){
////        stringlist+="number $i"
////    }
////
////    stringlist[5]="15"
////    stringlist.forEach{
////        println(it)
////    }
//
//
////    val map=HashMap<String,Int>();
////    map["hello"]=10;
////    print(map["hello"])
////
////    showHelp()
////    var person=Person(18,"wxq")
////    person.age=99
////    person.name="wwww"
////    print(person.name)
////    val func=fun(){
////        print("1111222122")
////    }
////    func.invoke()
////
////
////    val f1={p:Int->
////        "hllo"+p
////    }
////   print(f1(100))
////
//
////    val func:(parmer:Int)->Unit=fun(parmer:Int){
////        print("func11111"+parmer)
////    }
////    func(222222)
////
////    var arrra:IntArray= IntArray(5)
////    arrra[0]=111111
////    arrra[1]=111
////    arrra[2]=444
////    arrra[3]=3333
////
////    arrra.forEach { parmer :Int -> print("func11111"+parmer) }
////
////
////    val isOddNumber = { number: Int ->
////        println("number is $number")
////        number % 2 == 1
////    }
////
////
////     val isOddNumber2 = {  number: Int ->{
////        println("number is $number")
////        number % 2 == 1
////    }}
////
////    println(isOddNumber.invoke(100))
////    val toCharArray = File("KotlinTestActivity").readText().toCharArray()
//
////    println(toCharArray)
//
//
//    // 源代码
////    fun test(a : Int , b : Int) : Int{
////        return a + b
////    }
////
////    fun sum(num1 : Int , num2 : Int) : Int{
////        return num1 + num2
////    }
//
//    // 调用
////    test(10,sum(3,5)) // 结果为：18
//
//    // lambda
////    fun test(a : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
////        return a + b.invoke(3,5)
////    }
//
//    // 调用
////    test(10,{ num1: Int, num2: Int ->  num1 + num2 })  // 结果为：18
//
//
//
//
//
//    // 这里举例一个语言自带的一个高阶函数filter,此函数的作用是过滤掉不满足条件的值。
//    val arr = arrayOf(1,3,5,7,9)
//// 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
////    println(arr.filter { it < 5 }.component1())
////
////    arr.filter { it < 5 }.forEach { println(it) }
////
////
////    fun test(num1 : Int, bool : (Int) -> Boolean) : Int{
////        return if (bool(num1)){ num1 } else 0
////    }
////
////    println(test(10,{it > 5}))
////    println(test(4,{it > 5}))
////
//
//
////    val map = mapOf("key1" to "value1","key2" to "value2","key3" to "value3")
////
////    map.forEach{
////        value -> println("\t $value")
////    }
////
////
////    fun test4(x : Int , y : Int) : Int = x + y
////
////    fun(x:Int)=x+1
////    fun(x : Int , y : Int) : Int = x + y
////    val test1 = fun(x : Int , y : Int) = x + y  // 当返回值可以自动推断出来的时候，可以省略，和函数一样
////    val test2 = fun(x : Int , y : Int) : Int = x + y
////    val test3 = fun(x : Int , y : Int) : Int{
////        return x + y
////    }
////
////
////    var test = Test(1)
//
//
////     var test100= Test2(1,5)
////     var test1001= Test2(1)
////     var test10011= Test2()
////
////
////    val mime = Mime()
////    println("isEmpty = ${mime.isEmpty}")
////    println("num = ${mime.num}")
//
//
////    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
////
////    fun b(a:Int,b:Int): Int{
////        return a+b
////    }
//
////    val sum2 = { x: Int, y: Int -> {
////       return    x + y
////
////    } }
//
//
////    println("num ="+    sum(11,22))
////    println("b ="+"111"  )
////    println("num2 ="+    sum2(11,22))
//
////    、、携程代码
////    GlobalScope.launch {
////        for (i in 1..10) {
////            println("子协程执行第${i}次")
////            println("子协程执行"+Thread().name)
////            val sleepTime = (random() * 1000).toLong()
////            delay(sleepTime)
////        }
////        println("子协程执行结束"+Thread().name)
////    }
////    println("主程序睡眠前"+Thread().name)
////    sleep(10 * 1000)
////    println("主程序结束...")
//
////
////    val job = GlobalScope.launch {
////
////        for (i in 1..10) {
////            println("子协程执行第${i}次")
////            val sleepTime = (random() * 1000).toLong()
////            delay(sleepTime)
////        }
////        println("子协程执行结束")
////    }
////    println(job.isActive)
////    println(job.isCompleted)
////    sleep(10 * 1000)
////    println("主程序结束...")
////    println(job.isCompleted)
//
//
//    GlobalScope.launch(Dispatchers.Main) {
//
//        println("img之前")
//        //指定Lambda在IO线程执行，withContext方法就是一个挂起函数
//        val img = withContext(Dispatchers.IO) {
//            //网络操作获取照片
//            sleep(3000)
//            "wxq"
//        }
//        println("img之后")
//        //返回主线程
//        println(img)
//    }
//
//
//
//
//}





fun main(args: Array<String>?) = runBlocking {
    println("当前线程"+Thread().name)
//    GlobalScope.launch {
//        println("当前线程"+Thread().name)
//        for (i in 1..10) {
//            println("子协程执行第${i}次")
//            val sleepTime = (random() * 1000).toLong()
//            delay(500)
//
//        }
//        println("子协程执行结束")
//        println("当前线程"+Thread().name)
//    }
    GlobalScope.launch() {


        println("img之前"+Thread().name)
        //指定Lambda在IO线程执行，withContext方法就是一个挂起函数
        val img = withContext(Dispatchers.IO) {
            //网络操作获取照片
            println("img当前线程"+Thread().name)
            sleep(3000)
            "wxq"
        }
        println("img外当前线程"+Thread().name)
        println("img之后")
        //返回主线程
        println(img)
        println("img外当前线程"+Thread().name)
        println("img外当前线程"+Thread().name)
        println("img外当前线程"+Thread().name)
        println("img外当前线程"+Thread().name)
    }




//使用
    val daqi = daqi {
        it.toString() +
                "1223123"

    }
    println(daqi)



    var aa=fun(x: Int, y: Int): Int {
        return x + y
    }



    println( "aaa"+aa(1,33333).toString())



    println("开始睡眠...")
    sleep(8000)
//    delay(3 * 1000) // 1️⃣
    println("主程序结束...")


    var dafa:alias;

//不使用sam,正常使用
// 创建一个类的实例
    val isEven = object : Runable {
        override fun accept(i: Int): Boolean {
            return i % 2 == 0
        }

        override fun accept2(i: Int): Boolean {
            TODO("Not yet implemented")
        }
    }
//通过利用 Kotlin 的 SAM 转换，可以改为以下等效代码：
// 通过 lambda 表达式创建一个实例
    val isEven2 = Runnable{


    }

    val isEven23:(Int)->Boolean = { it % 2 == 0 }






    if (isEven23(222)) {
        print("okok")
    }
}


//定义一个接口
 interface IntPredicate {
    fun accept(i: Int): Boolean

    fun accept2(i: Int): Boolean
}



fun daqi(string:(Int) -> String):String{

    return string(1110)
}


fun a(funParam: (Int) -> String): String {
    return funParam(1)
}

fun b(param: Int): String {
    return param.toString()
}





fun abc(a: Runable){
    a.accept(444);
}




class Test constructor(var num : Int=100){
    init {
        num = 5
        println("num = $num")
    }
}



fun showHelp(){
    println("""
        simple
        intput
        out
    """.trimIndent())
}

class Person(age:Int,name:String){
    var age:Int=age
       get(){
        return field
    }
    var name:String=name


}