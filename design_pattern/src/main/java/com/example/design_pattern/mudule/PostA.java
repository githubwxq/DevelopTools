package com.example.design_pattern.mudule;

public class PostA extends Postman {//派送给A先生

        @Override
        protected void call() {//联系收货，实现父类的抽象方法
            System.out.println("联系A先生并送到门口");
        }
    }

