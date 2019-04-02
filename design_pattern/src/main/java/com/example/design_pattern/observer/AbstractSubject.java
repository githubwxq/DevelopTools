package com.example.design_pattern.observer;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象被观察者
 * @author Administrator
 */
public class AbstractSubject {
	/**
	 *保存注册观察者对象
	 */
	private List<Observer> list=new ArrayList<>();
	
	/**
	 * 添加观察者
	 * @param observer
	 */
	public void attach(Observer observer)
	{
		list.add(observer);
		
	}
	/**
	 * 删除观察者
	 * @param observer
	 */
	public void detach(Observer observer)
	{
		list.remove(observer);
	}
	/**
	 更新所有注册的观察者
	 * @param content
	 */
	public  void notifyObservers(String content)
	{
		for(Observer observer:list)
		{
			observer.update(content);
		}
	}
}
