package com.example.kotlintestdemo

class Test2 constructor(num:Int=100){

    init {
        println("number====$num")
    }

    constructor(num: Int,num2:Int):this(num){
        print(num+num2)
    }

}