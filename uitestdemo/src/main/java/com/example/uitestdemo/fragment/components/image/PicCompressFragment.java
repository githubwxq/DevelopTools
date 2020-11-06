package com.example.uitestdemo.fragment.components.image;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uitestdemo.R;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

public class PicCompressFragment extends BaseFragment {

    public static PicCompressFragment newInstance() {
        PicCompressFragment fragment = new PicCompressFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_pic_compress;
    }

    @Override
    protected void initViews() {

    }
}