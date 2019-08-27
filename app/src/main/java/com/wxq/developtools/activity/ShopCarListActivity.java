package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCarListActivity extends BaseActivity implements PullRefreshRecycleView.RefrishAndLoadMoreListener {


    @BindView(R.id.shop_product_list)
    PullRefreshRecycleView shopProductList;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_go_buy)
    TextView tvGoBuy;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    int page = 1;
    int rows = 10;


    List<ShopCarBean> shopCarBeanList = new ArrayList<>();


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ShopCarListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("购物车");
        shopProductList.setAdapter(new BaseQuickAdapter<ShopCarBean, BaseViewHolder>(R.layout.shop_car_item, shopCarBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, ShopCarBean item) {
                LoadingImgUtil.loadimg(item.coverUrl, helper.getView(R.id.iv_pic), false);
                helper.setText(R.id.tv_title, item.productName);
                helper.setText(R.id.tv_time, item.ticketDate);
                helper.setText(R.id.tv_count, item.num);
                helper.setText(R.id.tv_type, item.getTicketTypeName());
                helper.setText(R.id.tv_price, "¥" + item.unitPrice);
                if (item.isSelect) {
                    helper.setImageResource(R.id.iv_choose, R.mipmap.choose_icon);
                } else {
                    helper.setImageResource(R.id.iv_choose, R.mipmap.un_choose_icon);
                }
                helper.getView(R.id.iv_decrease).setOnClickListener(v -> {
                    if (Integer.valueOf(item.num) == 1) {
                        ToastUtils.showShort("不能再减少了");
                        return;
                    } else {
                        item.num = (Integer.valueOf(item.num) - 1) + "";
                    }
                    notifyDataSetChanged();
                    updataTotalMoney();
                });
                helper.getView(R.id.iv_indecrease).setOnClickListener(v -> {
                    item.num = (Integer.valueOf(item.num) + 1) + "";
                    updataTotalMoney();
                    notifyDataSetChanged();
                });

                helper.getView(R.id.iv_choose).setOnClickListener(v -> {
                    item.isSelect = !item.isSelect;
                    updataTotalMoney();
                    notifyDataSetChanged();
                });
            }
        }, this);
        shopProductList.autoRefresh();
    }

    private void updataTotalMoney() {
        double totalCount = 0;
        for (ShopCarBean bean : shopCarBeanList) {
            if (bean.isSelect) {
                totalCount += Double.valueOf(bean.getMonney());
            }
        }
        tvTotalMoney.setText("¥" + totalCount);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @Override
    public void refrishOrLoadMore(int page, int rows) {
        // 获取购物车数据  ，，，
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageShopCart(page, rows)
                .compose(RxTransformer.transformFlow(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<BaseListModeData<ShopCarBean>>() {
                    @Override
                    protected void onSuccess(BaseListModeData<ShopCarBean> data) {
                        shopProductList.updataData(data.list);
                    }
                });
    }

    boolean isSelectAll = false;

    @OnClick({R.id.tv_select_all, R.id.tv_go_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_all:
                isSelectAll = !isSelectAll;
                for (ShopCarBean bean : shopCarBeanList) {
                    bean.isSelect = isSelectAll;
                }
                updataTotalMoney();
                shopProductList.notifyDataSetChanged();
                break;
            case R.id.tv_go_buy:
                //前往支付页面 携带购物车信息
                List<ShopCarBean> carBeans = new ArrayList<>();
                for (ShopCarBean bean : shopCarBeanList) {
                    if (bean.isSelect) {
                        carBeans.add(bean);
                    }
                }
                ConfirmAndPayActivity.navToActivity(this,carBeans );
                break;
        }
    }
}
