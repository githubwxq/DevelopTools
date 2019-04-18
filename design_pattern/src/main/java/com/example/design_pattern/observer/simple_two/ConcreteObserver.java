package com.example.design_pattern.observer.simple_two;

public class ConcreteObserver  implements Observer{
	private String name;
	
	public ConcreteObserver(String name) {
		this.name = name;
	}

	@Override
	public void update(String content) {
		System.out.println(name+":   "+content);
	}
	

}
