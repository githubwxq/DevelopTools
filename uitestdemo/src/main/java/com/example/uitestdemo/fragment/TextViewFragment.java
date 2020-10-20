package com.example.uitestdemo.fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.example.uitestdemo.bean.TestIntentMode;
import com.juziwl.uilibrary.textview.stytle.DottedUnderlineSpan;
import com.juziwl.uilibrary.textview.stytle.IconTextSpan;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.util.SpanUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.Unbinder;

public class TextViewFragment extends BaseFragment {
    @BindView(R.id.tv_with_image)
    TextView tvWithImage;
    Unbinder unbinder;
    @BindView(R.id.tv_with_image1)
    TextView tvWithImage1;
    @BindView(R.id.tv_with_image2)
    TextView tvWithImage2;
    Unbinder unbinder1;

    public static TextViewFragment newInstance() {
        TextViewFragment fragment = new TextViewFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_text_view;
    }


    TestIntentMode intentMode;

    @Override
    protected void initViews() {
        tvWithImage.setText(new SpanUtils().append("测试图片的位子").appendImage(R.mipmap.back_img).create());
//        tvWithImage1.setText(new SpanUtils().append("测试图片的位子").appendImage(R.mipmap.back_img,SpanUtils.ALIGN_TOP).create());
        tvWithImage2.setText(new SpanUtils().append("测试图片的位子").appendImage(R.mipmap.back_img,SpanUtils.ALIGN_CENTER).create());
        IconTextSpan iconTextSpan = new IconTextSpan(getActivity(),"热热热");
        DottedUnderlineSpan dottedUnderlineSpan = new DottedUnderlineSpan(R.color.green_800,"01234");
        SpannableStringBuilder spannableString=new SpannableStringBuilder("0123456");
//        spannableString.setSpan(dottedUnderlineSpan,0,4,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(iconTextSpan,1,2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvWithImage1.setText(spannableString);





    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        super.dealWithRxEvent(action, event);
        if (action==999) {
            ToastUtils.showShort(intentMode.name);
        }
    }
}
