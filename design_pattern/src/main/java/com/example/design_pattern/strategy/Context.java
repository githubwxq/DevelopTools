package com.example.design_pattern.strategy;
//用来操作不同的策略，不同的妹纸使用不同的策略，随机应变：
public class Context {
        
        private ChaseStragety chaseStragety;//定义抽象策略类
        
        public Context(ChaseStragety chaseStragety) {//构造方法传递具体策略对象过来
            this.chaseStragety = chaseStragety;
        }
        
        public void chase(){//执行具体策略对象的策略
            chaseStragety.chase();
        }
    }