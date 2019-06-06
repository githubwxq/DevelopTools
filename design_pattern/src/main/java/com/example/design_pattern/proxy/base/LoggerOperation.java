package com.example.design_pattern.proxy.base;

import java.lang.reflect.Method;

public class LoggerOperation implements IOperation {
    public void end(Method method) {
        System.out.print(method.getName() + " Method end!");
    }
    public void start(Method method) {
        System.out.print(method.getName() + " Method Start!");
    }
}
