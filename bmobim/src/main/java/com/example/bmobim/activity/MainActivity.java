package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bmobim.R;
import com.example.bmobim.contract.MainContract;
import com.example.bmobim.fragment.ConversationFragment;
import com.example.bmobim.fragment.DynamicFragment;
import com.example.bmobim.fragment.MySelfFragment;
import com.example.bmobim.presenter.MainActivityPresenter;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.wxq.commonlibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        fragments.add(DynamicFragment.getInstance(""));
        fragments.add(MySelfFragment.getInstance(""));
//        fragments.add(AddressListFragment.getInstance(""));
//        fragments.add(ChooseCityFragment.getInstance(""));
        title.add("会话");
        title.add("空间");
        title.add("我的");
//        title.add("通讯录");
//        title.add("选择城市");
        adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                fragments, title);
        viewpage.setOffscreenPageLimit(5);
        viewpage.setAdapter(adapter);


//        testAddress();


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
