package com.example.kotlintestdemo


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
   
//    var animator=ValueAnimator.ofFloat(100f,0f,100f)
//    animator.doOnCancel {  it.duration}


//    var data=TestData("123123123");
//
//    data.age=8888;
//
//    println("当前age"+data.age.toString())
//
//
    val people= listOf<NewPerson>(

            NewPerson("wxq",5),
            NewPerson("wxqq",30),
                    NewPerson("wxqmm",15)
    )
//    val filter = people.filter { it.age > 18 }
//    filter.forEach { println(it.name+it.age.toString()) }

//
 var newPerson=   NewPerson("wxq",5);
    //变量存储lambda，必须显示指定参数类型
    val getAge = { p: NewPerson -> p.age }
//    people.maxBy(NewPerson::age)
//    println("maxget"+people.maxBy(getAge))//Person(name=jack, age=29)



    //成员引用
    val aaa = Person::age
    print(aaa)
//等价
    val bbbbb = {person:Person ->person.age}
    print("bbbbbbbbbbb"+bbbbb)


    //顶层函数
    fun salute()={
        println("Salute")
          "==========salute=======111111"
    }

    var iii=salute();

    print(salute())

    fun NewPerson.isAdult():Boolean{
       return age>=23;
    }



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



fun  printMessageWithPrefix(message:Collection<String>,preFix:String){
message.forEach { it.toString()+preFix }



}

fun printProblemCounts(respons:Collection<String>){
var clienterror=0;
var serverErrors=0;
    respons.forEach {

        if (it.startsWith("4")) {
                  clienterror++;
        }else{

        }

    }



}
