package com.example.bmob.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.contract.BmobHomeContract;
import com.example.bmob.fragment.HomeFragment;
import com.example.bmob.presenter.BmobHomeActivityPresenter;
import com.juziwl.uilibrary.BottomNavigationViewHelper;
import com.juziwl.uilibrary.ninegridview.L;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.router.RouterContent;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建日期：
 * 描述:bmob首页
 *
 * @author:wxq
 */
@Route(path = RouterContent.BMOB_MAIN)
public class BmobHomeActivity extends BaseActivity<BmobHomeContract.Presenter> implements BmobHomeContract.View, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R2.id.layout_pager)
    FrameLayout mFrameLayout;
    @BindView(R2.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R2.id.nav_view)
    NavigationView mNavView;
    @BindView(R2.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private static String TAG = "BmobHomeActivity";
    private MenuItem menuItem;
    private MenuItem mItemWelfare;
    private MenuItem mItemVideo;
    private MenuItem mItemAboutUs;
    private MenuItem mItemLogout;
    private TextView mMUsTv;
    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, BmobHomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_bmob_home;
    }


    @Override
    protected void initViews() {
        BarUtils.setStatusBarVisibility(this,false);
        BarUtils.setNavBarVisibility(this,true);
        initNavigationView();
        initFragment();
        initBottomNavigationView();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(HomeFragment.getInstance("one"));
        mFragmentList.add(HomeFragment.getInstance("two"));
        mFragmentList.add(HomeFragment.getInstance("three"));
        mFragmentList.add(HomeFragment.getInstance("four"));
    }



    private void initNavigationView() {
        //头部布局  登录
        mMUsTv =(TextView) mNavView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        //福利
        mItemWelfare = mNavView.getMenu().findItem(R.id.nav_item_welfare);
        //视频
        mItemVideo = mNavView.getMenu().findItem(R.id.nav_item_video);
        //关于我们
        mItemAboutUs = mNavView.getMenu().findItem(R.id.nav_item_about_us);
        //退出登录
        mItemLogout = mNavView.getMenu().findItem(R.id.nav_item_logout);

        //通过actionbardrawertoggle将toolbar与drawablelayout关联起来
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this ,mDrawerLayout, R.string.open_call_phone, R.string.collapsed_string){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //可以重新侧滑方法,该方法实现侧滑动画,整个布局移动效果
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };

        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
        //设置图片为本身的颜色
        mNavView.setItemIconTintList(null);
        //设置item的点击事件
        mNavView.setNavigationItemSelectedListener(this);
        //头部设置监听
        mMUsTv.setOnClickListener(this);

    }


    private void initBottomNavigationView() {
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        // 预设定进来后,默认显示fragment
        addFragment(R.id.layout_pager, mFragmentList.get(0));
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab_home) {
                addFragment(R.id.layout_pager, mFragmentList.get(0));
            } else if (item.getItemId() == R.id.tab_goods) {
                addFragment(R.id.layout_pager, mFragmentList.get(1));
            } else if (item.getItemId() == R.id.tab_cart) {
                addFragment(R.id.layout_pager, mFragmentList.get(2));
            } else if (item.getItemId() == R.id.tab_self) {
                addFragment(R.id.layout_pager, mFragmentList.get(3));
            }
            return true;
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item == mItemWelfare){
            ToastUtils.showShort("first");
            closeDrawer();
        }else if (item == mItemVideo){
            ToastUtils.showShort("second");
            closeDrawer();
        }else if (item == mItemAboutUs){
            ToastUtils.showShort("third");
            closeDrawer();
        }else if (item == mItemLogout){
            ToastUtils.showShort("four");
            closeDrawer();
        }

        return true;
    }
    /**
     * 关闭侧滑
     */
    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nav_header_login_tv){
            L.d(TAG, "点击了登录");
        }
    }

    /**
     * 显示fragment
     * @param frameLayoutId
     * @param fragment
     */
    private void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mCurrentFragment != null) {
                    transaction.hide(mCurrentFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mCurrentFragment != null) {
                    transaction.hide(mCurrentFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mCurrentFragment = fragment;
            transaction.commit();
        }
    }

    @Override
    protected BmobHomeContract.Presenter initPresent() {
        return new BmobHomeActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

}
