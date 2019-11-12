package com.example.uitestdemo;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.skinlibrary.SkinManager;
import com.example.uitestdemo.fragment.VideoViewGuideFragment;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.service.MyService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.example.trackpoint.annotation.Background;
//import com.example.trackpoint.annotation.UiThread;

public class TestUiFragmentActivity extends BaseActivity {

    @BindView(R2.id.viewpage)
    ViewPager viewpage;

     @BindView(R2.id.iv_use_skin)
     ImageView iv_use_skin;



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
        fragmentList.add(new SuperTextViewFragment());
        fragmentList.add(new VideoViewGuideFragment());
        fragmentList.add(new SeatTableFragment());
        fragmentList.add(new TestDispatchEventFragment());
        fragmentList.add(new VlayoutFragment());
        fragmentList.add(new TestMemoryFragment());
        fragmentList.add(new NineGrideFragment());
        fragmentList.add(new ViewLocationFragment());
        fragmentList.add(new ViewLifeCycleFragment());
        viewpage.setOffscreenPageLimit(18);
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


//        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
//                , Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.CAMERA
//        ).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//
//
//
//            }
//        });





//        、、测试service

//        UIHandler.getInstance().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(TestUiFragmentActivity.this,MyService.class);
//                bindService(intent,connection,BIND_AUTO_CREATE);
////                startActivity();
//
////                 gotothread();
//
//            }
//        },2000);

//        UIHandler.getInstance().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//             unbindService(connection);
//            }
//        },20000);

//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        Log.e("wexcq","我是主线程"+Thread.currentThread().getName());
//
//        gotothread();


        iv_use_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载asset文件
                isPifu=!isPifu;
                if (isPifu){
                    selectSkin();
                }else {
                    SkinManager.getInstance().loadSkin("");
                }


            }
        });
    }

    boolean isPifu=false;


    private void selectSkin() {
        File theme = new File(getFilesDir(), "theme");
        if (theme.exists() && theme.isFile()) {
            theme.delete();
        }
        theme.mkdirs();
        File skinFile = new File(theme, "wxq.skin");;
        if (skinFile.exists()) {
            Log.e("SkinActivity", "皮肤已存在,开始换肤");
            return;
        }
        Log.e("SkinActivity", "皮肤不存在,开始下载");
        FileOutputStream fos = null;
        InputStream is = null;
        //临时文件
        File tempSkin = new File(skinFile.getParentFile(), "wxq" + ".temp");
        try {
            fos = new FileOutputStream(tempSkin);
            //假设下载皮肤包
            is = getAssets().open("app-skin-debug.apk");
            byte[] bytes = new byte[10240];
            int len;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }

            //换肤
            SkinManager.getInstance().loadSkin(tempSkin.getAbsolutePath());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempSkin.delete();
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }







    //    @Background
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
