package com.example.design_pattern.build;

import com.example.design_pattern.build.special.NewComputer;

public class Client {
	public static void main(String[] args) {
		com.example.design_pattern.build.Builder builder = new com.example.design_pattern.build.ConcreteBuilder();//创建建造者实例，（装机人员）
		com.example.design_pattern.build.Director direcror = new com.example.design_pattern.build.Director(builder);//创建指挥者实例，并分配相应的建造者，（老板分配任务）
		direcror.Construct("i7-6700", "三星DDR4", "希捷1T");//组装电脑
		// Builder 模式
		NewComputer newComputer = new NewComputer.Builder()
				.cpu("cpu")
				.screen("screen")
				.memory("memory")
				.mainboard("mainboard")
				.build();
		 System.out.print(newComputer.toString());

	}
}
