package com.example.uitestdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.UIHandler;

import butterknife.BindView;
import butterknife.Unbinder;

public class TestMemoryFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView tvText;
    Unbinder unbinder;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    Unbinder unbinder1;

    public TestMemoryFragment() {
        // Required empty public constructor
    }

    public static TestMemoryFragment newInstance() {
        TestMemoryFragment fragment = new TestMemoryFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_test_memory;
    }

    @Override
    protected void initViews() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);

        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                int largeMemoryClass = activityManager.getLargeMemoryClass();
                int memoryClass = activityManager.getMemoryClass();
                long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);
                long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
                long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
                tvText.setText("\ngetLargeMemoryClass" + largeMemoryClass + "\nmemoryClass" + memoryClass
                        + "\ntotalMemory" + totalMemory
                        + "\nfreeMemory" + freeMemory
                        + "\nmaxMemory" + maxMemory
                );
            }
        }, 200);

        ivGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().startActivity(new Intent(getActivity(),TestActivity.class));
            }
        });
    }

}
