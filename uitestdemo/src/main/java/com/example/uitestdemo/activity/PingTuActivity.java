package com.example.uitestdemo.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uitestdemo.weight.PingTuWeight;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.textview.stytle.DividerSpan;
import com.juziwl.uilibrary.textview.stytle.RadiusBgSpan;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.DensityUtil;
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

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("获取图片控件");

        SpannableStringBuilder addmsg = new SpannableStringBuilder("标签");
        RadiusBgSpan bgSpan = new RadiusBgSpan(getContext(), Color.parseColor("#abd0ff"), R.color.white, DensityUtil.dip2px(this,2));
        addmsg.setSpan(bgSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(addmsg);

        SpannableStringBuilder addmsg2 = new SpannableStringBuilder("0");
        DividerSpan DividerSpan = new DividerSpan(getContext(), DensityUtil.dip2px(this,5));
        addmsg2.setSpan(DividerSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(addmsg2);

        SpannableStringBuilder addmsg3 = new SpannableStringBuilder("标签3");
        RadiusBgSpan bgSpan3 = new RadiusBgSpan(getContext(), Color.parseColor("#abd0ff"), R.color.white, DensityUtil.dip2px(this,2));
        addmsg3.setSpan(bgSpan3, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(addmsg3);

        SpannableStringBuilder addmsg4 = new SpannableStringBuilder("0");
        ImageSpan bgSpan4= new ImageSpan(getContext(), R.mipmap.addchildicon);
        addmsg4.setSpan(bgSpan4, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(addmsg4);





        tv_change.setText(spannableStringBuilder);


        LoadingImgUtil.loadimg("http://pic.app.hongseqf.qianfanyun.cn/_20200407190746_5e8c5f02d3ee7.jpg?imageslim|imageView2/1/w/420/h/420",ivNewPic,false);


//        BarUtils.setStatusBarVisibility(this,false);
//        BarUtils.setNavBarVisibility(this, false);
        //测试gifdrawable


//        try {
//            GifDrawable   drawable = new GifDrawable(getResources(),R.drawable.timg);
//
//            GifImageView gifImageView=findViewById(R.id.giv_bg);
//            gifImageView.setBackground(drawable);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ImmersionBar.with(this).init();

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
