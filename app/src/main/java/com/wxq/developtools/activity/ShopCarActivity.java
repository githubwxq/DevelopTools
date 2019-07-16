package com.wxq.developtools.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.model.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopCarActivity extends BaseActivity {


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


    @Override
    protected void initViews() {
        topHeard.setTitle("购物车").setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shopProductList.setAdapter(new BaseQuickAdapter<ShopCarBean, BaseViewHolder>(R.layout.shop_car_item, shopCarBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, ShopCarBean item) {
                helper.setText(R.id.tv_title, item.productName);
                helper.setText(R.id.tv_time, item.ticketDate);
                helper.setText(R.id.tv_count, item.num);
                helper.setText(R.id.tv_price, item.unitPrice);
                helper.getView(R.id.iv_decrease).setOnClickListener(v -> {
                    if (Integer.valueOf(item.num) == 1) {
                        ToastUtils.showShort("不能再减少了");
                        return;
                    } else {
                        item.num = (Integer.valueOf(item.num) - 1) + "";
                    }
                    notifyDataSetChanged();
                    if (item.isSelect){
                        updataTotalMoney();
                    }
                });
                helper.getView(R.id.iv_indecrease).setOnClickListener(v -> {
                    item.num = (Integer.valueOf(item.num) - 1) + "";
                    if (item.isSelect){
                        updataTotalMoney();
                    }
                    notifyDataSetChanged();
                });
            }
        });
    }

    private void updataTotalMoney() {
        int totalCount=0;
        for (ShopCarBean bean : shopCarBeanList) {
            if (bean.isSelect) {
                totalCount+=Integer.valueOf(bean.getMonney());
            }
        }
        tvTotalMoney.setText("¥"+totalCount);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
