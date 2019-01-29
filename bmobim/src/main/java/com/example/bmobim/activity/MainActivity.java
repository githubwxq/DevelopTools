package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.bmobim.R;
import com.example.bmobim.contract.MainContract;
import com.example.bmobim.fragment.ConversationFragment;
import com.example.bmobim.presenter.MainActivityPresenter;
import com.juziwl.uilibrary.viewpage.NoScrollViewPager;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.juziwl.uilibrary.viewpage.adapter.BaseViewPagerAdapter;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    public static final String TITLE = "";
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    BaseFragmentAdapter adapter;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

      List<Fragment>  fragments=new ArrayList<>();
      List<String>  title=new ArrayList<>();

    @Override
    protected void initViews() {
        topHeard.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(MainActivity.this,ContactActivity.class));
            }
        });

        fragments.add(ConversationFragment.getInstance(""));
//        fragments.add(ConversationFragment.getInstance(""));
        title.add("会话");
//        title.add("联系人");
        viewpage.setOffscreenPageLimit(1);
        adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                fragments, title);
        viewpage.setAdapter(adapter);
    }


    @Override
    protected MainContract.Presenter initPresent() {
        return new MainActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

}
