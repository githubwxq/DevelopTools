package com.example.bmobim.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.CityBean;
import com.example.bmobim.bean.CityHeardBean;
import com.example.bmobim.contract.ChooseCityContract;
import com.example.bmobim.presenter.ChooseCityFragmentPresenter;
import com.juziwl.uilibrary.recycler.IndexBar.bean.BaseIndexPinyinBean;
import com.juziwl.uilibrary.recycler.IndexBar.widget.IndexBar;
import com.juziwl.uilibrary.recycler.suspension.SuspensionDecoration;
import com.wxq.commonlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：2019 2 14
 * 描述:选择城市列表页面
 *
 * @author:wxq
 */
public class ChooseCityFragment extends BaseFragment<ChooseCityContract.Presenter> implements ChooseCityContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    Unbinder unbinder;

    private CityQuickAdapter mAdapter;

    //设置给InexBar、ItemDecoration的完整数据集 只关心 部分数据只需要指定接口就行
    private List<BaseIndexPinyinBean> mSourceDatas=new ArrayList<>();

    //头部数据源
    private List<CityHeardBean> mHeaderDatas=new ArrayList<>();

    //主体部分数据源（城市数据）
    private List<CityBean> mBodyDatas=new ArrayList<>();
//
    private LinearLayoutManager mManager;



    public static ChooseCityFragment getInstance(String parmer) {
        ChooseCityFragment fragment = new ChooseCityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_choose_city;
    }
    private SuspensionDecoration mDecoration;

    @Override
    protected void initViews() {
        rv.setLayoutManager(mManager= new LinearLayoutManager(getActivity()));
        rv.setAdapter( mAdapter= new CityQuickAdapter(mBodyDatas));
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new CityHeardBean(locationCity, "定位城市", "定"));
        List<String> recentCitys = new ArrayList<>();
        recentCitys.add("最近城市");
        mHeaderDatas.add(new CityHeardBean(recentCitys, "最近访问城市", "近"));
        List<String> hotCitys = new ArrayList<>();
        hotCitys.add("热门");
        mHeaderDatas.add(new CityHeardBean(hotCitys, "热门城市", "热"));

//        mSourceDatas.addAll(mHeaderDatas);


//        mBodyDatas

        mBodyDatas.add((CityBean) new CityBean("定位",CityBean.sepecialCity).setBaseIndexTag("!"));
        mBodyDatas.add((CityBean) new CityBean("热门",CityBean.sepecialCity).setBaseIndexTag("!"));
        mBodyDatas.add((CityBean) new CityBean("通用",CityBean.sepecialCity).setBaseIndexTag("!"));

        mBodyDatas.add(new CityBean("a阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("b阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("b阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("4阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("9阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("6阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("11阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("b阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("c阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("d阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("e阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("e阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("e阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("f阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("g阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("h阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("u阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("j阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("1阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("2阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("3阿里",CityBean.singleCity));
        mBodyDatas.add(new CityBean("4阿里",CityBean.singleCity));
//        mBodyDatas.add(new CityBean("k阿里"));
//        mBodyDatas.add(new CityBean("i阿里"));
//        mBodyDatas.add(new CityBean("n阿里"));
        mSourceDatas.addAll(mBodyDatas);
        rv.addItemDecoration(mDecoration=new SuspensionDecoration(getActivity(),mSourceDatas));

        indexBar.setmPressedShowTextView(tvSideBarHint).setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager) ;//设置RecyclerView的LayoutManager

//        mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_search_user,null));
//        mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_search_user,null));
//        mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_search_user,null));
        indexBar.setmSourceDatas(mBodyDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mBodyDatas);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected ChooseCityContract.Presenter initPresenter() {
        return new ChooseCityFragmentPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static class CityQuickAdapter extends BaseMultiItemQuickAdapter<CityBean,BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public CityQuickAdapter(List<CityBean> data) {
            super(data);
            addItemType(CityBean.singleCity, R.layout.item_city);
            addItemType(CityBean.sepecialCity, R.layout.item_search_user);

        }

        @Override
        protected void convert(BaseViewHolder helper, CityBean item) {
            switch (helper.getItemViewType()) {
                case CityBean.singleCity:
                    helper.setText(R.id.tvCity, item.city);
                    break;
                case CityBean.sepecialCity:
                    helper.setText(R.id.name,  item.city);
                    break;
            }
        }
    }
}
