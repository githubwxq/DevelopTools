package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.otherview.ShopNumberChooseView;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.AddShopCarParmer;
import com.wxq.developtools.model.PackageBean;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.ProductPackageVosBean;
import com.wxq.developtools.model.ShopCarBean;
import com.wxq.developtools.model.VosBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmOrderActivity extends BaseActivity {


    public static int RESERVE = 1;
    public static int ADDCARD = 2;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_package_name)
    TextView tvPackageName;
    @BindView(R.id.tv_is_adult)
    TextView tvIsAdult;
    @BindView(R.id.tv_is_child)
    TextView tvIsChild;
    @BindView(R.id.ll_choose_time)
    LinearLayout llChooseTime;
    @BindView(R.id.tv_ku_cun)
    TextView tvKuCun;
    @BindView(R.id.shop_choose_view)
    ShopNumberChooseView shopChooseView;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @BindView(R.id.rv_data_time)
    PullRefreshRecycleView rv_data_time;


    public static void navToActivity(Context context, ProductDetailBean data, int type) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("productDetailBean", data);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    int type;

    /**
     * 商品详情对象
     */
    ProductDetailBean productDetailBean;


    /**
     * 商品对象
     */
    PackageBean packageBean;

    /**
     * 默认成人套餐
     */
    boolean isAdult = true;

    /**
     * 套餐id
     */
    String packId = "";

    /**
     * 具体选中的某个套餐  成人 和小孩 中的一个
     */
    VosBean vosBean;
    /**
     * 购买数目
     */
    int buyNumber = 1;

    /**
     * 套餐对象
     */
    ProductPackageVosBean productPackageVosBean;


    /**
     * 出发日期列表
     */
    public List<VosBean> vosBeanList = new ArrayList<>();

    @Override
    protected void initViews() {
        productDetailBean = (ProductDetailBean) getIntent().getSerializableExtra("productDetailBean");
        type = getIntent().getIntExtra("type", 0);

        //获取套餐详情  获取当前套餐id
        for (ProductPackageVosBean productPackageVo : productDetailBean.productPackageVos) {
            if (productPackageVo.isSelect) {
                packId = productPackageVo.id;
                tvPackageName.setText(productPackageVo.name);
                tvPackageName.setSelected(true);
                productPackageVosBean = productPackageVo;
            }
        }

        tvIsAdult.setSelected(isAdult);
        tvTitle.setText(productDetailBean.name);
        rv_data_time.setLayoutManager(new GridLayoutManager(this, 4))
                .setNeedEmptyView(false)
                .setAdapter(new BaseQuickAdapter<VosBean, BaseViewHolder>(R.layout.item_date, vosBeanList) {
                    @Override
                    protected void convert(BaseViewHolder helper, VosBean item) {
                        if (item.isSelect) {
                            helper.setBackgroundColor(R.id.rl_bg, Color.parseColor("#FFF9EC"));
                        }else {
                            helper.setBackgroundColor(R.id.rl_bg,context.getResources().getColor(R.color.white));
                        }
                        helper.setText(R.id.tv_time,item.ticketDate);
                        helper.setText(R.id.tv_money,"¥"+item.sellingPrice);
                        helper.getView(R.id.rl_bg).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (VosBean mDatum : mData) {
                                    mDatum.isSelect=false;
                                }
                                item.isSelect=true;
                                vosBean=item;
                                tvKuCun.setText("库存" + (Integer.valueOf(vosBean.stockNum) - 1) + "件");
                                shopChooseView.init(buyNumber, Integer.valueOf(vosBean.stockNum), currentCount -> {
                                    buyNumber = currentCount;
                                    tvKuCun.setText("库存" + (Integer.valueOf(vosBean.stockNum) - currentCount) + "件");
                                });
                                notifyDataSetChanged();
                            }
                        });


                    }
                });

        if (ADDCARD == type) {
            topHeard.setTitle("添加购物车页");
            tvConfirm.setText("添加");
        } else {
            topHeard.setTitle("确认订单页");
            tvConfirm.setText("确定");
        }
        getDataById(packId);
    }

    /**
     * 获取套餐详情根据套餐id
     *
     * @param packId
     */
    private void getDataById(String packId) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .findProductPackageDetailById(packId)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<PackageBean>() {
                    @Override
                    protected void onSuccess(PackageBean data) {
                        packageBean = data;
                        updateUi();
                    }
                });
    }

    private void updateUi() {
        vosBeanList.clear();
        for (VosBean adultVo : packageBean.adultVos) {
            adultVo.isSelect=false;
        }
        for (VosBean vo : packageBean.childVos) {
            vo.isSelect=false;
        }
        if (isAdult) {
            vosBeanList.addAll(packageBean.adultVos);
        } else {
            vosBeanList.addAll( packageBean.childVos);
        }
        vosBeanList.get(0).isSelect=true;// 默认选中第一个
        vosBean =  vosBeanList.get(0);
        tvKuCun.setText("库存" + (Integer.valueOf(vosBean.stockNum) - 1) + "件");
        shopChooseView.init(buyNumber, Integer.valueOf(vosBean.stockNum), currentCount -> {
            buyNumber = currentCount;
            tvKuCun.setText("库存" + (Integer.valueOf(vosBean.stockNum) - currentCount) + "件");
        });
        rv_data_time.notifyDataSetChanged();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @OnClick({R.id.tv_title, R.id.tv_package_name, R.id.tv_is_adult, R.id.tv_is_child, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (type == ADDCARD) {
                    //加入购物车 调用接口 这些数据传到后台
                     addToCardShop();
                } else {
                    //确认订单 前往支付页面 重组数据
                    List<ShopCarBean> carBeans = new ArrayList<>();

                    ShopCarBean shopCarBean=new ShopCarBean();
                    // 商品名称
                    shopCarBean.productName=        productDetailBean.name;
                    // 套餐名称
                    shopCarBean.productPackageName=productPackageVosBean.name;
                    shopCarBean.ticketType=vosBean.ticketType;
                    shopCarBean.ticketDate=vosBean.ticketDate;
                    shopCarBean.num=buyNumber+"";
                    shopCarBean.unitPrice=vosBean.sellingPrice;
                    shopCarBean.productId=productDetailBean.id; //商品id
                    shopCarBean.productPackageId=productPackageVosBean.id; //套餐id
                    shopCarBean.productPackageDetailId=vosBean.id; //套餐中某个详情id

                    carBeans.add(shopCarBean);

                    ConfirmAndPayActivity.navToActivity(this,carBeans);





                }
                break;

            case R.id.tv_is_adult:
                isAdult = true;
                tvIsAdult.setSelected(true);
                tvIsChild.setSelected(false);
                updateUi();
                break;
            case R.id.tv_is_child:
                isAdult = false;
                tvIsChild.setSelected(true);
                tvIsAdult.setSelected(false);
                updateUi();
                break;

        }
    }

    private void addToCardShop() {
        AddShopCarParmer parmer=new AddShopCarParmer(buyNumber+"",productDetailBean.id,vosBean.id,productPackageVosBean.id,vosBean.ticketDate,vosBean.ticketType);
        Api.getInstance()
                .getApiService(KlookApi.class)
                .insertShopCart(parmer)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object data) {
                        ToastUtils.showShort("添加到购物车成功");
                        ShopCarListActivity.navToActivity(context);
                    }
                });
    }
}
