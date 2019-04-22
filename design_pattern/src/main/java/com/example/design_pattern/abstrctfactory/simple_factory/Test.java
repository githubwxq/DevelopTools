package com.example.design_pattern.abstrctfactory.simple_factory;

public class Test {
    public static void main(String[] args) {
        //通过简单工厂模式创造ImplB对象
        Api api = Factory.create(2);
        api.operator();
    }
}