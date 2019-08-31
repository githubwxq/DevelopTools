//package com.example.module_login.activity;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.example.module_login.R;
//import com.example.module_login.R2;
//import com.example.module_login.presenter.LoginActivityPresent;
//import com.juziwl.uilibrary.edittext.EditTextWithDel;
//import com.wxq.commonlibrary.router.RouterContent;
//import com.wxq.commonlibrary.util.StringUtils;
//import com.wxq.commonlibrary.util.ToastUtils;
//import butterknife.BindView;
//import io.reactivex.functions.Consumer;
//
////import cn.bmob.v3.BmobUser;
//
///*
// *登录页面3
// * */
//public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
//
//    @BindView(R2.id.title_back)
//    TextView titleBack;
//    @BindView(R2.id.etd_tel)
//    EditTextWithDel etdTel;
//    @BindView(R2.id.etd_pwd)
//    EditTextWithDel etdPwd;
//    @BindView(R2.id.cb_show_hide_pwd)
//    CheckBox cbShowHidePwd;
//    @BindView(R2.id.btn_login)
//    Button btnLogin;
//    @BindView(R2.id.tv_service_phone_remark)
//    TextView tvServicePhoneRemark;
//    @BindView(R2.id.layout_login)
//    LinearLayout layoutLogin;
//    @BindView(R2.id.tv_register)
//    TextView tvRegister;
//
//    public static void navToActivity(Context context) {
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//    }
//    //如果设置了target > 28，需要增加这个权限，否则不会弹出"始终允许"这个选择框
//    private static String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
//    @Override
//    protected void initViews() {
//        click(btnLogin, o -> {
//
//            if (StringUtils.isEmpty(etdTel.getText().toString()) || StringUtils.isEmpty(etdPwd.getText().toString())) {
//                ToastUtils.showShort("请输入手机号或密码");
//                return;
//            }
//            rxPermissions.request(	Manifest.permission.ACCESS_COARSE_LOCATION
//            ,	Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_PHONE_STATE,
//                    BACK_LOCATION_PERMISSION
//            ).subscribe(new Consumer<Boolean>() {
//                @Override
//                public void accept(Boolean aBoolean) throws Exception {
//                 if (aBoolean){
//                     mPresenter.loginWithAccountAndPwd(etdTel.getText().toString(), etdPwd.getText().toString());
//                 }else {
//                     ToastUtils.showShort("请先赋予权限");
//                 }
//                }
//            });
//
//
//
//        });
//        click(tvRegister, o -> {
//            RegisterActivity.navToActivity(this);
//        });
//        cbShowHidePwd.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            etdPwd.setPassWordStyle(isChecked);
//        });
//    }
//
//    @Override
//    protected int attachLayoutRes() {
//        return R.layout.activity_login;
//    }
//
//    @Override
//    protected LoginContract.Presenter initPresent() {
//        return new LoginActivityPresent(this);
//    }
//
//    @Override
//    public void naveToMainActivity() {
//        //前往首页模块
//        ARouter.getInstance().build(RouterContent.BMOB_MAIN).navigation();
//        finish();
//    }
//
//    @Override
//    public boolean isNeedHeardLayout() {
//        return false;
//    }
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        User currentUser = BmobUser.getCurrentUser(User.class);
////        if (currentUser!=null) {
////            currentUser.getUsername();
////
////        }
//    }
//}
