package com.example.kotlintestdemo

data class User(val name: String, val age: Int)





fun main(args: Array<String>) {
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack)
    println(olderJack)

    val jane=User("jane",333)

    val (time,age)=jane
    println("$time, $age years of age") // prints "Jane, 35 years of age"
}