package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.juziwl.uilibrary.dialog.ChooseHeadPicHelp;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.AppManager;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wxq.commonlibrary.constant.GlobalContent.SEVENCLOUDDIR;

public class EditPersonActivity extends BaseActivity {
    @BindView(R.id.iv_heard)
    ImageView ivHeard;
    @BindView(R.id.rl_change_heard)
    RelativeLayout rlChangeHeard;
    @BindView(R.id.rl_acount_detail)
    RelativeLayout rlAcountDetail;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.tv_out)
    TextView tvOut;
    ChooseHeadPicHelp chooseHeadPicHelp;

    public  String picPath;

    public static void navToActivity(Context context,String picPath) {
        Intent intent = new Intent(context, EditPersonActivity.class);
        intent.putExtra("picPath",picPath);
        context.startActivity(intent);
    }



    @Override
    protected void initViews() {
        topHeard.setTitle("编辑账户");
        picPath=getIntent().getStringExtra("picPath");
        LoadingImgUtil.loadimg(picPath,ivHeard,true);
        chooseHeadPicHelp=new ChooseHeadPicHelp(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_person;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.iv_heard, R.id.rl_change_heard, R.id.rl_acount_detail, R.id.rl_change_pwd, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_heard:
                WatchImagesActivity.navToWatchImages(this,picPath,0);
                break;
            case R.id.rl_change_heard:
                // 更换头像 拍照还是 选相册
                chooseHeadPicHelp.getHeardPic(new ChooseHeadPicHelp.HeadPicCallBack() {
                    @Override
                    public void getPath(String path) {
                        picPath=path;
                        LoadingImgUtil.loadimg(picPath,ivHeard,true);
                        uploadPic();
                    }
                });
                break;
            case R.id.rl_acount_detail:
                AccountDetailActivity.navToActivity(this);
                break;
            case R.id.rl_change_pwd:
                //修改密码
                    ChangePwdActivity.navToActivity(this);
                break;
            case R.id.tv_out:
                AllDataCenterManager.getInstance().token="";
                AllDataCenterManager.getInstance().getPublicPreference().storeAutoLogin(0);
                AppManager.getInstance().killActivity(KLookMainActivity.class);
                //前往登录页面
                KlookLoginActivity.naveToActivity(this);
                finish();
                break;
        }
    }

    private void uploadPic() {
        // 1 获取服务token  2调用上传 3 更新头像接口
        showLoadingDialog();
        Api.getInstance()
                .getApiService(KlookApi.class).getQiniuToken()
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    protected void onSuccess(String data) {
                        UploadManager uploadManager=new UploadManager();
                        uploadManager.put(new File(picPath), null, data, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                try {
                                    ToastUtils.showShort("上传得到hash职务"+response.get("hash"));
                                    Log.e("wxq",key+info.toString()+response.get("hash"));
                                    //更新信息
                                    String currentUrlPath=SEVENCLOUDDIR+response.get("hash").toString();
                                    LoadingImgUtil.loadimg(picPath,ivHeard,true);
//                                    UpdateHeardParmer parmer1=new UpdateHeardParmer(response.get("hash").toString(),AllDataCenterManager.getInstance().userInfo.id);
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("head",response.get("hash").toString());
                                    map.put("id",AllDataCenterManager.getInstance().userInfo.id);
                                     Api.getInstance().getApiService(KlookApi.class).updateHead(map)
                                             .compose(ResponseTransformer.handleResult())
                                             .subscribe(new RxSubscriber<Object>() {
                                                 @Override
                                                 protected void onSuccess(Object s) {
                                                     ToastUtils.showShort("头像上传成功");
                                                     AllDataCenterManager.getInstance().userInfo.head=currentUrlPath;
                                                 }

                                                 @Override
                                                 public void onComplete() {
                                                     dismissLoadingDialog();
                                                 }
                                                 @Override
                                                 public void onError(Throwable e) {
                                                     super.onError(e);
                                                     dismissLoadingDialog();
                                                 }
                                             });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },null);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chooseHeadPicHelp.onActivityResult(requestCode,resultCode,data);
    }
}
