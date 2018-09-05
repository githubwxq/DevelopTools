package com.example.uitestdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper;
import com.juziwl.uilibrary.easycommonadapter.CommonRecyclerAdapter;
import com.juziwl.uilibrary.recycler.viewpagerecycle.MySnapHelper;
import com.juziwl.uilibrary.recycler.viewpagerecycle.StartSnapHelper;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.router.RouterContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterContent.UI_MAIN)
public class MainUiTestActivity extends BaseActivity {

    @BindView(R.id.tv_test_ui)
    TextView tvTestUi;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    List<String> mlist = new ArrayList<>();

    @Override
    protected void initViews() {
        tvTestUi.setText("需要顶部栏目");

        topHeard.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitle("UI测试主界面");
        mlist.add("1");
        mlist.add("2");
        mlist.add("3");
        mlist.add("4");
        mlist.add("5");

        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MySnapHelper mLinearSnapHelper = new MySnapHelper();
        mLinearSnapHelper.attachToRecyclerView(rvList);
        rvList.setAdapter(new CommonRecyclerAdapter<String>(this, R.layout.snap_recycle_item, mlist) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                helper.setText(R.id.tv_tip, item);
                if (position == 0) {
                    helper.setBackgroundRes(R.id.tv_tip, R.color.red_50);
                }
                if (position == 1) {
                    helper.setBackgroundRes(R.id.tv_tip, R.color.blue_100);
                }
                if (position == 2) {
                    helper.setBackgroundRes(R.id.tv_tip, R.color.yellow_50);
                }


            }
        });


    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main_ui_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
}
