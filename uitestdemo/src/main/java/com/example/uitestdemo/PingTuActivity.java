package com.example.uitestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import io.reactivex.functions.Consumer;

public class PingTuActivity extends BaseActivity {

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
