package com.example.kotlintestdemo


    val  a =1
    var b =2
    var c:Int = 10

    fun main(args: Array<String>?){
        println("helloworld!")
        println( getScore2("Tom"))   ;
    }



fun getScore(name: String) = when {
    //若name以Tom开头则命中此分支
    name.startsWith("Tom") -> {
        //处理
        println("你好，我是Tom开头的同学")
           "不及格"
    }
    name == "Jim" ->{
         "及格"
    }
    name == "Pony" -> "良好"
    name == "Tony" -> "优秀"
    else -> "名字非法"
}
fun getScore2(name: String) :String{
   return when {
        //若name以Tom开头则命中此分支
        name.startsWith("Tom") -> {
            //处理
            println("你好，我是Tom开头的同学")
            "不及格"
        }
        name == "Jim" ->{
            "及格"
        }
        name == "Pony" -> "良好"
        name == "Tony" -> "优秀"
        else -> "名字非法"
    }
}
