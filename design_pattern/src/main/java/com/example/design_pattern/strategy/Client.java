package com.example.design_pattern.strategy;
/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/02
 * desc:
 * version:1.0
 */
public class Client {

    public static void main(String[] args) {

        Context context;

        System.out.println("遇到爱逛街的妹子:");
        context=new Context(new ShoppingStrategy());
        context.chase();

        System.out.println("遇到爱看电影的妹子:");
        context=new Context(new MoviesStrategy());
        context.chase();

        System.out.println("遇到吃货妹子:");
        context=new Context(new EattingStrategy());
        context.chase();
    }

}


//        同一个问题具有不同算法时，即仅仅是具体的实现细节不同时，如各种排序算法等等。
//        对客户隐藏具体策略(算法)的实现细节，彼此完全独立；提高算法的保密性与安全性。
//        一个类拥有很多行为，而又需要使用if-else或者switch语句来选择具体行为时。使用策略模式把这些行为独立到具体的策略类中，可以避免多重选择的结构。
