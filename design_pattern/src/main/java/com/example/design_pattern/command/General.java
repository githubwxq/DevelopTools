package com.example.design_pattern.command;
/**
 * 将军
 * @author Administrator
 *
 */
public class General {
	private Command undoCommand;
	private Command attackCommand;


	public General()
	{
		//构建士兵
		Soldier soldier=new Soldier();
		//建立军队
		Army army=new Army(soldier);
		undoCommand=new UndoCommand(army);
		attackCommand =new AttachCommand(army);
	}
	/**
	 * 皇帝调用大臣攻击方法
	 */
	public void attach()
	{
		attackCommand.excute();
	}
	public  void  undo()
	{
		undoCommand.back();
	}

}