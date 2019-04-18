package com.example.design_pattern.observer.simple_one;

/**
 * Created by Mjj on 2017/10/6.
 */

public class Client {
    public static void main(String[] args) {
        //创建被观察者
        ConcreteObserverable concreteObserverable = new ConcreteObserverable();

        //创建三个不同的观察者
        Observer observerA = new ConcreateObserver("A");
        Observer observerB = new ConcreateObserver("B");
        Observer observerC = new ConcreateObserver("C");

        //将观察者注册到被观察者中
        concreteObserverable.registerObserver(observerA);
        concreteObserverable.registerObserver(observerB);
        concreteObserverable.registerObserver(observerC);

        //更新被观察者中的数据，当数据更新后，会自动通知所有已注册的观察者
        concreteObserverable.setInfomation(5, 12);
    }

}
