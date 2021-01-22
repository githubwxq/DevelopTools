package com.example.uitestdemo.fragment.components.qrcode;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.example.uitestdemo.activity.TestBitmapActivity;
import com.juziwl.uilibrary.media.ChooseMediaActivity;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;

/**
 * 从相册选取二维码识别页面
 */
public class QrdiscernFragment extends BaseFragment {

    @BindView(R.id.tv_choose_pic)
    TextView tv_choose_pic;


    public static QrdiscernFragment newInstance() {
        QrdiscernFragment fragment = new QrdiscernFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_qrdiscern;
    }

    @Override
    protected void initViews() {
        // 相册中选择图片 然后识别图片信息
        tv_choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //前往图片列表页面
                Intent intent = new Intent(getActivity(), ChooseMediaActivity.class);
                startActivity(intent);
            }
        });
    }
}