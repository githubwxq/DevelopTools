package com.example.design_pattern.duty;

public abstract class Handler {
	public Handler nextHandler;
	/*
	 *
	 */
	public void handRequest(AbstractRequest request)
	{
		//2==2
		if(request.getRequestLevel()==getHandlerLevel())
		{
			handle(request);
		}else {
			if(nextHandler!=null)
			{
				nextHandler.handRequest(request);
			}else {
				System.out.println("----> 所有处理对象 都不能处理 ");
			}
		}

	}

	/**
	 * 具体的处理方法，给子类实现
	 * @param request
	 */

	public abstract void handle(AbstractRequest request);

	/**
	 * 能够处理请求的级别
	 * @return
	 */
	public abstract  int getHandlerLevel();

}


//多个对象处理同一请求时，但是具体由哪个对象去处理需要运行时做判断。
//		具体处理者不明确的情况下，向这组对象提交了一个请求。