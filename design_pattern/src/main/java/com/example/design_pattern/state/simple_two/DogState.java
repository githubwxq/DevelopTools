package com.example.design_pattern.state.simple_two;
//实现抽象状态类的接口，比如人有单身狗和恋爱这两种状态：
public class DogState implements PersonState {//单身狗状态

        @Override
        public void movies() {
            System.out.println("一个人偷偷看岛国大片");
        }

        @Override
        public void shopping() {
            //单身狗逛条毛街啊
            //空实现
            System.out.println("not go to shop");
        }
    }