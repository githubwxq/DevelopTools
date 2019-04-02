package com.example.design_pattern.abstrctfactory;

public   class IOSFactory implements IFactory{

	@Override
	public IApi createApi() {
		return new IOSApi();
	}

	
}
