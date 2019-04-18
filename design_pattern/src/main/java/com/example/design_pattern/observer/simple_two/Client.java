package com.example.design_pattern.observer.simple_two;

public class Client {
	public static void main(String[] args) {
		ConcreteSubject subject=new ConcreteSubject();
		Observer observer1=new ConcreteObserver("1111");
		Observer observer2=new ConcreteObserver("2222");
		Observer observer3=new ConcreteObserver("3333");
		subject.attach(observer1);
		subject.attach(observer2);
		subject.attach(observer3);
		subject.notifyObservers("qweqweqweqwe");
	}
}
