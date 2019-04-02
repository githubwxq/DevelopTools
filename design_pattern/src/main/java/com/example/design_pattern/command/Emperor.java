package com.example.design_pattern.command;

public class Emperor {
	public static void main(String[] args) {
		General general=new General();
		general.attach();
		general.undo();
		 }
}
