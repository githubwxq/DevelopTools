package com.example.kotlintestdemo

class TestMaoHao{

     val x=2;
    fun y() {}
     val z={a:Int->a+2};


  fun test(){
        val a=TestMaoHao::class;
        val b1=TestMaoHao::class.java;
        val b2=TestMaoHao::class.java.kotlin

         val c1=TestMaoHao::javaClass;
         val c2=TestMaoHao()::javaClass;
         val c3=TestMaoHao().javaClass;




    }

}