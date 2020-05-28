package com.example.kotlintestdemo

class Mime{
    var  id:String="2123";
    var  name :String="kotlin";
    var  age:Int=111;

  private var test:String=""
    get() = "13132"
    set(value) {field=value}


    // size属性
    private val size = 0

    // 即isEmpty这个属性，是判断该类的size属性是否等于0
    val isEmpty : Boolean
        get() = this.size == 0

    // 另一个例子
    val num = 2
        get() = if (field > 5) 10 else 0

}