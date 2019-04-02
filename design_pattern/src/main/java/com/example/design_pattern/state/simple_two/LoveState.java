package com.example.design_pattern.state.simple_two;

public class LoveState implements PersonState {//恋爱状态

        @Override
        public void movies() {
            System.out.println("一起上电影院看大片~");
        }

        @Override
        public void shopping() {
            System.out.println("一起愉快的逛街去~");
        }
    }