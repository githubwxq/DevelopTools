package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.CityVosBean;

import butterknife.BindView;
import butterknife.OnClick;

public class CityActivity extends BaseActivity {


    @BindView(R.id.city_bg)
    ImageView cityBg;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.rt_search_product)
    TextView rtSearchProduct;
    @BindView(R.id.iv_food)
    ImageView ivFood;
    @BindView(R.id.iv_jingdian)
    ImageView ivJingdian;
    @BindView(R.id.iv_waanle)
    ImageView ivWaanle;
    @BindView(R.id.iv_jiaotong)
    ImageView ivJiaotong;

    String cityId;

    public static void navToActivity(Context context, String cityId) {
        Intent intent = new Intent(context, CityActivity.class);
        intent.putExtra("cityId", cityId);
        context.startActivity(intent);
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected void initViews() {
        BarUtils.setStatusBarAlpha(this, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rlTop);
        cityId= getIntent().getStringExtra("cityId");
        getCityInfoById(cityId);
    }

    private void getCityInfoById(String cityId) {
        Api.getInstance().getApiService(KlookApi.class)
                .findCityById(cityId)
                .compose(ResponseTransformer.handleResult())
                .compose(RxTransformer.transformFlowWithLoading(this))
                .subscribe(new RxSubscriber<CityVosBean>() {
                    @Override
                    protected void onSuccess(CityVosBean cityVosBean) {
                        tvCityName.setText(cityVosBean.name);
                        LoadingImgUtil.loadimg(cityVosBean.cover,cityBg,false);
                    }
                });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.city_bg, R.id.iv_back, R.id.rl_top, R.id.tv_city_name, R.id.rt_search_product, R.id.iv_food, R.id.iv_jingdian, R.id.iv_waanle, R.id.iv_jiaotong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.city_bg:

                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_top:
                break;
            case R.id.tv_city_name:
                break;
            case R.id.rt_search_product:
                // 前往城市搜索活动列表页面
               CityProductSearchListActivity.navToActivity(this,cityId,"");
                break;
            case R.id.iv_food:
                CityProductSearchListActivity.navToActivity(this,cityId,"1");
                break;
            case R.id.iv_jingdian:
                CityProductSearchListActivity.navToActivity(this,cityId,"2");
                break;
            case R.id.iv_waanle:
                CityProductSearchListActivity.navToActivity(this,cityId,"3");
                break;
            case R.id.iv_jiaotong:
                CityProductSearchListActivity.navToActivity(this,cityId,"4");

                break;
        }
    }
}
