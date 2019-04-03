package com.example.design_pattern.proxy.dynamic_proxy;

import com.example.design_pattern.proxy.static_proxy.Domestic;
import com.example.design_pattern.proxy.static_proxy.People;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/02
 * desc:动态代理 另一种写法
 * version:1.0
 */
public class DomesticProxy {

    //设计一个类变量记住代理类要代理的目标对象  需要代理的目标
    private Domestic ldh = new Domestic();

    public People getProxy() {
        People people = (People) Proxy.newProxyInstance(DomesticProxy.class.getClassLoader(), ldh.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                /**
                 * InvocationHandler接口只定义了一个invoke方法，因此对于这样的接口，我们不用单独去定义一个类来实现该接口，
                 * 而是直接使用一个匿名内部类来实现该接口，new InvocationHandler() {}就是针对InvocationHandler接口的匿名实现类
                 */
                /**
                 * 在invoke方法编码指定返回的代理对象干的工作
                 * proxy : 把代理对象自己传递进来
                 * method：把代理对象当前调用的方法传递进来
                 * args:把方法参数传递进来
                 *
                 * 当调用代理对象的person.sing("冰雨");或者 person.dance("江南style");方法时，
                 * 实际上执行的都是invoke方法里面的代码， !!!!!!!注意
                 * 因此我们可以在invoke方法中使用method.getName()就可以知道当前调用的是代理对象的哪个方法
                 */

                //如果调用的是代理对象的sing方法
                if (method.getName().equals("sing")) {
                    System.out.println("我是他的经纪人，singsingsingsingsing");
                    //已经给钱了，经纪人自己不会唱歌，就只能找刘德华去唱歌！
                    return method.invoke(ldh, args); //代理对象调用真实目标对象的sing方法去处理用户请求
                }

                //如果调用的是代理对象的buy方法
                if (method.getName().equals("buy")) {
                    System.out.println("我是他的经纪人，buybuybuybuybuy");
                    //已经给钱了，经纪人自己不会唱歌，就只能找刘德华去唱歌！
                    return method.invoke(ldh, args); //代理对象调用真实目标对象的sing方法去处理用户请求
                }

                return null;
            }
        });
        return people;
    }


}
