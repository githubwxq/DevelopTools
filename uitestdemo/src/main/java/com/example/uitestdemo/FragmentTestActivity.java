package com.example.uitestdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.trackpoint.annotation.Background;
import com.example.trackpoint.annotation.UiThread;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.service.MyService;
import com.wxq.commonlibrary.util.UIHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTestActivity extends BaseActivity {

    @BindView(R2.id.viewpage)
    ViewPager viewpage;
    List<Fragment> fragmentList=new ArrayList<>();

    private MyService.MyBinder binder;

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("wxq","onServiceConnected");

            binder=(MyService.MyBinder)service;
            binder.createProgressDialog();
            binder.onProgressUpdate();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("wxq","onServiceDisconnected");
        }
    };






    @Override
    protected void initViews() {
        fragmentList.add(new OneFragment());
//        fragmentList.add(new TestMemoryFragment());
        fragmentList.add(new ViewLocationFragment());
        fragmentList.add(new ViewLifeCycleFragment());
        viewpage.setOffscreenPageLimit(3);
        viewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewpage.setCurrentItem(0);


//        、、测试service

        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(FragmentTestActivity.this,MyService.class);
                bindService(intent,connection,BIND_AUTO_CREATE);
//                startActivity();

//                 gotothread();

            }
        },2000);

        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
             unbindService(connection);
            }
        },20000);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.e("wexcq","我是主线程"+Thread.currentThread().getName());

        gotothread();
    }

    @Background
    private void gotothread() {
          Log.e("wexcq","我是子线程"+Thread.currentThread().getName());

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fragment_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
