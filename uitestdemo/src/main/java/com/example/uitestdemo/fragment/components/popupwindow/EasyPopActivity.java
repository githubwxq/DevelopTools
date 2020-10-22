//package com.example.uitestdemo.fragment.components.popupwindow;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.uitestdemo.R;
//import com.juziwl.uilibrary.popupwindow.EasyPopup;
//import com.juziwl.uilibrary.popupwindow.XGravity;
//import com.juziwl.uilibrary.popupwindow.YGravity;
//import com.wxq.commonlibrary.util.ToastUtils;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class EasyPopActivity extends AppCompatActivity {
//
//
//    @BindView(R.id.btn_circle_comment)
//    Button btnCircleComment;
//    @BindView(R.id.btn_above)
//    Button btnAbove;
//    @BindView(R.id.btn_right)
//    Button btnRight;
//    @BindView(R.id.btn_bg_dim)
//    Button btnBgDim;
//    @BindView(R.id.btn_bg_dim_any)
//    Button btnBgDimAny;
//    @BindView(R.id.btn_gift)
//    Button btnGift;
//    @BindView(R.id.btn_pop_cmmt)
//    Button btnPopCmmt;
//    @BindView(R.id.tv_pop_everywhere)
//    TextView tvPopEverywhere;
//    @BindView(R.id.ll_complex_bg_dim)
//    LinearLayout llComplexBgDim;
//    @BindView(R.id.btn_complex)
//    Button btnComplex;
//
//
//    @BindView(R.id.ll_bg_text)
//    LinearLayout llBgText;
//    EasyPopup mCirclePop;
//
//    private EasyPopup mAbovePop;
//
//    private EasyPopup mBgDimPop;
//
//    private EasyPopup mAnyBgDimPop;
//
//    private CustomPopup customPopup;
////    private CmmtPopup mCmmtPopup;
////    private ComplexPopup mComplexPopup;
////
////    private EverywherePopup mEverywherePopup;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_easy_pop);
//        ButterKnife.bind(this);
//        initCirclePop();
//        initAbovePop();
//
//        initBgDimPop();
//
//        initAnyBgDimPop();
//
//        initCustomPop();
//    }
//
//    private void initCustomPop() {
//        customPopup = CustomPopup.create().setContext(this).apply();
//
//    }
//
//
//    private void initAnyBgDimPop() {
//        mAnyBgDimPop = EasyPopup.create()
//                .setContentView(this, R.layout.layout_any)
//                .setFocusAndOutsideEnable(true)
//                .setBackgroundDimEnable(true)
//                .setDimValue(0.4f)
//                .setDimView(llBgText)
//                .setDimColor(Color.YELLOW)
//                .apply();
//
//
//    }
//
//
//    private void initBgDimPop() {
//        mBgDimPop = EasyPopup.create()
//                .setContentView(this, R.layout.layout_any)
//                .setFocusAndOutsideEnable(true)
//                .setBackgroundDimEnable(true)
////                .setDimView(llBgText)
//                .setDimValue(0.4f)
//                .apply();
////         .setDimValue(0.4f)  只设置 改的是窗体的    背景 设置了dimview 改变的是dimview 给dimview 添加遮罩
//    }
//
//    private void initAbovePop() {
//
//        mAbovePop = EasyPopup.create()
//                .setContentView(this, R.layout.layout_any)
//                .setFocusAndOutsideEnable(true)
//                .setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        ToastUtils.showShort("我消失啦");
//                    }
//                })
//                .apply();
//
//    }
//
//    /**
//     * 朋友圈弹窗
//     */
//    private void initCirclePop() {
//        mCirclePop = EasyPopup.create().setContentView(this, R.layout.layout_circle_comment)
//                .setAnimationStyle(R.style.RightPopAnim)
//                .setFocusAndOutsideEnable(true)
//                .setOnViewListener(new EasyPopup.OnViewListener() {
//                    @Override
//                    public void initViews(View view, EasyPopup popup) {
//                        TextView textView = view.findViewById(R.id.tv_zan);
//                        textView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastUtils.showShort("点击点赞");
//                                mCirclePop.dismiss();
//                            }
//                        });
//                    }
//                }).setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        ToastUtils.showShort("我消失啦");
//                    }
//                }).apply();
//
//
//    }
//
//
//    @OnClick({R.id.btn_circle_comment, R.id.btn_above, R.id.btn_right, R.id.btn_bg_dim, R.id.btn_bg_dim_any, R.id.btn_gift, R.id.btn_pop_cmmt, R.id.tv_pop_everywhere, R.id.ll_complex_bg_dim, R.id.btn_complex})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_circle_comment:
//                mCirclePop.showAtAnchorView(view, YGravity.CENTER, XGravity.LEFT, 0, 0);
//
////                mCirclePop.findViewById()
//
//                break;
//            case R.id.btn_above:
//                showAbovePop(view);
//                break;
//            case R.id.btn_right:
//                showRightPop(view);
//                break;
//            case R.id.btn_bg_dim:
//                showBgDimPop(view);
//                break;
//            case R.id.btn_bg_dim_any:
//                showAnyBgDimPop(view);
//                break;
//            case R.id.btn_gift:
//                showCustomPop(view);
//
//                break;
//            case R.id.btn_pop_cmmt:
//                break;
//            case R.id.tv_pop_everywhere:
//                break;
//            case R.id.ll_complex_bg_dim:
//                break;
//            case R.id.btn_complex:
//                break;
//        }
//    }
//
//    private void showCustomPop(View view) {
//
////        customPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        customPopup.showAtAnchorView(view, YGravity.BELOW, XGravity.CENTER);
//
//    }
//
//    private void showAnyBgDimPop(View view) {
//        mAnyBgDimPop.showAtAnchorView(view, YGravity.ALIGN_BOTTOM, XGravity.ALIGN_RIGHT);
//    }
//
//    private void showRightPop(View view) {
//        mAbovePop.showAtAnchorView(view, YGravity.CENTER, XGravity.RIGHT);
//    }
//
//    private void showAbovePop(View view) {
//
//        mAbovePop.showAtAnchorView(view, YGravity.ABOVE, XGravity.CENTER);
////        mAbovePop.showAtAnchorView(view, YGravity.ABOVE, XGravity.LEFT);
////        mAbovePop.showAtAnchorView(view, YGravity.ABOVE, XGravity.ALIGN_LEFT);
////        mAbovePop.showAtAnchorView(view, YGravity.ABOVE, XGravity.RIGHT);
////        mAbovePop.showAtAnchorView(view, YGravity.ABOVE, XGravity.CENTER);
//
//    }
//
//
//    private void showBgDimPop(View view) {
//        mBgDimPop.showAtAnchorView(view, YGravity.ALIGN_TOP, XGravity.ALIGN_RIGHT);
//    }
//
//
//}
