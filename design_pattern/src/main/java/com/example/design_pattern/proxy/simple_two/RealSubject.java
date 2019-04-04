package com.example.design_pattern.proxy.simple_two;

public class RealSubject implements Subject,Serivice{

	@Override
	public void request() {
		System.out.println("真正的角色");
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

}
