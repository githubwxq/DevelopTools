package com.example.design_pattern.abstrctfactory;

public class AndroidFactory implements IFactory{

	@Override
	public IApi createApi() {
		return new  AndroidApi();
	}

 

}
