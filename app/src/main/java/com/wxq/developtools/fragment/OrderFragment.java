package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.activity.ConfirmAndPayActivity;
import com.wxq.developtools.activity.ProductActivity;
import com.wxq.developtools.activity.PublishCommentActivity;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.OrderBean;
import com.wxq.developtools.model.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OrderFragment extends BaseFragment implements PullRefreshRecycleView.RefrishAndLoadMoreListener{

    @BindView(R.id.title_bar)
    TextView titleBar;
    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;
    List<String> list=new ArrayList<>();

    List<OrderBean> orderBeanList=new ArrayList<>();

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initViews() {
        recyclerView.setAdapter(new BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.order_item, orderBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, OrderBean item) {
                LoadingImgUtil.loadimg(item.pic,helper.getView(R.id.iv_product_pic),false);
                helper.setText(R.id.tv_order_time,"下单时间  "+item.createTime);
                helper.setText(R.id.tv_title,item.productName);
                helper.setText(R.id.tv_time,item.orderDate);
                helper.setText(R.id.tv_type,item.getTicketDescribe());
                helper.setText(R.id.tv_price,"¥"+item.price);
                helper.setText(R.id.tv_state,item.getStateName());
                helper.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            //前往评论页面
                        PublishCommentActivity.navToActivity(mContext,item.productId);
                    }
                });
                helper.getView(R.id.tv_state).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (OrderBean.WAITPAY.equals( item.status)||OrderBean.PAYFAIL.equals( item.status)) {
                            // 前往支付页面


                            //确认订单 前往支付页面 重组数据
                            List<ShopCarBean> carBeans = new ArrayList<>();

                            ShopCarBean shopCarBean=new ShopCarBean();
                            // 商品名称
                            shopCarBean.productName=        item.productName;
                            // 套餐名称
                            shopCarBean.productPackageName=item.productPackageName;
                            shopCarBean.ticketType=item.ticketType;
                            shopCarBean.ticketDate=item.ticketDate;
                            shopCarBean.num=item.num+"";
                            shopCarBean.unitPrice=item.unitPrice;
                            shopCarBean.productId=item.productId; //商品id
                            shopCarBean.productPackageId=item.productPackageId; //套餐id
                            shopCarBean.productPackageDetailId=item.productPackageDetailId; //套餐中某个详情id

                            carBeans.add(shopCarBean);

                            ConfirmAndPayActivity.navToActivity(getActivity(),carBeans);

                        }

                    }
                });
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductActivity.navToActivity(mContext,item.productId);
                    }
                });
            }
        }, this);
    }

    private void getData(int page, int rows) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageOrder(page,rows)
                .compose(RxTransformer.transformFlow(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<BaseListModeData<OrderBean>>() {
                    @Override
                    protected void onSuccess(BaseListModeData<OrderBean> orderBeanBaseListModeData) {
                        recyclerView.updataData(orderBeanBaseListModeData.list);
                    }
                });
    }

    @Override
    public void lazyLoadData(View view) {
       recyclerView.autoRefresh();
    }
    @Override
    public void refrishOrLoadMore(int page, int rows) {
        getData(page,rows);
    }

    public void onFragmentResume() {
        ImmersionBar.with(this) .statusBarColor(com.wxq.commonlibrary.R.color.white)
                .statusBarDarkFont(true)
                .statusBarDarkFont(true).init();
        BarUtils.addMarginTopEqualStatusBarHeight(titleBar);
    }
}


//375  667  设计图