package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.PersonInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 出行人列表
 */
public class PeopleListActivity extends BaseActivity {


    @BindView(R.id.people_list_view)
    PullRefreshRecycleView peopleListView;
    List<PersonInfo> personInfoList = new ArrayList<>();

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, PeopleListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("选择出行人");
        topHeard.setRightText("添加出行人").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPeopleActivity.navToActivity(context);
            }
        });
        peopleListView.setLoadMoreEnable(false).setAdapter(new BaseQuickAdapter<PersonInfo, BaseViewHolder>(R.layout.person_item, personInfoList) {
            @Override
            protected void convert(BaseViewHolder helper, PersonInfo item) {
                helper.setText(R.id.tv_name, item.realName);
                helper.setText(R.id.tv_phone, "联系电话   " + item.contactNumber);
                helper.setText(R.id.tv_shengfeng, "身份证号   " + item.idCard);
                helper.getView(R.id.iv_choose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        getData();
    }

    private void getData() {
        // 获取用户数据
        Api.getInstance()
                .getApiService(KlookApi.class)
                .findUserAccountByUserId()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<List<PersonInfo>>() {
                    @Override
                    protected void onSuccess(List<PersonInfo> data) {
                        personInfoList.clear();
                        personInfoList.addAll(data);
                        peopleListView.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_user_list;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
