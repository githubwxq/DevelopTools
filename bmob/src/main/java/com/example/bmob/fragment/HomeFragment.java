package com.example.bmob.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.contract.HomeContract;
import com.example.bmob.presenter.HomeFragmentPresenter;
import com.juziwl.uilibrary.pullrefreshlayout.PullRefreshLayout;
import com.juziwl.uilibrary.pullrefreshlayout.widget.DiDiHeader;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：
 * 描述: 首页
 *
 * @author:wxq
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R2.id.rv_data)
    RecyclerView rvData;
    @BindView(R2.id.prl)
    PullRefreshLayout prl;
    Unbinder unbinder;

    public static HomeFragment getInstance(String parmer) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        currentType = (String) getArguments().get("PARMER");
        Logger.e("onAttach_______" + currentType);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initViews() {

        prl.setHeaderView(new DiDiHeader(getActivity(), prl));
        prl.setOnRefreshListener(new PullRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                prl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prl.refreshComplete();
                    }
                }, 3000);
            }
        });


        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(new RecyclerView.Adapter() {
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(getLayoutInflater().inflate(R.layout.item_change_clarity, parent, false)) {
                };
            }

            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            public int getItemCount() {
                return 8;
            }
        });

    }

    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        Logger.e("lazyLoadData________" + currentType);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Logger.e("onFragmentResume________" + currentType);
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Logger.e("onFragmentPause________" + currentType);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
