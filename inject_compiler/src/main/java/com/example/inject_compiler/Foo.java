package com.example.inject_compiler;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/28
 * desc:
 * version:1.0
 */
public class Foo { //type element
    private int a; //varableelement
    private Foo b;

    public Foo(int a) {  // executeableelement
        this.a = a;
    }
}
