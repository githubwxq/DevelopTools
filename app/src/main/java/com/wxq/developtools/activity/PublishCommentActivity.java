package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

public class PublishCommentActivity extends BaseActivity {
    String productId;

    public static void navToActivity(Context context,String productId) {
        Intent intent = new Intent(context, PublishCommentActivity.class);
        intent.putExtra("productId",productId);
        context.startActivity(intent);
    }



    @Override
    protected void initViews() {
        productId=getIntent().getStringExtra("productId");


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


    }
}
