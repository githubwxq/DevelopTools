package com.example.bmobim.presenter;

import com.example.bmobim.contract.AddressListContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.fragment.AddressListFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class AddressListFragmentPresenter extends RxPresenter<AddressListContract.View> implements AddressListContract.Presenter {


    public AddressListFragmentPresenter(AddressListContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
