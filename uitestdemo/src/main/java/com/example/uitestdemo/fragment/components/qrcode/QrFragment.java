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
 * A simple {@link Fragment} subclass.
 * Use the {@link QrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrFragment extends BaseFragment {

    public static QrFragment newInstance(String param1, String param2) {
        QrFragment fragment = new QrFragment();

        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_qr;
    }

    @Override
    protected void initViews() {





    }
}