package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.dialog.DialogViewHolder;
import com.juziwl.uilibrary.dialog.XXDialog;
import com.juziwl.uilibrary.edittext.DeletableEditText;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.pay.OnPayListener;
import com.wxq.commonlibrary.pay.WeiXinPay;
import com.wxq.commonlibrary.pay.ZFBPay;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.PayParmer;
import com.wxq.developtools.model.PayResultData;
import com.wxq.developtools.model.PersonInfo;
import com.wxq.developtools.model.ShopCarBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付页面 从
 */
public class ConfirmAndPayActivity extends BaseActivity {


    @BindView(R.id.order_list)
    PullRefreshRecycleView orderList;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    DeletableEditText etLiuyan;
    ImageView ivWeixing;
    RelativeLayout rlWeixing;
    ImageView ivZhifubao;
    RelativeLayout rlZhifubao;
    private XXDialog xxDialog = null;

    private PullRefreshRecycleView dialogRecycleView;

    /**
     * 所有用户根据是否都选择处理
     */
    private List<PersonInfo> allPersonInfoList = new ArrayList<>(); // 点击不同列表展示不同的选中效果

    List<ShopCarBean> beanList;

    double price ;

    /**
     * 默认选中微信
     */
    boolean isWeiXingPay = true;


    /**
     * 从购物车过来  和直接过来 需要分装数据
     * @param context
     * @param beanList
     */
    public static void navToActivity(Context context, List<ShopCarBean> beanList) {
        Intent intent = new Intent(context, ConfirmAndPayActivity.class);
        intent.putExtra("beanList", (Serializable) beanList);
        context.startActivity(intent);
    }

    View footView;
    @Override
    protected void initViews() {
        beanList = (List<ShopCarBean>) getIntent().getSerializableExtra("beanList");
        topHeard.setTitle("确认订单页");
        footView = LayoutInflater.from(this).inflate(R.layout.item_confirm_order_foot, null, false);
        etLiuyan=  footView.findViewById(R.id.et_liuyan);
        ivWeixing=  footView.findViewById(R.id.iv_weixing);
        rlWeixing=  footView.findViewById(R.id.rl_weixing);
        ivZhifubao=  footView.findViewById(R.id.iv_zhifubao);
        rlZhifubao=  footView.findViewById(R.id.rl_zhifubao);
        rlZhifubao.setOnClickListener(v -> {
            isWeiXingPay = false;
            ivWeixing.setSelected(false);
            ivZhifubao.setSelected(true);
        });
        rlWeixing.setOnClickListener(v -> {
            isWeiXingPay = true;
            ivWeixing.setSelected(true);
            ivZhifubao.setSelected(false);
        });
        orderList.setAdapter(new BaseQuickAdapter<ShopCarBean,BaseViewHolder>(R.layout.item_confirm_order,beanList) {
            @Override
            protected void convert(BaseViewHolder helper, ShopCarBean item) {
                helper.setText(R.id.tv_title,item.productName);
                helper.setText(R.id.tv_package_type,item.productPackageName);
                helper.setText(R.id.tv_people,item.getTicketTypeName() + "*" + item.num);
                helper.setText(R.id.tv_time,item.ticketDate);
                helper.setText(R.id.tv_add_people_label,"添加" + item.num + "位出行人");
                helper.getView(R.id.tv_choose_people).setSelected(true);
                helper.getView(R.id.tv_choose_people).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showChoosePeopleDialog(item.personList,Integer.valueOf(item.num));
                    }
                });
                PullRefreshRecycleView recycler_people_view=helper.getView(R.id.recycler_people_view);
                recycler_people_view.setNeedEmptyView(false).setAdapter(new BaseQuickAdapter<PersonInfo, BaseViewHolder>(R.layout.delete_person_item, item.personList) {
                    @Override
                    protected void convert(BaseViewHolder helper, PersonInfo item) {
                        helper.setText(R.id.tv_name, item.realName);
                        helper.setText(R.id.tv_id_card, item.idCard);
                        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) helper.getConvertView().getLayoutParams();
                        helper.getConvertView().setLayoutParams(param);
                        helper.getView(R.id.iv_delete).setOnClickListener(v -> {
                            mData.remove(item);
                            dialogRecycleView.notifyDataSetChanged();
                            notifyDataSetChanged();
                        });
                    }
                });
            }
        }).addFootView(footView);
        for (ShopCarBean shopCarBean : beanList) {
            BigDecimal multiply = new BigDecimal(shopCarBean.unitPrice).multiply(new BigDecimal(shopCarBean.num));
            price+=multiply.doubleValue();
        }
        tvTotalMoney.setText("¥" + price);
        if (isWeiXingPay) {
            ivWeixing.setSelected(true);
        } else {
            ivWeixing.setSelected(false);
        }
        getPersonListData();
    }

    /**
     * 获取所有的可添加用户
     */
    private void getPersonListData() {
        // 获取用户数据
        Api.getInstance()
                .getApiService(KlookApi.class)
                .findUserAccountByUserId()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<List<PersonInfo>>() {
                    @Override
                    protected void onSuccess(List<PersonInfo> data) {
                        allPersonInfoList.clear();
                        allPersonInfoList.addAll(data);
                    }
                });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_and_pay;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    public int getChooseNumber() {
        int chooseNumber = 0;
        for (PersonInfo personInfo : allPersonInfoList) {
            if (personInfo.isSelect) {
                chooseNumber++;
            }
        }
        return chooseNumber;
    }


    @OnClick({R.id.tv_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go_pay:
              boolean isPrepare=true;
                for (ShopCarBean shopCarBean : beanList) {
                    if (shopCarBean.personList.size()< Integer.valueOf(shopCarBean.num)) {
                        isPrepare=false;
                    }
                }
                if (isPrepare){
                    goToPay();
                }else {
                    ToastUtils.showShort("请选择出行人");
                }
                break;
        }
    }

    private void showChoosePeopleDialog(List<PersonInfo> personList, int totalPeople) {
        for (PersonInfo info : allPersonInfoList) { //还原
                info.isSelect=false;
        }
        for (PersonInfo personInfo : personList) {
              String hasChooseId= personInfo.id;
              for (PersonInfo info : allPersonInfoList) {
                if (info.id.equals(hasChooseId)) {
                    info.isSelect=true;
                }
            }
        }
            xxDialog = new XXDialog(context, R.layout.dialog_choose_person) {
                public RecyclerView recyclerView;
                @Override
                public void convert(DialogViewHolder holder) {
                    holder.getView(com.juziwl.uilibrary.R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            xxDialog.dismiss();
                        }
                    });
                    holder.getView(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 更新 上面recycleview数据
                            xxDialog.dismiss();
                        }
                    });
                    dialogRecycleView = holder.getView(R.id.recycler_view);
                    dialogRecycleView.setLoadMoreEnable(false).setRefreshEnable(false);
                    dialogRecycleView.setAdapter(new BaseQuickAdapter<PersonInfo, BaseViewHolder>(R.layout.person_item, allPersonInfoList) {
                        @Override
                        protected void convert(BaseViewHolder helper, PersonInfo item) {
                            helper.setText(R.id.tv_name, item.realName);
                            helper.setText(R.id.tv_phone, "联系电话   " + item.contactNumber);
                            helper.setText(R.id.tv_shengfeng, "身份证号   " + item.idCard);
                            helper.getView(R.id.iv_choose).setSelected(item.isSelect);
                            helper.getView(R.id.iv_choose).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!item.isSelect&&personList.size()==totalPeople) {
                                        ToastUtils.showShort("人数已满");
                                        return;
                                    }
                                    item.isSelect = !item.isSelect;
                                    if (item.isSelect) {
                                        personList.add(item);
                                    }else {
                                        personList.remove(item);
                                    }
                                    helper.getView(R.id.iv_choose).setSelected(item.isSelect);
                                    orderList.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    holder.getView(R.id.rl_add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddPeopleActivity.navToActivity(context);
                        }
                    });
                }
            };
        xxDialog.fromBottom().fullWidth().setCancelAble(true).setCanceledOnTouchOutside(true);
        xxDialog.showDialog();
    }

    public static final int UPDATELIST = 1111;

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (UPDATELIST == action) {
            ToastUtils.showShort("更新弹窗中recycycle数据");
            allPersonInfoList.add(0, (PersonInfo) event.getObject());
            dialogRecycleView.notifyDataSetChanged();
        }
    }


    /**
     * 支付宝支付测试
     */
    private void goToPay() {
        //创建支付参数
        PayParmer payParmer = new PayParmer();
        payParmer.price = price+"";
        List<PayParmer.PayDetail> dtos = new ArrayList<>();
        for (ShopCarBean carBean : beanList) {
            PayParmer.PayDetail payDetail = new PayParmer.PayDetail();
            payDetail.num = carBean.num + "";
            payDetail.productId = carBean.productId;
            payDetail.productPackageDetailId = carBean.productPackageDetailId;
            payDetail.productPackageId = carBean.productPackageId;
            payDetail.remark = etLiuyan.getText().toString();
            List<String> userAccountIds = new LinkedList<>();
            for (PersonInfo personInfo : allPersonInfoList) {
                if (personInfo.isSelect) {
                    userAccountIds.add(personInfo.id);
                }
            }
            payDetail.userAccountIds = userAccountIds;
            dtos.add(payDetail);
        }
        payParmer.dtos = dtos;
        if (isWeiXingPay){
            PayWithWeiXing(payParmer);
        }else {
            PayWithZhiFuBao(payParmer);
        }
    }

    private void PayWithWeiXing(PayParmer payParmer) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .wechatpay(payParmer)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<PayResultData>() {
                    @Override
                    protected void onSuccess(PayResultData data) {
//                        Log.e("wxq", "===============" + data);
//                        //调用微信支付
//                        ToastUtils.showShort(data.toString());
                        WeiXinPay.getInstance().pay(context, data.payInfo, data.weixinPayAppKey);
                    }
                });


    }


    private void PayWithZhiFuBao(PayParmer payParmer) {
        // 获取用户数据
        Api.getInstance()
                .getApiService(KlookApi.class)
                .alipay(payParmer)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<PayResultData>() {
                    @Override
                    protected void onSuccess(PayResultData data) {
                        ZFBPay.getInstance().pay(ConfirmAndPayActivity.this, data.payInfo, new OnPayListener() {
                            @Override
                            public void paySuccess(String orderNumber) {
                                ToastUtils.showShort("支付成功");
                                   finish();
                            }
                            @Override
                            public void payFailure(String message) {
                                ToastUtils.showShort("支付失败"+data.toString());
                            }
                        });
//                        ToastUtils.showShort(data.toString());
//                        Log.e("wxq", "===============" + data);

                    }
                });
    }

}
