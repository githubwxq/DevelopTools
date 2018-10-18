package com.example.uitestdemo;

import android.support.v7.widget.LinearLayoutManager;

import com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper;
import com.juziwl.uilibrary.easycommonadapter.CommonRecyclerAdapter;
import com.juziwl.uilibrary.recycler.xrecyclerview.SuperRecyclerView;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecycleViewTestActivity extends BaseActivity {


    @BindView(R.id.recycle)
    SuperRecyclerView recycle;


    @Override
    protected void initViews() {
        List<String> list=new ArrayList<>();
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        list.add("11");
        recycle.setPullRefreshEnabled(false);
        recycle.setLoadingMoreEnabled(false);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(new CommonRecyclerAdapter(RecycleViewTestActivity.this,R.layout.item_change_clarity,list) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, Object item, int position) {

            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_recycle_view_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
