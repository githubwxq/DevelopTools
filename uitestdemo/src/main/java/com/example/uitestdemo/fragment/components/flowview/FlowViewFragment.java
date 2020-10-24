package com.example.uitestdemo.fragment.components.flowview;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.floatview.xfloatview.permission.FloatWindowPermission;
import com.juziwl.uilibrary.floatview.xfloatview.service.AppMonitorService;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class FlowViewFragment extends BaseFragment {

    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_close)
    TextView tvClose;

    public static FlowViewFragment newInstance() {
        FlowViewFragment fragment = new FlowViewFragment();
        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_flow_view;
    }

    @Override
    protected void initViews() {
        FloatWindowPermission.getInstance().applyFloatWindowPermission(getContext());
    }

    @OnClick({R.id.tv_open, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open:
                FloatWindowPermission.getInstance().applyFloatWindowPermission(getContext());
                AppMonitorService.start(getContext(), null);
                gotoHome(getActivity());
                break;
            case R.id.tv_close:
                AppMonitorService.stop(getContext());
                break;
        }
    }
    /**
     * 防止华为机型未加入白名单时按返回键回到桌面再锁屏后几秒钟进程被杀
     */
    public static void gotoHome(Activity activity) {
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(launcherIntent);
    }

}