package com.example.design_pattern.proxy;

import com.example.design_pattern.observer.ConcreteObserver;
import com.example.design_pattern.observer.ConcreteSubject;
import com.example.design_pattern.observer.Observer;
import com.example.design_pattern.proxy.dynamic_proxy.DomesticProxy;
import com.example.design_pattern.proxy.dynamic_proxy.DynamicProxy;
import com.example.design_pattern.proxy.static_proxy.Domestic;
import com.example.design_pattern.proxy.static_proxy.Oversea;
import com.example.design_pattern.proxy.static_proxy.People;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/02
 * desc:代理模式的研究
 * Subject（抽象主题类）：接口或者抽象类，声明真实主题与代理的共同接口方法。
 * RealSubject（真实主题类）：也叫做被代理类或被委托类，定义了代理所表示的真实对象，负责具体业务逻辑的执行，客户端可以通过代理类间接的调用真实主题类的方法。
 * Proxy（代理类）：也叫委托类，持有对真实主题类的引用，在其所实现的接口方法中调用真实主题类中相应的接口方法执行。
 * Client（客户端类）：使用代理模式的地方。
 * <p>
 * 静态代理就是在程序运行前就已经存在代理类的字节码文件，
 * 代理类和委托类的关系在运行前就确定了。上面的例子实现就是静态代理。
 * 动态代理类的源码是在程序运行期间根据反射等机制动态的生成，
 * 所以不存在代理类的字节码文件。代理类和委托类的关系是在程序运行时确定。
 */
public class Client {

    public static void main(String[] args) {
//        People domestic = new Domestic();        //创建国内购买人
//        People oversea = new Oversea(domestic);  //创建海外代购类并将domestic作为构造函数传递
//        oversea.buy();

        People domestic = new Domestic();                                 //创建国内购买人
        DynamicProxy proxy = new DynamicProxy(domestic);                  //创建动态代理
        ClassLoader classLoader = domestic.getClass().getClassLoader();   //获取ClassLoader
//        newProxyInstance方法用来返回一个代理对象，这个方法总共有3个参数，ClassLoader loader用来指明生成代理对象使用哪个类装载器，Class<?>[] interfaces用来指明生成哪个对象的代理对象，通过接口指定，
//        InvocationHandler h用来指明产生的这个代理对象要做什么事情。所以我们只需要调用newProxyInstance方法就可以得到某一个对象的代理对象了。
        People oversea = (People) Proxy.newProxyInstance(classLoader, new Class[]{People.class}, proxy); //通过 Proxy 创建海外代购实例 ，实际上通过反射来实现的。
        oversea.buy(100);//调用海外代购的buy()

        //代理對象
        DomesticProxy dome=new DomesticProxy();
        //获得代理对象
        People domeProxy = dome.getProxy();
        domeProxy.buy(1111);
        domeProxy.sing("hello,word");
    }
}

//        在动态代理技术里，由于不管用户调用代理对象的什么方法，
//        都是调用开发人员编写的处理器的invoke方法
//       （这相当于invoke方法拦截到了代理对象的方法调用）。
//        并且，开发人员通过invoke方法的参数，还可以在拦截的同时，知道用户调用的是什么方法，
//        因此利用这两个特性，就可以实现一些特殊需求，
//        例如：拦截用户的访问请求，以检查用户是否有访问权限、动态为某个对象添加额外的功能。

//        什么情况下使用动态代理？
//        1、需要对较难修改的类方法进行功能增加。
//        2、RPC即远程过程调用，通过动态代理的建立一个中间人进行通信。
//        3、实现切面编程(AOP)可以采用动态代理的机制来实现。

/*        原理
        public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h){
        //所有被实现的业务接口
        final Class<?>[] intfs = interfaces.clone();
        //1、寻找或生成指定的代理类：com.sun.proxy.$ProxyX
        Class<?> cl = getProxyClass0(loader, intfs);
        //通过反射类中的Constructor获取其所有构造方法
        final Constructor<?> cons = cl.getConstructor(constructorParams);
        //2、用构造方法创建代理类com.sun.proxy.$ProxyX的实例,并传入InvocationHandler参数
        return cons.newInstance(new Object[]{h});
        }*/
