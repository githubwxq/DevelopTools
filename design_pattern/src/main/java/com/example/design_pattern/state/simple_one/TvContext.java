package com.example.design_pattern.state.simple_one;

public class TvContext {
	private TVState tvState=new PowerOff();
	public void setTate(TVState tvState)
	{
		this.tvState=tvState;
	}
	
	public void turnOn()
	{
		setTate(new PowerOn());
		tvState.turnOn();
	}
	
	public void turnOff()
	{
		setTate(new PowerOff());
		tvState.turnOff();
	}
	
	public void nextChannel()
	{
		tvState.nextChannel();
	}
	public void preChannel() {
		tvState.preChannel();
	}
	
}
