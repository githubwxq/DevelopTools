package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.ProductDetailBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索城市活动详情页面
 */
public class CityProductSearchListActivity extends BaseActivity implements PullRefreshRecycleView.RefrishAndLoadMoreListener {

    String cityId;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rt_search_product)
    EditText rtSearchProduct;
    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;
    List<ProductDetailBean> productDetailBeans = new ArrayList<>();
    String keyWord = "";// 搜索全部默认
    String type = "";// 类型
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    public static void navToActivity(Context context, String cityId, String type) {
        Intent intent = new Intent(context, CityProductSearchListActivity.class);
        intent.putExtra("cityId", cityId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        cityId = getIntent().getStringExtra("cityId");
        type = getIntent().getStringExtra("type");
        recyclerView.setAdapter(new BaseQuickAdapter<ProductDetailBean, BaseViewHolder>(R.layout.item_search_product, productDetailBeans) {
            @Override
            protected void convert(BaseViewHolder helper, ProductDetailBean item) {
                helper.setText(R.id.tv_activity_name, item.name);
                helper.setText(R.id.tv_comment, item.commentNum + "人评论过");
                helper.setText(R.id.tv_buy, item.buyNum + "人购买过");
                helper.setText(R.id.tv_money, "¥" + item.price);
                LoadingImgUtil.loadimg(item.cover, helper.getView(R.id.iv_picture), false);
                helper.getConvertView().setOnClickListener(v -> ProductActivity.navToActivity(context, item.id));
            }
        }, this);
        rtSearchProduct.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        rtSearchProduct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作 刷新数据
                    recyclerView.page = 1;
                    recyclerView.autoRefresh();
                    return true;
                }
                return false;
            }
        });

        if (StringUtils.isEmpty(type)) {
            title.setVisibility(View.GONE);
            llSearch.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.VISIBLE);
            llSearch.setVisibility(View.GONE);
        }
        rtSearchProduct.setText("");
        recyclerView.autoRefresh();
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city_product_list;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void refrishOrLoadMore(int page, int rows) {
        HashMap<String, String> data = new HashMap<>();
        data.put("city", cityId);
        data.put("name", rtSearchProduct.getText().toString());
        data.put("type", type);
        // 调用接口  初始化以及加载更多都走这个方法
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageProductByCondition(page, rows, data)
                .compose(RxTransformer.transformFlow(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<BaseListModeData<ProductDetailBean>>() {
                    @Override
                    protected void onSuccess(BaseListModeData<ProductDetailBean> orderBeanBaseListModeData) {
                        recyclerView.updataData(orderBeanBaseListModeData.list);
                    }
                });
    }
}
