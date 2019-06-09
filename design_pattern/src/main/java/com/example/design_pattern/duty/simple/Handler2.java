package com.example.design_pattern.duty.simple;

public class Handler2  extends Handler{

	@Override
	public void handle(AbstractRequest request) {
		System.out.println("handle2---->"+request.getRequestLevel());

	}

	@Override
	public int getHandlerLevel() {
		return 2;
	}

}
