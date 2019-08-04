package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.juziwl.uilibrary.edittext.SuperEditText;
import com.luck.picture.lib.PictureSelectorView;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.DisplayUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.SaveCommentParmer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.functions.Function;

import static com.wxq.commonlibrary.constant.GlobalContent.SEVENCLOUDDIR;

public class PublishCommentActivity extends BaseActivity {
    String productId;
    @BindView(R.id.et_text)
    SuperEditText etText;
    @BindView(R.id.pic_select_view)
    PictureSelectorView picSelectView;

    public static void navToActivity(Context context, String productId) {
        Intent intent = new Intent(context, PublishCommentActivity.class);
        intent.putExtra("productId", productId);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        topHeard.setTitle("评论").setRightText("发布", 15, R.color.red_600).setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishComment();
            }
        });
        productId = getIntent().getStringExtra("productId");
        picSelectView.initData(this, 3, 9, DisplayUtils.getScreenWidth(this) - DensityUtil.dip2px(this, 40));
        picSelectView.setChooseMode(PictureMimeType.ofImage());
    }

    List<LocalMedia> selectList; //选中的图片集合
    List<String> needUpHash = new ArrayList<>(); //选中的图片集合
    int picNum = 0;
    boolean flag = true;

    private void publishComment() {
        selectList = picSelectView.getSelectList();
        if (StringUtils.isEmpty(etText.getText().toString())) {
            ToastUtils.showShort("请输入评论内容");
            return;
        }
        // 获取上传token
        Api.getInstance()
                .getApiService(KlookApi.class).getQiniuToken()
                .compose(ResponseTransformer.handleResult())
                .flatMap(new Function<String, Flowable<List<String>>>() {
                    @Override
                    public Flowable<List<String>> apply(String token) throws Exception {
                        return Flowable.create(emitter -> {
                            // 图片创建集合
                            if (selectList.size() > 0) {
                                needUpHash.clear();
                                upLoadQiNiu(selectList.get(picNum), token, emitter);
                            } else {
                                emitter.onNext(new ArrayList<>());
                                emitter.onComplete();
                            }
                        }, BackpressureStrategy.ERROR);
                    }
                }).flatMap(pics -> {
                    SaveCommentParmer saveCommentParmer = new SaveCommentParmer();
                    saveCommentParmer.content = etText.getText().toString();
                    saveCommentParmer.productId = productId;
                    saveCommentParmer.picUrls = pics;
                    return Api.getInstance().getApiService(KlookApi.class).saveComment(saveCommentParmer).compose(ResponseTransformer.handleResult());
                }).compose(RxTransformer.transformFlowWithLoading(this))
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        ToastUtils.showShort("评论成功");
                        finish();
                    }
                });
    }

    private void upLoadQiNiu(LocalMedia localMedia, String token, FlowableEmitter<List<String>> emitter) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(new File(localMedia.getPath()), null, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                try {
                    String currentUrlPath = SEVENCLOUDDIR + response.get("hash").toString();
                    if (info.isOK()) {
                        needUpHash.add(response.get("hash").toString());
                        picNum++;
                        if (picNum < selectList.size()) {
                            upLoadQiNiu(selectList.get(picNum), token, emitter);
                        } else {
                            picNum = 0;
                            flag = true;
                            emitter.onNext(needUpHash);
                            emitter.onComplete();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);


    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_comment;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picSelectView.onActivityResult(requestCode, resultCode, data);
    }

}
