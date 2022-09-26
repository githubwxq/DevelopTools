package com.example.kotlintestdemo

object Stringutil {

    fun lettersCount(string: String): Int {
        var count = 0;
        for (char in string) {
            if (char.isLetter()) count++
        }
        return count
    }


}

fun a(){
Stringutil.lettersCount("112");
}