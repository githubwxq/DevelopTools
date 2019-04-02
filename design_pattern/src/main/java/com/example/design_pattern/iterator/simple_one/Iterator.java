package com.example.design_pattern.iterator.simple_one;

// 创建迭代器接口
public interface Iterator {

        boolean hasNext();    //是否存在下一条记录

        Object next();        //返回当前记录并移到下一条记录
    }


//            Iterator（迭代器接口）：负责定义、访问和遍历元素的接口。
//            ConcreteIterator（具体迭代器类）:实现迭代器接口。
//            Aggregate（容器接口）：定义容器的基本功能以及提供创建迭代器的接口。
//            ConcreteAggregate（具体容器类）：实现容器接口中的功能。
//            Client（客户端类）：即要使用迭代器模式的地方。
