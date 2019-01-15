package com.example.bmob.cardmodule.presenter;

import com.example.bmob.cardmodule.contract.CardListContract;
import com.example.module_login.bean.Card;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.cardmodule.activity.CardListActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class CardListActivityPresenter extends RxPresenter<CardListContract.View> implements CardListContract.Presenter {


    private List<Card> cardList=new ArrayList<>();

    public CardListActivityPresenter(CardListContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
        //獲取數據

    }
    public List<Card> getCardList() {
        BmobQuery<Card> bmobQuery=new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Card>() {
            @Override
            public void done(List<Card> list, BmobException e) {
                cardList.addAll(list);
                mView.updateData(cardList);
            }
        });
        return cardList;
    }
}
