package com.example.kotlintestdemo

import android.view.View
import android.widget.Toast
import java.time.Period

data class User(val name: String, val age: Int){
    val num = 10
    var nickname = "geniusmart"
        get() = field.plus("@email.com")


}


class ContextKotlin{
   lateinit var callBack: (view :String)->Unit

    fun perform(strategy:(view :String)->Unit,name:String){
        strategy(name)
    }
}




fun main(args: Array<String>) {
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack.nickname)
    println(jack)
    println(olderJack)

    val jane=User("jane",333)

    val (time,age)=jane
    println("$time, $age years of age") // prints "Jane, 35 years of age"


    var context1=Context(StrategyA())
    var context2=Context(StrategyB())
    var context3=Context(object :Strategy{
        override fun action() {
            println("context3")
        }

    })
    context1.preform()
    context2.preform()
    context3.preform()


    context1.apply {  }

    ContextKotlin().perform({ println("我答应传入的参数"+it+it.lastChar()) },"wxq")
//    ContextKotlin().perform { println("strategyB") }

//
//    val str = "kotlin"
//    str should startWith("kot")
//    str.length shouldBe 6
    1.days();

    var sum={x:Int,y:Int->x+y}
    var sum2={x:Int,y:Int->{
        println("sum2")

    }}

    printSum { x:Int, y:Int->x+y}
    printSum2 { x:Int, y:Int-> print("x============"+x)}

    println("fanhui"+printSum(sum))
    println("=====================================================================")
    println(  printSum2 { x:Int, y:Int-> print("x============"+x)})


    kotlinDSL {a,b->
         append(a)
         append(b)
         print(this)
    }
}

fun String.lastChar(): Char = this.get(this.length - 1)

fun Int.days():String {
    return "111"
}


fun printSum(sum:(Int,Int)->Int):Int{
    print("sum返回值类型"+sum(0,2))
  return  sum(1,2)
}

fun printSum2(sum:(Int,Int)->Unit){
    print("printSum2返回值类型"+sum(0,2))
    sum(7,9)
}

// 声明接收者  带接收者的 lambda
fun kotlinDSL(block:StringBuilder.(String,String)->Unit){


}


class Outer{

    class Inner{ // 静态成员类，等价于public final class Outer

    }

    inner class OtherInner{ // 非静态成员类

        fun action(){
            // 调用外部类实例
            this@Outer.toString()
        }
    }
}
