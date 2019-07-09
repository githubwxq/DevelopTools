package com.example.module_login.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.LoginContract;
import com.example.module_login.presenter.KlookLoginActivityPresent;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.util.StringUtils;

import butterknife.BindView;

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
        showToast("前往首页 arouter前往首页开发");


    }

    @Override
    protected void initViews() {
        mTvToSignUp.setOnClickListener(v -> RegisterActivity.navToActivity(context));
        mFabToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = mEtAccount.getText().toString();
                String pwd = mEtPwd.getText().toString();
                if (StringUtils.isBlank(tel)||   StringUtils.isBlank(pwd)) {
                    showToast("请输入手机号或密码");
                }else {
                    mPresenter.loginWithAccountAndPwd(tel,pwd);
                }
            }
        });
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
