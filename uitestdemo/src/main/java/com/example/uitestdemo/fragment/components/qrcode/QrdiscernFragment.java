package com.example.uitestdemo.fragment.components.qrcode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uitestdemo.R;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

/**
 * 从相册选取二维码识别页面
 */
public class QrdiscernFragment extends BaseFragment {


    public QrdiscernFragment() {
        // Required empty public constructor
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



    }
}