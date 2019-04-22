package com.example.design_pattern.abstrctfactory.simple_factory;

//具体的产品B
public class ImplB implements Api {
    @Override
    public void operator() {
        System.out.println("完成了一种操作B");
    }
}
