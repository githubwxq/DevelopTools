package com.example.design_pattern.single;

/**
 * 静态内部类[推荐用]
 */
public class InnnerClassSingle {
private InnnerClassSingle()
{
    System.out.println("InnnerClassSingle");
}
private static class SingleHodler{


private static final InnnerClassSingle instance=new InnnerClassSingle();


}

public static InnnerClassSingle getInstance()
{
    System.out.println("getInstance");

return SingleHodler.instance;
}
}


 /*   静态成员变量和静态代码块(static{})只有在类被调用的时候才会初始化。
        这里是指在运行时真正被使用到才会被初始化，如果是在编译时被使用到，但在运行时没有使用到也不会被初始化，
        静态内部类只有当被外部类调用到的时候才会初始化。
        这里也是指在运行时，也就是说不在于你在编辑器中有没有写调用的代码，而是你写的这段调用代码运行时是否会被真正执行到。
        在只使用了外部类，但是没有使用内部类的情况下，内部类里面的东西不会被初始化。
*/
