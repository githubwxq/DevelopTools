package com.example.bmobim.presenter;

import com.example.bmobim.bean.Friend;
import com.example.bmobim.contract.ContactContract;
import com.example.bmobim.model.BmobUserModel;
import com.github.promeg.pinyinhelper.Pinyin;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.ContactActivity;
import com.wxq.commonlibrary.util.ToastUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ContactActivityPresenter extends RxPresenter<ContactContract.View> implements ContactContract.Presenter {

    List<Friend> friends = new ArrayList<>();

    public ContactActivityPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
        findFriends();
    }


    @Override
    public void findFriends() {
        BmobUserModel.getInstance().queryFriends(
                new FindListener<Friend>() {
                    @Override
                    public void done(List<Friend> list, BmobException e) {
                        if (e == null) {
                            friends.clear();
                            //添加首字母
                            for (int i = 0; i < list.size(); i++) {
                                Friend friend = list.get(i);
                                String username = friend.getFriendUser().getUsername();
                                if (username != null) {
                                    String pinyin = Pinyin.toPinyin(username.charAt(0));
                                    friend.setPinyin(pinyin.substring(0, 1).toUpperCase());
                                    friends.add(friend);
                                }
                            }
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                        mView.updateRecycleViewData(friends);
                    }
                }
        );

    }
}
