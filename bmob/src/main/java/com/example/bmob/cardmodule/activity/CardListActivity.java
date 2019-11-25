package com.example.bmob.cardmodule.activity;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.cardmodule.contract.CardListContract;
import com.example.bmob.cardmodule.presenter.CardListActivityPresenter;
import com.example.module_login.bean.Card;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2019 1 15
 * 描述:帖子列表
 * @author:wxq
 */
public class CardListActivity extends BaseActivity<CardListContract.Presenter> implements CardListContract.View {

    public static final String TITLE = "帖子列表";
    @BindView(R2.id.recyclerView)
    PullRefreshRecycleView recyclerView;


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, CardListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_card_list;
    }


    @Override
    protected void initViews() {
        topHeard.setTitle(TITLE).setRightText("");
        recyclerView.setAdapter(new BaseQuickAdapter<Card, BaseViewHolder>(R.layout.card_item, mPresenter.getCardList()) {
            @Override
            protected void convert(BaseViewHolder helper, Card item) {
                helper.setText(R.id.tv_title, item.content);
                if (item.images != null && item.images.contains("png")) {
                    if (item.images.split(",").length > 0) {
                        LoadingImgUtil.loadimg(item.images.split(",")[0], helper.getView(R.id.iv_image), false);
                    }
                }
            }
        });
    }


    @Override
    protected CardListContract.Presenter initPresent() {
        return new CardListActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

    @Override
    public void updateData(List<Card> cardList) {
        recyclerView.notifyDataSetChanged();
    }
}
