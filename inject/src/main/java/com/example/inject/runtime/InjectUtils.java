package com.example.inject.runtime;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/04
 * desc: 第三方容器注入 运行时候直接通过注解加反射完成相关任务
 * version:1.0
 */
public class InjectUtils {

    public static void inject(Context context) {
        injectLayout(context);
        injectView(context);
        injectEvents(context);
    }

    /**
     * 只是调用方法并且赋值给成员变量
     * @param context
     */
    private static void injectLayout(Context context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        //拿到Activity类上面的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
            Method method = null;
            try {
                method = clazz.getMethod("setContentView", int.class);
                //调用这个方法 第一个参数为类的实例，第二个参数为相应函数中的参数
                method.invoke(context, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 只是调用方法
     * @param context
     */
    private static void injectView(Context context) {
        Class<?> aClass = context.getClass();
       //获取到Activity里面所有的成员变量 包含 textView
        Field[] fields=aClass.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject!=null) {
                //拿到id  R.id.text
                int valueId=viewInject.value();
                try {
                    Method method=aClass.getMethod("findViewById",int.class);
                    View view = (View) method.invoke(context, valueId);
//                    类中的成员变量为private,故必须进行此操作
                    field.setAccessible(true);
                    field.set(context,view);//
//                    View view1 = (View) field.get(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        
    }

    /**
     * 注入事件
     * @param context
     */
    private static void injectEvents(Context context) {
        Class<?> clazz=context.getClass();

        Method[] methods = context.getClass().getDeclaredMethods();
        //获取所有的方法
        for (Method method : methods) {
            //遍历方法上的注解
            for (Annotation annotation : method.getAnnotations()) {

                Class<?> anntionType = annotation.annotationType();
                //获取注解的注解   onClick 注解上面的EventBase
                EventBase eventBase=anntionType.getAnnotation(EventBase.class);
                if (eventBase==null) {
                    continue;
                }
                  /*
                开始获取事件三要素  通过反射注入进去
                 1 listenerSetter  返回     setOnClickListener字符串
                 */
                String listenerSetter=eventBase.listenerSetter();
                //得到 listenerType--》 View.OnClickListener.class,
                Class<?> listenerType=eventBase.listenerType();
                 //callMethod--->onClick
                String callMethod=eventBase.callBackMethod();
                //方法名 与方法Method的对应关心
                Map<String,Method> methodMap=new HashMap<>();


                methodMap.put(callMethod,method); //method activity中申明的方法进行管理好为了 呆伙

                try {
                    Method valueMethod=anntionType.getDeclaredMethod("value");
                    //参数传自己annation 自己 不是他的类 类型  获取注解 获取类注解的value值
//                    if (annotation instanceof OnClick) {
//                        OnClick click= (OnClick) annotation;
//                        int[] viewIds= click.value(); //调用方法就是获取值 为了兼容性  OnClick onlongclick 都可以
//                    }
                     int[] viewIds= (int[]) valueMethod.invoke(annotation);
                     System.out.print("viewIds"+viewIds);
                     // 拿到控件id
                    for (int viewId : viewIds) {
                        //通过反射拿到TextView
                        Method findViewById=clazz.getMethod("findViewById",int.class);
                        //拿到对应的控件  然后给控件添加监听器
                        // 此刻的监听器 需要和 activity产生关联 最后点击才能回到activity那边去
                        View view= (View) findViewById.invoke(context,viewId);
                        if(view==null)
                        {
                            continue;
                        }
                        /*
                        listenerSetter  setOnClickLitener
                        listenerType   View.OnClickListener.class
                        获取控件对应的 set方法  第一个参数  方法名注解的注解里面有 第二个 参数类型注解的注解里面也有
                         */
                        Method setOnClickListener=view.getClass().getMethod(listenerSetter,listenerType);
                        // activity 的代理类
                        ListenerInvocationHandler handler=new ListenerInvocationHandler(context,methodMap);

                        // proxyy已经实现了listenerType接口    new Class[]{View.OnClickListener.class} 接口对应的类 产生的代理对象就会实现那个接口 onclik接口
                        Object proxy= Proxy.newProxyInstance
                                (listenerType.getClassLoader(),
                                        new Class[]{listenerType},handler);

                        /**  setOnClickListener method 对象 需要执行对应方法 就调用invoke 传入
                         * 类比 于  textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                        });
                         */

                        setOnClickListener.invoke(view,proxy);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
