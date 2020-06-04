package com.example.kotlintestdemo

import android.animation.ValueAnimator
import androidx.core.animation.doOnCancel


class Child(var name:String):MyInterface{



    override fun bar() {
        println("Childbar")
    }

    override fun foo() {
        super.foo()
        println("ChildFoo")
    }
}


fun Child.myPrint(){
    println(name)

}


fun main(args:Array<String>){
    val c=Child("wxq");
    c.foo()
    c.bar()
   c.myPrint();


    var a = 1
// 模板中的简单名称：
    val s1 = "a is $a"

    a = 2
// 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $a"

    println(s2)


    val items= listOf("apple","orange");
   
    var animator=ValueAnimator.ofFloat(100f,0f,100f)
    animator.doOnCancel {  it.duration}


}

fun maxOf(a:Int,b:Int):Int{
    if(a>b){
        return a;
    }else{
        return b;
    }
}

fun maxOf2(a:Int,b:Int) = if(a>b)a else b


fun parseInt(str: String): Int {
    // ……
    return 1
}

fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // 直接使用 `x * y` 会导致编译错误，因为它们可能为 null
//    if (x != null && y != null) {
        // 在空检测后，x 与 y 会自动转换为非空值（non-nullable）
        println(x * y)
//    }
//    else {
//        println("'$arg1' or '$arg2' is not a number")
//    }
}