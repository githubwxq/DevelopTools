package com.example.design_pattern.proxy.static_proxy;

// 静态代理
public class Oversea implements People {
        People mPeople;//持有People类的引用

        public Oversea(People people) {
            mPeople = people;
        }

        @Override
        public void buy(int number) {
            System.out.println("hai wai dai li ：buy");
            mPeople.buy(number);//调用了被代理者的buy()方法,
        }

    @Override
    public void sing(String name) {
        System.out.println("hai wai dai li ：sing");
        mPeople.sing(name);//调用了被代理者的buy()方法,
    }
}