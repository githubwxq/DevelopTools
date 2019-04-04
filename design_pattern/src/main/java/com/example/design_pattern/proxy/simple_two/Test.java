package com.example.design_pattern.proxy.simple_two;

import java.lang.reflect.Proxy;

public class Test {
//	public static void main(String[] args) {
//		RealSubject realSubject=new RealSubject();
//		Proxy proxy=new Proxy(realSubject);
//		proxy.request();
//	}
	public static void main(String[] args) {
		Subject subject=new RealSubject();
		//实例化IncationHandler
		MyIncationHandler myIncationHandler=new MyIncationHandler(subject);
		/**
		 * 第一个参数  classLoader contextLoader
		 * 第二个参数   接口数组   决定着返回的对象实现了哪些接口
		 * 第三个参数	myIncationHandler 代理时 需要处理具体的操作
		 */
		Subject proxy=(Subject)Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				subject.getClass().getInterfaces(),myIncationHandler);
		proxy.request();
	}
}
