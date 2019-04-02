package com.example.design_pattern.build;

//创建具体的建造者（ConcreteBuilder）:装机人员
public class ConcreteBuilder extends Builder {
    //创建产品实例
    private Computer mComputer = new Computer();

    @Override
    public void buildCPU(String cpu) {//组装CPU
        mComputer.setCPU(cpu);
    }

    @Override
    public void buildMemory(String memory) {//组装内存
        mComputer.setMemory(memory);
    }

    @Override
    public void buildHD(String hd) {//组装硬盘
        mComputer.setHD(hd);
    }

    @Override
    public Computer create() {//返回组装好的电脑
        return mComputer;
    }
}