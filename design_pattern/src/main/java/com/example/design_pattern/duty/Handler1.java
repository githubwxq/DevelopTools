package com.example.design_pattern.duty;
/**
 * 具体处理者
 * @author Administrator
 *
 */
public class Handler1 extends Handler{

	@Override
	public void handle(AbstractRequest request) {
		System.out.println("handle1---->"+request.getRequestLevel());
	}

	
	@Override
	public int getHandlerLevel() {
		return 1;
	}

}
