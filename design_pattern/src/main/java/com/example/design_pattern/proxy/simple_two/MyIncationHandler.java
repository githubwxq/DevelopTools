package com.example.design_pattern.proxy.simple_two;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyIncationHandler implements InvocationHandler {
	/**
	 * ��ʵ��������
	 */
	private Object target;
	
	public MyIncationHandler(Object target) {
		this.target = target;
	}
	/**
	 * ����invoke
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before");
		Object result=method.invoke(target, args);
		System.out.println("after");
		return result;
	}

}
