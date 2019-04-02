package com.example.design_pattern.proxy.static_proxy;

public class Domestic implements People {

        @Override
        public void buy(int number) {//具体实现
            System.out.println("guo nei dai li "+number);
        }

    @Override
    public void sing(String name) {
        System.out.println("guo nei dai li "+name);
    }
}