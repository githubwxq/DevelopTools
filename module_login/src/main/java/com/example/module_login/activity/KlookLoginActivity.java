package com.example.module_login.activity;

import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.LoginContract;
import com.example.module_login.presenter.KlookLoginActivityPresent;
import com.gyf.immersionbar.ImmersionBar;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.util.StringUtils;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 登入页面
 */
public class KlookLoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R2.id.mEtAccount)
    EditText mEtAccount;
    @BindView(R2.id.mEtPwd)
    EditText mEtPwd;
    @BindView(R2.id.mFabToLogin)
    FloatingActionButton mFabToLogin;
    @BindView(R2.id.mProBar)
    ProgressBar mProBar;
    @BindView(R2.id.mTvToSignUp)
    TextView mTvToSignUp;


    @Override
    public void naveToMainActivity() {
//        showToast("前往首页 arouter前往首页开发");
        ARouter.getInstance().build("/klook/main").navigation();

//        ScanActivity.navToActivity(this);


        finish();
    }

    @Override
    protected void initViews() {
        mTvToSignUp.setOnClickListener(v -> RegisterActivity.navToActivity(context));
        mFabToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = mEtAccount.getText().toString();
                String pwd = mEtPwd.getText().toString();
                if (StringUtils.isBlank(tel) || StringUtils.isBlank(pwd)) {
                    showToast("请输入手机号或密码");
                } else {
                    rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CAMERA
                    ).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            mPresenter.loginWithAccountAndPwd(tel, pwd);
                        }
                    });
                }
            }
        });

        ImmersionBar.with(this).transparentStatusBar().init();
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

//    @OnClick({R2.id.mFabToLogin, R2.id.mProBar, R2.id.mTvToSignUp})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R2.id.mFabToLogin:
//                break;
//            case R2.id.mProBar:
//                break;
//            case R2.id.mTvToSignUp:
//                 RegisterActivity.navToActivity(this);
//                break;
//        }
//    }
}
