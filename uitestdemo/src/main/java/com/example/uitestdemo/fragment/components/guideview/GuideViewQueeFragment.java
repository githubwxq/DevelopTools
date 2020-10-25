package com.example.uitestdemo.fragment.components.guideview;

import android.view.View;
import android.widget.Button;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.guidview.GuideCaseQueue;
import com.juziwl.uilibrary.guidview.GuideCaseView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class GuideViewQueeFragment extends BaseFragment {

    @BindView(R.id.step1)
    Button step1;
    @BindView(R.id.step2)
    Button step2;
    @BindView(R.id.step3)
    Button step3;
    @BindView(R.id.step4)
    Button step4;
    @BindView(R.id.changePicture)
    Button changePicture;

    public static GuideViewQueeFragment newInstance() {
        GuideViewQueeFragment fragment = new GuideViewQueeFragment();

        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_guide_view_quee;
    }

    @Override
    protected void initViews() {
        showTextGuideView();

    }

    /**
     * 显示文字引导
     */
    private void showTextGuideView() {
        final GuideCaseView guideStep1 = new GuideCaseView.Builder(getActivity())
                .title("请注意，这是第一步")
                .focusOn(step1)
                .build();

        final GuideCaseView guideStep2 = new GuideCaseView.Builder(getActivity())
                .title("请注意，这是第二步")
                .focusOn(step2)
                .build();

        final GuideCaseView guideStep3 = new GuideCaseView.Builder(getActivity())
                .title("请注意，这是第三步")
                .focusOn(step3)
                .build();

        final GuideCaseView guideStep4 = new GuideCaseView.Builder(getActivity())
                .title("请注意，这是第四步")
                .focusOn(step4)
                .build();

        new GuideCaseQueue()
                .add(guideStep1)
                .add(guideStep2)
                .add(guideStep3)
                .add(guideStep4)
                .show();
    }

    /**
     * 显示图片引导
     */
    private void showPictureGuideView() {
        final GuideCaseView guideStep1 = new GuideCaseView.Builder(getActivity())
                .picture(R.drawable.img_guidecaseview_1)
                .focusOn(step1)
                .build();

        final GuideCaseView guideStep2 = new GuideCaseView.Builder(getActivity())
                .picture(R.drawable.img_guidecaseview_2)
                .focusOn(step2)
                .build();

        final GuideCaseView guideStep3 = new GuideCaseView.Builder(getActivity())
                .picture(R.drawable.img_guidecaseview_3)
                .focusOn(step3)
                .build();

        final GuideCaseView guideStep4 = new GuideCaseView.Builder(getActivity())
                .picture(R.drawable.img_guidecaseview_4)
                .focusOn(step4)
                .build();

        new GuideCaseQueue()
                .add(guideStep1)
                .add(guideStep2)
                .add(guideStep3)
                .add(guideStep4)
                .show();
    }


    @OnClick({R.id.step1, R.id.step2, R.id.step3, R.id.step4, R.id.changePicture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changePicture:
                showPictureGuideView();
                break;
        }
    }
}