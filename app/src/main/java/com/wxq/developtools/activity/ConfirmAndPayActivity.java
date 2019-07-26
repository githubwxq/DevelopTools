package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
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
import com.wxq.commonlibrary.pay.PayResult;
import com.wxq.commonlibrary.pay.WeiXinPay;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.PayParmer;
import com.wxq.developtools.model.PayResultData;
import com.wxq.developtools.model.PersonInfo;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.ProductPackageVosBean;
import com.wxq.developtools.model.VosBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmAndPayActivity extends BaseActivity {

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
    PullRefreshRecycleView recyclerPeopleView;
    @BindView(R.id.et_liuyan)
    DeletableEditText etLiuyan;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    @BindView(R.id.iv_weixing)
    ImageView ivWeixing;
    @BindView(R.id.rl_weixing)
    RelativeLayout rlWeixing;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.rl_zhifubao)
    RelativeLayout rlZhifubao;


    private XXDialog xxDialog = null;

    private PullRefreshRecycleView dialogRecycleView;

    /**
     * 所有用户根据是否都选择处理
     */
    private List<PersonInfo> allPersonInfoList = new ArrayList<>();


    ProductDetailBean productDetailBean;
    VosBean vosBean;
    int buyNumber;
    ProductPackageVosBean productPackageVosBean;

    String price = "";

    boolean isWeiXingPay = true;


    public static void navToActivity(Context context, ProductPackageVosBean productPackageVosBean, ProductDetailBean productDetailBean, VosBean vosBean, int buyNumber) {
        Intent intent = new Intent(context, ConfirmAndPayActivity.class);
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
        tvPeople.setText(vosBean.getTicketTypeName() + "*" + buyNumber);
        tvTime.setText(vosBean.ticketDate);
        tvChoosePeople.setSelected(true);
        tvAddPeopleLabel.setText("添加" + buyNumber + "位出行人");

        BigDecimal multiply = new BigDecimal(vosBean.sellingPrice).multiply(new BigDecimal(buyNumber));

//        double   f   =   111231.5585;
//
//        BigDecimal   b   =   new   BigDecimal(f);
//
//        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); //保留2位小数
//
        price = multiply.doubleValue() + "";
        tvTotalMoney.setText("¥" + price);
        if (isWeiXingPay) {
            ivWeixing.setSelected(true);
        }else {
            ivWeixing.setSelected(false);
        }

        recyclerPeopleView.setLoadMoreEnable(false).setRefreshEnable(false).setAdapter(new BaseQuickAdapter<PersonInfo, BaseViewHolder>(R.layout.delete_person_item, allPersonInfoList) {
            @Override
            protected void convert(BaseViewHolder helper, PersonInfo item) {
                helper.setText(R.id.tv_name, item.realName);
                helper.setText(R.id.tv_id_card, item.idCard);
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) helper.getConvertView().getLayoutParams();
                if (item.isSelect) {
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT; // 根据具体需求场景设置
                    param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    helper.getConvertView().setVisibility(View.VISIBLE);
                } else {
                    param.height = 0;
                    param.width = 0;
                    helper.getConvertView().setVisibility(View.GONE);
                }
                helper.getConvertView().setLayoutParams(param);
                helper.getView(R.id.iv_delete).setOnClickListener(v -> {
                    item.isSelect = false;
                    dialogRecycleView.notifyDataSetChanged();
                    notifyDataSetChanged();
                });
            }
        });
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
        return R.layout.activity_confirm_order_two;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    public int getChooseNumber(){
        int chooseNumber=0;
        for (PersonInfo personInfo : allPersonInfoList) {
            if (personInfo.isSelect) {
                chooseNumber++;
            }
        }
        return chooseNumber;
    }


    @OnClick({R.id.tv_choose_people, R.id.tv_go_pay,R.id.rl_weixing, R.id.rl_zhifubao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_people:
                showChoosePeopleDialog();
                break;
            case R.id.tv_go_pay:
                //  判断条件是否都完善了
                if (getChooseNumber()<buyNumber) {
                    ToastUtils.showShort("请选择出行人");
                    return;
                }

                goToPay();
                break;
            case R.id.rl_weixing:
                isWeiXingPay=true;
                ivWeixing.setSelected(true);
                ivZhifubao.setSelected(false);
                break;
            case R.id.rl_zhifubao:
                isWeiXingPay=false;
                ivWeixing.setSelected(false);
                ivZhifubao.setSelected(true);
                break;

        }
    }

    private void showChoosePeopleDialog() {
        if (xxDialog == null) {
            xxDialog = new XXDialog(context, R.layout.dialog_choose_person) {
                public RecyclerView recyclerView;

                @Override
                public void convert(DialogViewHolder holder) {
                    holder.getView(com.juziwl.uilibrary.R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 拍照  先请求权限
                            xxDialog.dismiss();
                        }
                    });

                    holder.getView(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 更新 上面recycleview数据
                            recyclerPeopleView.notifyDataSetChanged();
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
                                    // 获取当前已经选中的人数
                                    int count = 0;
                                    for (PersonInfo personInfo : allPersonInfoList) {
                                        if (personInfo.isSelect) {
                                            count++;
                                        }
                                    }
                                    if (!item.isSelect) { // 需要选中的时候判断
                                        if (count >= buyNumber) {
                                            ToastUtils.showShort("人数已满");
                                            return;
                                        }
                                    }
                                    item.isSelect = !item.isSelect;
                                    helper.getView(R.id.iv_choose).setSelected(item.isSelect);
                                    recyclerPeopleView.notifyDataSetChanged();
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
        }
        xxDialog.fromBottom().fullWidth().setCancelAble(true).setCanceledOnTouchOutside(true);
        xxDialog.showDialog();
    }

    public static final int UPDATELIST = 1111;

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (UPDATELIST == action) {
            ToastUtils.showShort("更新弹窗中recycycle数据");
            allPersonInfoList.add(0,(PersonInfo)event.getObject());
            dialogRecycleView.notifyDataSetChanged();
        }
    }


    /**
     * 支付宝支付测试
     */
    private void goToPay() {
        //创建支付参数
        PayParmer payParmer = new PayParmer();
        payParmer.price = price;
        List<PayParmer.PayDetail> dtos = new ArrayList<>();
        PayParmer.PayDetail payDetail = new PayParmer.PayDetail();
        payDetail.num = buyNumber + "";
        payDetail.productId = productDetailBean.id;
        payDetail.productPackageDetailId = vosBean.id;
        payDetail.productPackageId = vosBean.productPackageId;
        payDetail.remark = etLiuyan.getText().toString();
        List<String> userAccountIds = new LinkedList<>();
        for (PersonInfo personInfo : allPersonInfoList) {
            if (personInfo.isSelect) {
                userAccountIds.add(personInfo.id);
            }
        }
        payDetail.userAccountIds = userAccountIds;
        dtos.add(payDetail);
        payParmer.dtos = dtos;
//        PayWithZhiFuBao(payParmer);
        PayWithWeiXing(payParmer);
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
                        Log.e("wxq", "===============" + data);
                        //调用微信支付
                        ToastUtils.showShort(data.toString());
                        WeiXinPay.getInstance().pay(context,data.payInfo,data.weixinPayAppKey);
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
                        ToastUtils.showShort(data.toString());
                        Log.e("wxq", "===============" + data);
                         Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(ConfirmAndPayActivity.this);
                                Map<String, String> result = alipay.payV2(data.payInfo, true);
                                Log.e("wxq", result.toString());
                                UIHandler.getInstance().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        PayResult payResultData = new PayResult(result);
                                        String resultInfo = payResultData.getResult();// 同步返回需要验证的信息
                                        String resultStatus = payResultData.getResultStatus();
                                        if (TextUtils.equals(resultStatus, "9000")) {
                                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                            ToastUtils.showShort("支付成功"+ payResultData);
                                        } else {
                                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                            ToastUtils.showShort("支付失败"+ payResultData);
                                        }
                                    }
                                });
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }
}
