package com.example.kotlintestdemo

class CountingSetBy(val countingSet: MutableCollection<String>):MutableCollection<String>  by countingSet{

    var count = 0

    override fun add(element: String): Boolean {
        count++
        return countingSet.add(element)
    }

    override fun addAll(elements: Collection<String>): Boolean {
        count+=elements.size
        return countingSet.addAll(elements)
    }
}