package com.example.uitestdemo.fragment.components.popupwindow;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.popupwindow.easypopup.BasePopup;
import com.wxq.commonlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomPopup extends BasePopup<CustomPopup> {

    private RecyclerView mRecyclerView;

    public static CustomPopup create(){
        return new CustomPopup();
    }
    @Override
    protected void initAttributes() { //调用配置
        setContentView(R.layout.layout_recycle);
        setHeight(SizeUtils.dp2px(200));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusAndOutsideEnable(true);
    }
    // 最后apply的时候会初始化的
    @Override
    protected void initViews(View view, CustomPopup popup) {
        view.setBackgroundColor(Color.BLUE);
        mRecyclerView=findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 4, GridLayoutManager.VERTICAL, false));
        List<String> list =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("data"+i);
        }
        mRecyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycle_item, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.text,item);
            }
        });
    }
}
