package com.example.bmobim.presenter;

import com.example.bmobim.contract.ChooseCityContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.fragment.ChooseCityFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ChooseCityFragmentPresenter extends RxPresenter<ChooseCityContract.View> implements ChooseCityContract.Presenter {


    public ChooseCityFragmentPresenter(ChooseCityContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
