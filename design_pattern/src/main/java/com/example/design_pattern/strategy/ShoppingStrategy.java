package com.example.design_pattern.strategy;

public class ShoppingStrategy implements ChaseStragety {

        @Override
        public void chase() {
            System.out.println("一起逛街咯~");
        }
    }