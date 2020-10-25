package com.example.uitestdemo.fragment.components.guideview;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.guidview.FocusShape;
import com.juziwl.uilibrary.guidview.GuideCaseView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideCaseViewStyleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideCaseViewStyleFragment extends BaseFragment {

    @BindView(R.id.btn_nofocus)
    Button btnNofocus;
    @BindView(R.id.btn_rounded_rect)
    Button btnRoundedRect;
    @BindView(R.id.btn_large_circle)
    Button btnLargeCircle;
    @BindView(R.id.btn_rect_position)
    Button btnRectPosition;
    @BindView(R.id.btn_background_color)
    Button btnBackgroundColor;
    @BindView(R.id.btn_no_anim)
    Button btnNoAnim;
    @BindView(R.id.btn_custom_anim)
    Button btnCustomAnim;
    @BindView(R.id.btn_custom_view)
    Button btnCustomView;

    public static GuideCaseViewStyleFragment newInstance() {
        GuideCaseViewStyleFragment fragment = new GuideCaseViewStyleFragment();

        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_guide_case_view_style;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.btn_nofocus, R.id.btn_rounded_rect, R.id.btn_large_circle, R.id.btn_rect_position, R.id.btn_background_color, R.id.btn_no_anim, R.id.btn_custom_anim, R.id.btn_custom_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_nofocus:
                new GuideCaseView.Builder(getActivity())
                        .picture(R.drawable.img_guidecaseview_gain_speed_gesture)
                        .build()
                        .show();

                break;
            case R.id.btn_rounded_rect:
                new GuideCaseView.Builder(getActivity())
                        .focusOn(view)
                        .title("这是圆角矩形聚焦框")
                        .focusShape(FocusShape.ROUNDED_RECTANGLE)
                        .roundRectRadius(90)
                        .build()
                        .show();
                break;
            case R.id.btn_large_circle:
                new GuideCaseView.Builder(getActivity())
                        .focusOn(view)
                        .focusCircleRadiusFactor(1.5)
                        .title("一个巨大的圆形聚焦")
                        .focusBorderColor(Color.GREEN)
                        .titleStyle(0, Gravity.BOTTOM | Gravity.CENTER)
                        .build()
                        .show();
                break;
            case R.id.btn_rect_position:
                new GuideCaseView.Builder(getActivity())
                        .title("坐标聚焦")
                        .focusRectAtPosition(600, 80, 800, 140)
                        .roundRectRadius(60)
                        .build()
                        .show();
                break;
            case R.id.btn_background_color:
                new GuideCaseView.Builder(getActivity())
                        .focusOn(view)
                        .backgroundColor(Color.parseColor("#AAff0000"))
                        .title("背景颜色和文字样式都可以自定义")
                        .titleStyle(0, Gravity.TOP | Gravity.CENTER)
                        .build()
                        .show();
                break;
            case R.id.btn_no_anim:
                new GuideCaseView.Builder(getActivity())
                        .focusOn(view)
                        .disableFocusAnimation()
                        .build()
                        .show();
                break;
            case R.id.btn_custom_anim:
                Animation enterAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.gcv_alpha_in);
                Animation exitAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.gcv_alpha_out);

                final GuideCaseView guideCaseView = new GuideCaseView.Builder(getActivity())
                        .focusOn(view)
                        .title("自定义进入和退出动画")
                        .enterAnimation(enterAnimation)
                        .exitAnimation(exitAnimation)
                        .build();
                guideCaseView.show();
                exitAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        guideCaseView.removeView();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;
            case R.id.btn_custom_view:

//                GuideCaseView    guideCaseView1 = new GuideCaseView.Builder(getActivity())
//                        .focusOn(view)
//                        .customView(R.layout.layout_custom_guide_case_view, view12 -> view12.findViewById(R.id.btn_action_close).setOnClickListener(view1 -> guideCaseView.hide()))
//                        .closeOnTouch(false)
//                        .build();
//                guideCaseView1.show();


                break;
        }
    }
}