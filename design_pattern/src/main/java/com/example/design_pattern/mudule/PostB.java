package com.example.design_pattern.mudule;

public class PostB extends Postman {//派送给B先生

        @Override
        protected void call() {//联系收货，实现父类的抽象方法
            System.out.println("联系B先生并送到门口");
        }

        @Override
        protected boolean isSign() {//是否签收,覆盖父类的钩子方法，控制流程的走向
            return false;
        }

        @Override
        protected void refuse() {//拒签，覆盖父类的钩子方法
            System.out.println("拒绝签收：商品不符");
        }
    }