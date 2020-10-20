package com.example.uitestdemo.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.example.uitestdemo.activity.DetailActivity;
import com.example.uitestdemo.bean.ItemBean;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.juziwl.uilibrary.recycler.itemdecoration.GridDividerItemDecoration;
import com.juziwl.uilibrary.recycler.layoutmanager.XGridLayoutManager;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ComponentFragment extends BaseFragment {

    @BindView(R.id.recycle)
    PullRefreshRecycleView recycle;

    public static ComponentFragment newInstance() {
        ComponentFragment fragment = new ComponentFragment();
        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recycle_view;
    }

    @Override
    protected void initViews() {
        List<ItemBean> itemBeans = new ArrayList<>();
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));
        itemBeans.add(new ItemBean("textview"));

        recycle.setRefreshEnable(false)
                .setLoadMoreEnable(false)
                .setLayoutManager(new XGridLayoutManager(getActivity(), 3))
                .addItemDecoration(new GridDividerItemDecoration(getActivity(), 3, DensityUtil.dip2px(getActivity(), 3)))
                .setAdapter(new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.layout_widget_item, itemBeans) {
                    @Override
                    protected void convert(BaseViewHolder helper, ItemBean item) {
                        helper.setText(R.id.item_name, item.getName());
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DetailActivity.navToActivity(mContext,item);
//                                startActivity();
                            }
                        });
                    }
                });
    }
}