package com.wxq.developtools.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.igexin.sdk.PushManager;
import com.juziwl.uilibrary.BottomNavigationViewHelper;
import com.juziwl.uilibrary.viewpage.NoScrollViewPager;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.UIHandler;
import com.wxq.developtools.GeTuiIntentService;
import com.wxq.developtools.GeTuiPushService;
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

      String cid=  PushManager.getInstance().getClientid(getApplicationContext());

      initGeTui();


    }
    private static final String TAG = "GetuiSdkDemo";
    private void initGeTui() {
//        parseManifests();
        Log.d(TAG, "initializing sdk...");

        PackageManager pkgManager = getPackageManager();


        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    PushManager.getInstance().initialize(context, GeTuiPushService.class);
                    PushManager.getInstance().registerPushIntentService(context, GeTuiIntentService.class);
                    UIHandler.getInstance().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String cid=   PushManager.getInstance().getClientid(context);
                            Log.d(TAG, "cid......."+cid);
                        }
                    },3000);
                }
            }
        });



        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)


        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
//        if (DemoApplication.payloadData != null) {
//            tLogView.append(DemoApplication.payloadData);
//        }

        // cpu 架构
        Log.d(TAG, "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));

        // 检查 so 是否存在
        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
        Log.e(TAG, "libgetuiext2.so exist = " + file.exists());
    }

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
