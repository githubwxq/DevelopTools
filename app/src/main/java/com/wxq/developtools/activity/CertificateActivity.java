package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

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
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.Certificate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 凭证列表页面
 */
public class CertificateActivity extends BaseActivity implements PullRefreshRecycleView.RefrishAndLoadMoreListener {

    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;

    List<Certificate> List=new ArrayList<>();

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, CertificateActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void initViews() {
        topHeard.setTitle("使用凭证");
        recyclerView.setAdapter(new BaseQuickAdapter<Certificate,BaseViewHolder>(R.layout.item_certificate, List) {
            @Override
            protected void convert(BaseViewHolder helper, Certificate item) {
                helper.setText(R.id.tv_title,"您的订单"+item.voucheDetail+"已完成");
                ImageView imageView= helper.getView(R.id.iv_pic);
                 LoadingImgUtil.loadimg(item.vouchePic,imageView,false);
            }
        },this);
        recyclerView.autoRefresh();
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_certificate;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
    @Override
    public void refrishOrLoadMore(int page, int rows) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageUseVouche(page,rows)
                .compose(RxTransformer.transformFlow(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<BaseListModeData<Certificate>>() {
                    @Override
                    protected void onSuccess(BaseListModeData<Certificate> orderBeanBaseListModeData) {
                        recyclerView.updataData(orderBeanBaseListModeData.list);
                    }
                });
    }
}
