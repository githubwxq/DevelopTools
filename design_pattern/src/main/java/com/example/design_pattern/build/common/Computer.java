package com.example.design_pattern.build;

/*
    Product（产品类）：要创建的复杂对象。在本类图中，产品类是一个具体的类，而非抽象类。实际编程中，产品类可以是由一个抽象类与它的不同实现组成，也可以是由多个抽象类与他们的实现组成。
    Builder（抽象建造者）：创建产品的抽象接口，一般至少有一个创建产品的抽象方法和一个返回产品的抽象方法。引入抽象类，是为了更容易扩展。
    ConcreteBuilder（实际的建造者）：继承Builder类，实现抽象类的所有抽象方法。实现具体的建造过程和细节。
    Director（指挥者类）：分配不同的建造者来创建产品，统一组装流程。
*/

public class Computer { //定义具体的产品类（Product）：电脑
    private String mCPU;
    private String mMemory;
    private String mHD;

    public void setCPU(String CPU) {
        mCPU = CPU;
    }

    public void setMemory(String memory) {
        mMemory = memory;
    }

    public void setHD(String HD) {
        mHD = HD;
    }
}


