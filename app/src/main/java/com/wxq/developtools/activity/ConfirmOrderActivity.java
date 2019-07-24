package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.otherview.ShopNumberChooseView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.VosBean;
import com.wxq.developtools.model.PackageBean;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.ProductPackageVosBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmOrderActivity extends BaseActivity {


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

    public static void navToActivity(Context context, ProductDetailBean data) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("productDetailBean", data);
        context.startActivity(intent);
    }

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
     *具体选中的某个套餐  成人 和小孩 中的一个
     */
    VosBean vosBean;
    /**
     *购买数目
     */
    int buyNumber=1;

    /**
     *套餐详情对象
     */
    ProductPackageVosBean productPackageVosBean;

    @Override
    protected void initViews() {
        productDetailBean = (ProductDetailBean) getIntent().getSerializableExtra("productDetailBean");
        //获取套餐详情  获取当前套餐id
        for (ProductPackageVosBean productPackageVo : productDetailBean.productPackageVos) {
            if (productPackageVo.isSelect) {
                packId = productPackageVo.id;
                tvPackageName.setText(productPackageVo.name);
                tvPackageName.setSelected(true);
                productPackageVosBean=productPackageVo;
            }
        }
        topHeard.setTitle("确认订单页");
        tvIsAdult.setSelected(isAdult);
        tvTitle.setText(productDetailBean.name);
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
                        ToastUtils.showShort("获取套餐信息");
                        packageBean = data;
                        updateUi();
                    }
                });
    }

    private void updateUi() {
        if (isAdult) {
            List<VosBean> adultVos = packageBean.adultVos;
            // 展示选择的日期  对应多个
            Log.e("wxq", "adultVos" + adultVos.toString());
        } else {
            List<VosBean> childVos = packageBean.childVos;
            // 展示选择的日期
            Log.e("wxq", "childVos" + childVos.toString());
        }
        // 默认选中第一个
        vosBean=packageBean.adultVos.get(0);
        tvKuCun.setText("库存"+(Integer.valueOf(vosBean.stockNum)-1)+"件");
        shopChooseView.init(buyNumber, Integer.valueOf(vosBean.stockNum), new ShopNumberChooseView.SizeChangeListener() {
            @Override
            public void sizeChange(int currentCount) {
                buyNumber=currentCount;
                tvKuCun.setText("库存"+(Integer.valueOf(vosBean.stockNum)-currentCount)+"件");
            }
        });
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
                //确认订单 前往再次确定页面
                ConfirmOrderTwoActivity.navToActivity(this,productPackageVosBean,productDetailBean,vosBean,buyNumber);
                break;

            case R.id.tv_is_adult:
                isAdult = true;
                tvIsAdult.setSelected(true);
                tvIsChild.setSelected(false);
                break;
            case R.id.tv_is_child:
                isAdult = false;
                tvIsChild.setSelected(true);
                tvIsAdult.setSelected(false);
                break;

        }
    }
}
