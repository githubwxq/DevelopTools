package com.example.design_pattern.proxy.simple_two;
/**
 * 代理者
 * @author Administrator
 *需要持有一个真实对象
 *代理模式
 *被代理的对象就称为真实对象
 */
public class Proxy1 implements Subject{
	/**
	 * 真实对象
	 */
	private Subject subject;
	
	public Proxy1(Subject subject) {
		this.subject = subject;
	}

	@Override
	public void request() {
		
		System.out.println("begin");
	     subject.request();
	     System.out.println("after");
	}

}
