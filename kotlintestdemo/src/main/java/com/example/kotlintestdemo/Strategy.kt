package com.example.kotlintestdemo



class StrategyA : Strategy{
    override fun action() {
        println("StrategyA")
    }
}

class StrategyB : Strategy{
    override fun action() {
        println("StrategyB")
    }
}

class Context(var strategy: Strategy){

    fun preform(){
        strategy.action()
    }

    fun preform2(strage:Strategy){
        strage.action()
    }

}