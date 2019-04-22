package com.example.design_pattern.single;

/**
 * 静态内部类[推荐用]
 */
public class InnnerClassSingle {
private InnnerClassSingle()
{

}
private static class SingleHodler{
private static final InnnerClassSingle instance=new InnnerClassSingle();
}

public static InnnerClassSingle getInstance()
{
return SingleHodler.instance;
}
}
