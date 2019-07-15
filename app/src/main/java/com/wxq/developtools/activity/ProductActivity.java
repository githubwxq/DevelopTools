package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.CommentBean;
import com.wxq.developtools.model.ProductCommentData;
import com.wxq.developtools.model.ProductDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class ProductActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    PullRefreshRecycleView rvList;
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


    @BindView(R.id.rl_top)
    RelativeLayout rlTop;


    ImageView iv_cover_pic;
    TextView tv_title;
    TextView tv_price;
    TextView tv_describe;
    TextView tv_include;
    TextView tv_not_include;
    TextView tv_you_know;
    TextView tv_question;

    RecyclerView recycler_view;

    ProductDetailBean productDetailBean;

    public static void navToActivity(Context context, String id) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    List<CommentBean> commentBeanList = new ArrayList<>();

    private void updateHeard(ProductDetailBean productDetailBean) {
        LoadingImgUtil.loadimg(productDetailBean.cover,iv_cover_pic,false);
        tv_title.setText(productDetailBean.name);
        tv_price.setText("¥"+productDetailBean.price);
        tv_describe.setText(productDetailBean.description);
        tv_include.setText(productDetailBean. priceInclude);
        tv_not_include.setText(productDetailBean. priceUninclude);
        tv_you_know.setText(productDetailBean.k);

    }



    @Override
    protected void initViews() {
        BarUtils.setStatusBarAlpha(this, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rlTop);
        View heardView = LayoutInflater.from(this).inflate(R.layout.product_heard, null, false);
        iv_cover_pic = heardView.findViewById(R.id.iv_cover_pic);
        tv_title = heardView.findViewById(R.id.tv_title);
        tv_price = heardView.findViewById(R.id.tv_price);
        recycler_view = heardView.findViewById(R.id.recycler_view);
        tv_describe = heardView.findViewById(R.id.tv_describe);
        tv_include = heardView.findViewById(R.id.tv_include);
        tv_not_include = heardView.findViewById(R.id.tv_not_include);
        tv_you_know = heardView.findViewById(R.id.tv_you_know);
        tv_question = heardView.findViewById(R.id.tv_question);




        rvList.addHeaderView(heardView, true).setAdapter(new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.product_comment, commentBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {

            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                rows++;
                getComment();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getComment();
            }
        });

        getData();
    }

    int page = 1;
    int rows = 10;
    String id;

    public void getComment() {
        Api.getInstance().getApiService(KlookApi.class)
                .pageProductComment(page, rows, id)
                .compose(ResponseTransformer.handleResult())
                .compose(RxTransformer.transformFlowWithLoading(this)).subscribe(new RxSubscriber<ProductCommentData>() {
            @Override
            protected void onSuccess(ProductCommentData productCommentData) {
                if (page == 1) {
                    commentBeanList.clear();
                } else {
                    if (productCommentData.list.size() < rows) {
                        rvList.setLoadMoreEnable(false);
                    } else {
                        rvList.setLoadMoreEnable(true);
                    }
                }
                commentBeanList.addAll(productCommentData.list);
                rvList.notifyDataSetChanged();
            }
        });

    }


    private void getData() {
        id = getIntent().getStringExtra("id");
        Flowable<ProductDetailBean> productDetailBeanFlowable = Api.getInstance().getApiService(KlookApi.class)
                .findProductById(id)
                .compose(ResponseTransformer.handleResult());
        Flowable<ProductCommentData> collectionDataFlowable = Api.getInstance().getApiService(KlookApi.class)
                .pageProductComment(page, rows, id)
                .compose(ResponseTransformer.handleResult());

        Flowable.zip(productDetailBeanFlowable, collectionDataFlowable, new BiFunction<ProductDetailBean, ProductCommentData, String>() {
            @Override
            public String apply(ProductDetailBean date, ProductCommentData collectionData) throws Exception {
                commentBeanList.addAll(collectionData.list);
                productDetailBean = date;
                return "";
            }
        }).compose(RxTransformer.transformFlowWithLoading(this)).subscribe(new RxSubscriber<String>() {
            @Override
            protected void onSuccess(String s) {
                rvList.notifyDataSetChanged();
                updateHeard(productDetailBean);
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
