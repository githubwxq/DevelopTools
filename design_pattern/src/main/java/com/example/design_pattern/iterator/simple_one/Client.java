package com.example.design_pattern.iterator.simple_one;

//提供一种方法访问一个容器对象中各个元素，而又不需暴露该对象的内部细节。

public class Client {
	public static void main(String[] args) {
		Aggregate aggregate=new DeliveryAggregate();
		aggregate.add("1111");
		aggregate.add("2222");
		aggregate.add("3333");
		aggregate.add("9527");

		Iterator iterator = aggregate.iterator();
		while (iterator.hasNext()){
			String tel = (String) iterator.next();
			System.out.println("当前号码为："+tel);
		}
		System.out.println("后面没有了");
	}
}
