package com.example.design_pattern.abstrctfactory.simple_factory;

//具体的产品A
public class ImplA implements Api {
    @Override
    public void operator() {
        System.out.println("完成了一种操作A");
    }
}