package com.example.interviewdemo.bus;

/**
 * Created by Administrator on 2018/4/23.
 */

public class Subscription {

    SubscribeMethod subscribeMethod;
    Object subscribe;

    public Subscription(SubscribeMethod subscribeMethod, Object subscribe) {
        this.subscribeMethod = subscribeMethod;
        this.subscribe = subscribe;
    }

    public SubscribeMethod getSubscribeMethod() {
        return subscribeMethod;
    }

    public Object getSubscribe() {
        return subscribe;
    }
}
