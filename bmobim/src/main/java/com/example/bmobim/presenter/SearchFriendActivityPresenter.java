package com.example.bmobim.presenter;

import com.example.bmobim.contract.SearchFriendContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.SearchFriendActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class SearchFriendActivityPresenter extends RxPresenter<SearchFriendContract.View> implements SearchFriendContract.Presenter {


    public SearchFriendActivityPresenter(SearchFriendContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
