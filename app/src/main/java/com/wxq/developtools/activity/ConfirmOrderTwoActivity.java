package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.juziwl.uilibrary.edittext.DeletableEditText;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.ProductPackageVosBean;
import com.wxq.developtools.model.VosBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrderTwoActivity extends BaseActivity {
    ProductDetailBean productDetailBean;
    VosBean vosBean;
    int buyNumber;
    ProductPackageVosBean productPackageVosBean;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_package_type)
    TextView tvPackageType;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_add_people_label)
    TextView tvAddPeopleLabel;
    @BindView(R.id.tv_choose_people)
    TextView tvChoosePeople;
    @BindView(R.id.recycler_people_view)
    RecyclerView recyclerPeopleView;
    @BindView(R.id.et_liuyan)
    DeletableEditText etLiuyan;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;


    public static void navToActivity(Context context, ProductPackageVosBean productPackageVosBean, ProductDetailBean productDetailBean, VosBean vosBean, int buyNumber) {
        Intent intent = new Intent(context, ConfirmOrderTwoActivity.class);
        intent.putExtra("productDetailBean", productDetailBean);
        intent.putExtra("productPackageVosBean", productPackageVosBean);
        intent.putExtra("vosBean", vosBean);
        intent.putExtra("buyNumber", buyNumber);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        productDetailBean = (ProductDetailBean) getIntent().getSerializableExtra("productDetailBean");
        productPackageVosBean = (ProductPackageVosBean) getIntent().getSerializableExtra("productPackageVosBean");
        vosBean = (VosBean) getIntent().getSerializableExtra("vosBean");
        buyNumber = getIntent().getIntExtra("buyNumber", 0);
        topHeard.setTitle("确认订单页");
        tvTitle.setText(productDetailBean.name);
        tvPackageType.setText(productPackageVosBean.name);
        tvPeople.setText(vosBean.getTicketTypeName()+"*"+buyNumber);
        tvTime.setText(vosBean.ticketDate);
        tvChoosePeople.setSelected(true);
        tvAddPeopleLabel.setText("添加"+buyNumber+"位出行人");
        recyclerPeopleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_order_two;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_choose_people, R.id.tv_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_people:
                break;
            case R.id.tv_go_pay:
                break;
        }
    }
}
