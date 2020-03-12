package com.example.uitestdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juziwl.uilibrary.textview.stytle.RadiusBgSpan;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ImageUtils;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class PingTuActivity extends BaseActivity {

    @BindView(R.id.pingtu)
    PingTuWeight pingtu;

    @BindView(R.id.iv_new_pic)
    ImageView ivNewPic;
  @BindView(R.id.tv_change)
  TextView tv_change;

    @Override
    protected void initViews() {
        rxPermissions.request(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS

                , Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
//                oldImage.setImageURI(Uri.fromFile(new File("/sdcard/text.png")));
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ImageUtils.cutBitmapFromView(pingtu);
                ivNewPic.setImageBitmap(bitmap);
            }
        });

        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder("获取图片控件");

        SpannableStringBuilder addmsg=new SpannableStringBuilder("标签");
        RadiusBgSpan bgSpan=new RadiusBgSpan(getContext(),R.color.red_100,R.color.white,10);
        addmsg.setSpan(bgSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(addmsg);
        tv_change.setText(spannableStringBuilder);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_ping_tu;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
