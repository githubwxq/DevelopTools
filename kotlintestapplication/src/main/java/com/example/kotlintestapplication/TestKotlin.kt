package com.example.kotlintestapplication

import java.io.File

fun main(args: Array<String>){
//    print("1111111")
    val stringlist=ArrayList<String>()
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
    val toCharArray = File("KotlinTestActivity").readText().toCharArray()

    println(toCharArray)







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