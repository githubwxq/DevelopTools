package com.wxq.developtools.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.MainActivity;
import com.wxq.developtools.R;
import com.wxq.developtools.constract.LoginContract;
import com.wxq.developtools.present.KlookLoginActivityPresent;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 登入页面
 */

public class KlookLoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.mEtAccount)
    EditText mEtAccount;
    @BindView(R.id.mEtPwd)
    EditText mEtPwd;
    @BindView(R.id.mFabToLogin)
    FloatingActionButton mFabToLogin;
    @BindView(R.id.mProBar)
    ProgressBar mProBar;
    @BindView(R.id.mTvToSignUp)
    TextView mTvToSignUp;

    public static void naveToActivity(Context context) {
        Intent intent = new Intent(context, KlookLoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void naveToMainActivity() {
        ARouter.getInstance().build("/klook/main").navigation();
        finish();
    }

    @Override
    protected void initViews() {
        mEtAccount.setText( AllDataCenterManager.getInstance().getPublicPreference().getAccount());
        mEtPwd.setText( AllDataCenterManager.getInstance().getPublicPreference().getPwd());
        ImmersionBar.with(this).transparentStatusBar().init();
        mTvToSignUp.setOnClickListener(v -> RegisterActivity.navToActivity(context));
        mFabToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        if (AllDataCenterManager.getInstance().getPublicPreference().getAutoLogin()==1) {
            login();
        }
    }

    private void login() {
        String tel = mEtAccount.getText().toString();
        String pwd = mEtPwd.getText().toString();
        if (StringUtils.isBlank(tel) || StringUtils.isBlank(pwd)) {
            showToast("请输入手机号或密码");
        } else {

            XXPermissions.with(this)
                    // 不适配 Android 11 可以这样写
                    //.permission(Permission.Group.STORAGE)
                    // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                    .permission(Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE,Permission.CAMERA,Permission.ACCESS_FINE_LOCATION)
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean all) {
                            if (all) {
                                ToastUtils.showShort("获取存储权限成功");
                                mPresenter.loginWithAccountAndPwd(tel, pwd);
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予存储权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(KlookLoginActivity.this, denied);
                            } else {
                                ToastUtils.showShort("获取存储权限失败");
                            }
                        }
                    });


//            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
//                    , Manifest.permission.ACCESS_FINE_LOCATION
//                    , Manifest.permission.WRITE_SETTINGS,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.CAMERA
//            ).subscribe(new Consumer<Boolean>() {
//                @Override
//                public void accept(Boolean aBoolean) throws Exception {
//                    mPresenter.loginWithAccountAndPwd(tel, pwd);
//                }
//            });
        }
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_klook_login;
    }

    @Override
    protected LoginContract.Presenter initPresent() {
        return new KlookLoginActivityPresent(this);
    }

}
