package com.example.kotlintestapplication

import com.example.kotlintestdemo.Mime
import com.example.kotlintestdemo.Test2
import java.io.File
import java.util.*

fun main(){
//    print("1111111")
    val stringlist= ArrayList<String>()
//    stringlist.add("11111")
//    stringlist.add("22222")
//    stringlist.add("33333")


//    for (i in 0..10){
//        stringlist+="number $i"
//    }
//
//    stringlist[5]="15"
//    stringlist.forEach{
//        println(it)
//    }


//    val map=HashMap<String,Int>();
//    map["hello"]=10;
//    print(map["hello"])
//
//    showHelp()
//    var person=Person(18,"wxq")
//    person.age=99
//    person.name="wwww"
//    print(person.name)
//    val func=fun(){
//        print("1111222122")
//    }
//    func.invoke()
//
//
//    val f1={p:Int->
//        "hllo"+p
//    }
//   print(f1(100))
//

//    val func:(parmer:Int)->Unit=fun(parmer:Int){
//        print("func11111"+parmer)
//    }
//    func(222222)
//
//    var arrra:IntArray= IntArray(5)
//    arrra[0]=111111
//    arrra[1]=111
//    arrra[2]=444
//    arrra[3]=3333
//
//    arrra.forEach { parmer :Int -> print("func11111"+parmer) }
//
//
//    val isOddNumber = { number: Int ->
//        println("number is $number")
//        number % 2 == 1
//    }
//
//
//     val isOddNumber2 = {  number: Int ->{
//        println("number is $number")
//        number % 2 == 1
//    }}
//
//    println(isOddNumber.invoke(100))
//    val toCharArray = File("KotlinTestActivity").readText().toCharArray()

//    println(toCharArray)


    // 源代码
//    fun test(a : Int , b : Int) : Int{
//        return a + b
//    }
//
//    fun sum(num1 : Int , num2 : Int) : Int{
//        return num1 + num2
//    }

    // 调用
//    test(10,sum(3,5)) // 结果为：18

    // lambda
//    fun test(a : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
//        return a + b.invoke(3,5)
//    }

    // 调用
//    test(10,{ num1: Int, num2: Int ->  num1 + num2 })  // 结果为：18





    // 这里举例一个语言自带的一个高阶函数filter,此函数的作用是过滤掉不满足条件的值。
    val arr = arrayOf(1,3,5,7,9)
// 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
//    println(arr.filter { it < 5 }.component1())
//
//    arr.filter { it < 5 }.forEach { println(it) }
//
//
//    fun test(num1 : Int, bool : (Int) -> Boolean) : Int{
//        return if (bool(num1)){ num1 } else 0
//    }
//
//    println(test(10,{it > 5}))
//    println(test(4,{it > 5}))
//


//    val map = mapOf("key1" to "value1","key2" to "value2","key3" to "value3")
//
//    map.forEach{
//        value -> println("\t $value")
//    }
//
//
//    fun test4(x : Int , y : Int) : Int = x + y
//
//    fun(x:Int)=x+1
//    fun(x : Int , y : Int) : Int = x + y
//    val test1 = fun(x : Int , y : Int) = x + y  // 当返回值可以自动推断出来的时候，可以省略，和函数一样
//    val test2 = fun(x : Int , y : Int) : Int = x + y
//    val test3 = fun(x : Int , y : Int) : Int{
//        return x + y
//    }
//
//
//    var test = Test(1)


//     var test100= Test2(1,5)
//     var test1001= Test2(1)
//     var test10011= Test2()
//
//
//    val mime = Mime()
//    println("isEmpty = ${mime.isEmpty}")
//    println("num = ${mime.num}")


    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

    fun b(a:Int,b:Int): Int{
        return a+b
    }

//    val sum2 = { x: Int, y: Int -> {
//       return    x + y
//
//    } }


    println("num ="+    sum(11,22))
    println("b ="+   )
//    println("num2 ="+    sum2(11,22))

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