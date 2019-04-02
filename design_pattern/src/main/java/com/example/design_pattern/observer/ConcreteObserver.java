package com.example.design_pattern.observer;

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
