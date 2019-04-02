package com.example.design_pattern.command;

public class AttachCommand implements Command {
	private Army army;
	
	public AttachCommand(Army army) {
		this.army = army;
	}

	@Override
	public void excute() {
		army.attack();
	}

	@Override
	public void back() {
		army.back();
	}

}
