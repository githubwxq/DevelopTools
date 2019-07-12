package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.ProductDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;
    @BindView(R.id.tv_go_buy)
    TextView tvGoBuy;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    ProductDetailBean productDetailBean;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;

    public static void navToActivity(Context context, String id) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
//        BarUtils.setNavBarImmersive(this);
        BarUtils.setStatusBarAlpha(this, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rlTop);
        getData();
    }

    private void getData() {
        String id = getIntent().getStringExtra("id");
        Api.getInstance().getApiService(KlookApi.class)
                .findProductById(id)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<ProductDetailBean>() {
                    @Override
                    protected void onSuccess(ProductDetailBean data) {
                        productDetailBean = data;
                        ToastUtils.showShort(productDetailBean.toString());

                    }
                });
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_product;
    }


    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_shop_car, R.id.tv_add_card, R.id.tv_go_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_collect:
                saveProduct();
                break;
            case R.id.iv_shop_car:
                // 查看购物车

                break;
            case R.id.tv_add_card:
                // 添加到购物车
                break;
            case R.id.tv_go_buy:
                // 前往购买
                break;
        }
    }

    private void saveProduct() {
        if (productDetailBean != null) {
            Api.getInstance()
                    .getApiService(KlookApi.class)
                    .saveProduct(productDetailBean.id)
                    .compose(RxTransformer.transformFlowWithLoading(this))
                    .compose(ResponseTransformer.handleResult())
                    .subscribe(new RxSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            ToastUtils.showShort("收藏成功");
                        }
                    });
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
