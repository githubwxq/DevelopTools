package com.example.bmob.cardmodule.contract;


import com.example.module_login.bean.Card;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface CardListContract {
    interface View extends BaseView {
        void updateData(List<Card> cardList);
    }

    interface Presenter extends BasePresenter<View> {
        public List<Card> getCardList();

    }

}
