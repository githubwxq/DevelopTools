package com.wxq.developtools.activity;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDLocation;
//import com.example.im_huanxing.ImActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.juziwl.uilibrary.BottomNavigationViewHelper;
import com.juziwl.uilibrary.activity.BaiduChooseAddressActivity;
import com.juziwl.uilibrary.viewpage.NoScrollViewPager;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.map.baidu.BaiduMapManager;
import com.wxq.commonlibrary.map.baidu.LocationListener;
import com.wxq.commonlibrary.permission.PermissionPageUtils;
import com.wxq.commonlibrary.util.PermissionUtils;
import com.wxq.commonlibrary.util.UIHandler;
//import com.wxq.developtools.GeTuiIntentService;
//import com.wxq.developtools.GeTuiPushService;
import com.wxq.developtools.R;
import com.wxq.developtools.fragment.DestinationFragment;
import com.wxq.developtools.fragment.ExploreFragment;
import com.wxq.developtools.fragment.MySelfFragment;
import com.wxq.developtools.fragment.OrderFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/klook/main")
public class KLookMainActivity extends BaseActivity {

    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    List<Fragment> fragmentList=new ArrayList<>();
    MenuItem prevMenuItem;

    @Override
    protected void initViews() {
        PushAgent.getInstance(context).onAppStart();
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.bottom_explore) {
                viewPage.setCurrentItem(0);
            }
            if (item.getItemId()==R.id.bottom_destination) {
                viewPage.setCurrentItem(1);
            }
            if (item.getItemId()==R.id.bottom_order) {
                viewPage.setCurrentItem(2);
            }
            if (item.getItemId()==R.id.bottom_setting) {
                viewPage.setCurrentItem(3);
            }
            return false;
        });
        navigation.setBackgroundResource(R.color.white);


        fragmentList.add(ExploreFragment.newInstance());
        fragmentList.add(DestinationFragment.newInstance());
        fragmentList.add(OrderFragment.newInstance());
        fragmentList.add(MySelfFragment.newInstance());
        viewPage.setOffscreenPageLimit(fragmentList.size());
        viewPage.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(),fragmentList));
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//      String cid=  PushManager.getInstance().getClientid(getApplicationContext());
//
//      initGeTui();

        PermissionPageUtils permissionPageUtils = new PermissionPageUtils(this);
        permissionPageUtils.jumpToNotificaitonPage();
        initUmengPush();




        BaiduMapManager.getInstance().getBDLocation(this, new LocationListener() {

            public void success(BDLocation success) {
                LogUtil.e(success.getAddress()+"");
            }

            @Override
            public void permissionError() {

            }

            @Override
            public void otherError(String error) {

            }
        });

//        BaiduChooseAddressActivity.navToActivity(this);

//        ImActivity.navToActivity(this);

    }

    private void initUmengPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName("com.wxq.developtools");
        mPushAgent.setNotificaitonOnForeground(false);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);

                mPushAgent.setMessageHandler(new UmengMessageHandler(){
                    @Override
                    public void dealWithCustomMessage(Context context, UMessage uMessage) {
                        Log.e(TAG,"收到：-------->  " +",s1:" + uMessage);
                        super.dealWithCustomMessage(context, uMessage);
                    }

                    @Override
                    public Notification getNotification(Context context, UMessage uMessage) {
                        Log.e(TAG,"getNotification" +",s1:" + uMessage);
                        return super.getNotification(context, uMessage);
                    }
                });

                mPushAgent.setNotificationClickHandler(new UmengNotificationClickHandler(){
                    @Override
                    public void handleMessage(Context context, UMessage uMessage) {
                        Log.e(TAG,"收到：handleMessage " +",s1:" + uMessage);
                        super.handleMessage(context, uMessage);
                    }
                    @Override
                    public void dealWithCustomAction(Context context, UMessage msg) {
//                super.dealWithCustomAction(context, msg);
                        Log.e(TAG,"dealWithCustomAction" +",s1:" + msg);
                    }
                });

                mPushAgent.setDisplayNotificationNumber(3);

            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });


        mPushAgent.setMessageHandler(new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(Context context, UMessage uMessage) {
                Log.e(TAG,"收到：-------->  " +",s1:" + uMessage);
                super.dealWithCustomMessage(context, uMessage);
            }

            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                Log.e(TAG,"getNotification" +",s1:" + uMessage);
                return super.getNotification(context, uMessage);
            }
        });

        mPushAgent.setNotificationClickHandler(new UmengNotificationClickHandler(){
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                Log.e(TAG,"收到：handleMessage " +",s1:" + uMessage);
                super.handleMessage(context, uMessage);
            }
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
//                super.dealWithCustomAction(context, msg);
                Log.e(TAG,"dealWithCustomAction" +",s1:" + msg);
            }
        });

        mPushAgent.setDisplayNotificationNumber(3);


    }

    private static final String TAG = "GetuiSdkDemo";
//    private void initGeTui() {
////        parseManifests();
//        Log.d(TAG, "initializing sdk...");
//
//        PackageManager pkgManager = getPackageManager();
//
//
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                if (aBoolean){
//                    PushManager.getInstance().initialize(context, GeTuiPushService.class);
//                    PushManager.getInstance().registerPushIntentService(context, GeTuiIntentService.class);
//                    UIHandler.getInstance().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            String cid=   PushManager.getInstance().getClientid(context);
//                            Log.d(TAG, "cid......."+cid);
//                        }
//                    },3000);
//                }
//            }
//        });
//
//
//
//        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
//        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
//        // IntentService, 必须在 AndroidManifest 中声明)
//
//
//        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
////        if (DemoApplication.payloadData != null) {
////            tLogView.append(DemoApplication.payloadData);
////        }
//
//        // cpu 架构
//        Log.d(TAG, "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));
//
//        // 检查 so 是否存在
//        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
//        Log.e(TAG, "libgetuiext2.so exist = " + file.exists());
//    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_klook_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
