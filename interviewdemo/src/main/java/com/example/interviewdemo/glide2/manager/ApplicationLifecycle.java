package com.example.interviewdemo.glide2.manager;


public class ApplicationLifecycle implements Lifecycle {
    @Override
    public void addListener(LifecycleListener listener) {
        listener.onStart();
    }

    @Override
    public void removeListener(LifecycleListener listener) {
    }
}
