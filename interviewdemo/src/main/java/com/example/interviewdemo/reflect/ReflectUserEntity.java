package com.example.interviewdemo.reflect;

import java.lang.reflect.Method;

public class ReflectUserEntity {

    public static void main(String[] args) throws Exception {
        Class<?> userClass=Class.forName("com.example.interviewdemo.reflect.UserEntity");

        UserEntity userEntity = (UserEntity) userClass.newInstance();

        System.out.println("第一次借钱：");
        int money = userEntity.getMoney();
        System.out.println("实际拿到钱为: " + money);
        System.out.println("------------------------分割线------------------------");

        //第二种方法,（无参的示例：借钱）
        System.out.println("第二次借钱：");
        Method getMoney = userClass.getDeclaredMethod("getMoney");
        getMoney.setAccessible(true );
        Object invoke = getMoney.invoke(userEntity);
        System.out.println("实际拿到钱为：" + invoke);


        //第二种方法,（单个参数的示例：还钱）
        System.out.println("第一次还钱：");
        Method repay1 = userClass.getDeclaredMethod("repay",int.class);//得到方法对象,有参的方法需要指定参数类型
        repay1.setAccessible(true);
        repay1.invoke(userEntity,3000);//执行还钱方法，有参传参
        System.out.println("------------------------分割线------------------------");
        System.out.println("第二次还钱：");

        Method repay = userClass.getDeclaredMethod("repay", String.class, int.class);
        repay.invoke(userEntity,"小王",10000);

    }



}

//
//在调用私有方法时必须用getDeclaredMethod，getDeclaredMethod和getMethod区别如下：
//        getMethod：Returns a object that reflects the specified public member method of the class or interface represented by this object.（只能获取public的方法）
//
//        getDeclaredMethod：Returns a object that reflects the specified declared method of the class or interface represented by this object. （能获取类中所有方法）
//        ————————————————
//        版权声明：本文为CSDN博主「yin1031468524」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/yin1031468524/article/details/61202571