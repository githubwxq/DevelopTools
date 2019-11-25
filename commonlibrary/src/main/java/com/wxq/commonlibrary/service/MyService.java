package com.wxq.commonlibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import android.util.Log;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/27
 * desc:最后来学习如何让Service与Activity进行通信。这就需要借助服务的onBind()方法了。
 * 比如希望在MyService里提供一个下载功能，
 * 然后在活动中可以决定何时开始下载，以及随时查看下载进度。
 * 一起学习一下：
 * version:1.0
 */
public class MyService  extends Service {

public  MyBinder binder=new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("wxq","onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("wxq","onBind");

        return binder;
    }



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("wxq","onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("wxq","onDestroy");


    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("wxq","onUnbind");
        return super.onUnbind(intent);
    }

    public  class MyBinder extends Binder{
        public void createProgressDialog(){
            Log.i("wxq","MyBinder====createProgressDialog");


        }
        public  void onProgressUpdate(){
            Log.i("wxq","MyBinder==onProgressUpdate");


        }



    }



}
