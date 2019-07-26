package com.wxq.developtools.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.AppManager;
import com.wxq.commonlibrary.util.ToastUtils;

public class WXPayEntryActivity extends FragmentActivity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        api = WXAPIFactory.createWXAPI(this, GlobalContent.WEIXIN_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                ToastUtils.showShort("支付成功");
                sendBroadcast(new Intent("com.Pay"));
                finish();
            } else if (resp.errCode == -1) {
                ToastUtils.showShort("支付失败");
                sendBroadcast(new Intent("com.Pay.error"));
                finish();
            } else if (resp.errCode == -2) {
                ToastUtils.showShort("支付取消");
                sendBroadcast(new Intent("com.Pay.cancle"));
                finish();
            }
        }

    }
}