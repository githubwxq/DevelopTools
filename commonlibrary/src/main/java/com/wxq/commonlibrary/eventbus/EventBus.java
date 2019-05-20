package com.wxq.commonlibrary.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/08
 * desc:
 * version:1.0
 */
public class EventBus {
    private Handler handler;
    private ExecutorService executorService;//

    private Map<Object, List<SubscribleMethod>> cacheMap; //保存事件处理总表

    public static EventBus getDefault() {
        return EventBusHolder.instance;
    }

    public static class EventBusHolder {
        private static EventBus instance = new EventBus();
    }

    public EventBus() {
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
        cacheMap = new HashMap<>();
    }


    /**
     * EventBus注册
     *
     * @param activity
     */
    public void register(Object activity) {
        List<SubscribleMethod> list = cacheMap.get(activity);
        if (list == null) {
            //没有的话
            list = getSubscribleMethods(activity);
            cacheMap.put(activity, list);
        }
    }

    private List<SubscribleMethod> getSubscribleMethods(Object activity) {
        List<SubscribleMethod> list = new ArrayList<>();
        //反射处理
        Class<?> classType = activity.getClass();
        while (classType != null) {
            String className = classType.getName();
            //打印类的全名称
            System.out.println(className);
            if (className.startsWith("java.") || className.startsWith("javax.")
                    || className.startsWith("android.")) {
                break;
            }
            //符合要求
            // 该方法是获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。
            Method[] methods = classType.getDeclaredMethods();
            //getMethods(),该方法是获取本类以及父类或者父接口中所有的公共方法(public修饰符修饰的)
            Log.w("wxq", "methods.length==" + methods.length);

            for (Method method : methods) {

                //获取有注解的方法

                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                //监测这个方法符不符合
                Class<?>[] paramtypes = method.getParameterTypes();
                if (paramtypes.length != 1) {
                    throw new RuntimeException("只能有一个参数");
                }
                //符合要求
                ThreadMode threadMode = subscribe.threadMode();
                //打印参数的类型  列入java.lang.String   class的类名不就是参数对象包名
                System.out.println(paramtypes[0].getName());
                SubscribleMethod subscribleMethod = new SubscribleMethod(method, threadMode, paramtypes[0]);
                list.add(subscribleMethod);


            }
            classType = classType.getSuperclass();
        }
        return list;
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public void post(final Object event) {
        //遍历查找到的subscribe 方法
        Set<Object> set = cacheMap.keySet();
        for (Object activity : set) {
            for (SubscribleMethod subscribleMethod : cacheMap.get(activity)) {
                ThreadMode threadMode = subscribleMethod.getThreadMode(); //获取方法运行线程在哪个线程

                switch (threadMode) {
                    case MainThread:
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            invoke(subscribleMethod, activity, event);
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    invoke(subscribleMethod,activity,event);
                                }
                            });
                        }
                        break;

                    case Async:
                        if (Looper.myLooper()==Looper.getMainLooper()){
                            //执行在主线程，切换线程
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Log.w("TAG","子线程预定");
                                    invoke(subscribleMethod,activity,event);
                                }
                            });
                        }else{
                            //执行在子线程
                            invoke(subscribleMethod,activity,event);
                        }



                        break;
                    case PostThread:

                        break;
                    case BackgroundThread:

                        break;


                }


            }
        }
    }


    /* subscribleMethod 参数
     */
    private void invoke(SubscribleMethod subscribleMethod, Object activityClass, Object event) {

        Method method=subscribleMethod.getMethod();

//        event 参数传递
        try {
            if (event.getClass()== subscribleMethod.getaClassType()) {
                method.invoke(activityClass,event);
            }
        } catch (IllegalAccessException e) {
            Log.w("TAG","IllegalAccessException");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.w("TAG","InvocationTargetException");
            e.printStackTrace();
        }

    }


    /**
     * EventBus注销
     *
     * @param activity
     */
    public void unregister(Object activity) {
        List<SubscribleMethod> list = cacheMap.get(activity);
        if (list != null) {
            cacheMap.remove(activity);
        }
    }


}
