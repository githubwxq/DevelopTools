package com.juziwl.uilibrary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

public class VideoListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;


    @Override
    protected void initViews() {
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mRecyclerView.setadapte

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_video_list;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
