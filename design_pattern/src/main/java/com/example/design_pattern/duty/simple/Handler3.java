package com.example.design_pattern.duty.simple;

public class Handler3  extends Handler{

	@Override
	public void handle(AbstractRequest request) {
		System.out.println("handle3---->"+request.getRequestLevel());
	}

	@Override
	public int getHandlerLevel() {
		return 3;
	}

}
