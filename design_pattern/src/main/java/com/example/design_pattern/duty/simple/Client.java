package com.example.design_pattern.duty.simple;

public class Client {
	public static void main(String[] args) {
		/**
		 *
		 */
		Handler handler1=new Handler1();
		Handler handler2=new Handler2();
		Handler handler3=new Handler3();

		//拼装链子
		handler1.nextHandler=handler2;
		handler2.nextHandler=handler3;

		AbstractRequest request=new Request1();
		//一定要将请求对象  丢给第一个处理
		handler1.handRequest(request);
	}
}
