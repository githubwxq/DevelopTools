package com.example.kotlintestdemo

class Test constructor(var num:Int){

    init {
        num=1000;
        print("number=$num")
    }

}

fun main(){
    var  test=Test(11111);

}


