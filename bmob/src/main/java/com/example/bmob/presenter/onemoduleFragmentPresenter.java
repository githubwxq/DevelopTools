package com.example.bmob.presenter;

import com.example.bmob.contract.onemoduleContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.fragment.onemoduleFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class onemoduleFragmentPresenter extends RxPresenter<onemoduleContract.View> implements onemoduleContract.Presenter {


    public onemoduleFragmentPresenter(onemoduleContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
