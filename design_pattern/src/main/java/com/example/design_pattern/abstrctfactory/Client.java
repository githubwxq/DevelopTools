package com.example.design_pattern.abstrctfactory;

public class Client {
	public static void main(String[] args) {
		IFactory factory=new IOSFactory();
		factory.createApi().show();
	}
}
