package com.example.bmobim.presenter;

import com.example.bmobim.contract.DynamicContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.fragment.DynamicFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class DynamicFragmentPresenter extends RxPresenter<DynamicContract.View> implements DynamicContract.Presenter {


    public DynamicFragmentPresenter(DynamicContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
