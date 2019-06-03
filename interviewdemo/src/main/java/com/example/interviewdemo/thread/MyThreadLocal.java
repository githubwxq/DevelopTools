package com.example.interviewdemo.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {

    private Map values = Collections.synchronizedMap(new HashMap());

    public Object get() {
        Thread curThread = Thread.currentThread();
        Object o = values.get(curThread);
        if (o == null && !values.containsKey(curThread)) {
            o = initialValue();
            values.put(curThread, o);
        }
        return o;
    }

    public void set(Object newValue) {
        values.put(Thread.currentThread(), newValue);
    }

    public Object initialValue() {
        return null;
    }

}
/*

在线程是活动的并且ThreadLocal对象是可访问的时，该线程就持有一个到该线程局部变量副本的隐含引用，当该线程运行结束后，
        该线程拥有的所有线程局部变量的副本都将失效，并等待垃圾收集器收集。
        　　由于ThreadLocal中可以持有任何类型的对象，所以使用ThreadLocal获取当前线程的值是需要进行强制类型转换。
        但随着J2SE5.0将模版引入，新的支持模版参数的ThreadLocal<T>类将从中受益。也可以减少强制类型转换，并将一些错误检查提前到了编译期，将一定程度地简化ThreadLocal的使用。

        　　ThreadLocal和其它同步机制相比有什么优势呢？ThreadLocal和其它所有的同步机制都是为了解决多线程中的对同一变量的访问冲突，在普通的同步机制中，是通过对象加锁来实现多个线 程对同一变量的安全访问的。这时该变量是多个线程共享的，使用这种同步机制需要很细致地分析在什么时候对变量进行读写，什么时候需要锁定某个对象，
        什么时候释放该对象的锁等等很多。所有这些都是因为多个线程共享了资源造成的。ThreadLocal就从另一个角度来解决多线程的并发访问，
        ThreadLocal会为每一个线程维护一个和该线程绑定的变量的副本，从而隔离了多个线程的数据，每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。ThreadLocal提供了线程安全的共享对象，在编写多线程代码时，可以把不安全的整个变量封装进ThreadLocal，或者把该对象的特定于线程的状态封装进ThreadLocal。
        　　当然ThreadLocal并不能替代同步机制，两者面向的问题领域不同。同步机制是为了同步多个线程对相同资源的并发访问，是为了多个线程之间进行通信的有效方式；而ThreadLocal是隔离多个线程的数据共享，从根本上就不在多个线程之间共享资源（变量），这样当然不需要对多个线程进行同步了。
        所以，如果你需要进行多个线程之间进行通信，则使用同步机制；如果需要隔离多个线程之间的共享冲突，可以使用ThreadLocal，
        这将极大地简化我们的程序，使程序更加易读、简洁。
*/
