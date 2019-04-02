package com.example.design_pattern.strategy;

public class EattingStrategy implements ChaseStragety {

        @Override
        public void chase() {
            System.out.println("一起吃饭咯~");
        }
    }