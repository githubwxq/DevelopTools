package com.example.uitestdemo.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.uitestdemo.R;
import com.example.uitestdemo.R2;
import com.juziwl.uilibrary.activity.VideoListActivity;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.dispatch.MyDispatchButton;
import com.wxq.commonlibrary.util.ToastUtils;

import butterknife.BindView;

public class TestDispatchEventFragment extends BaseFragment {

    public  static String TAG="TestDispatchEventFragment";
    @BindView(R2.id.btn_dispatch)
    MyDispatchButton btnDispatch;
    public TestDispatchEventFragment() {
        // Required empty public constructor
    }

    public static TestDispatchEventFragment newInstance() {
        TestDispatchEventFragment fragment = new TestDispatchEventFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initViews() {
        btnDispatch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int type = event.getAction();
                if (MotionEvent.ACTION_DOWN == type) {
                    Log.e(TAG,"onTouch====MyButton=====ACTION_DOWN");
                }
                if (MotionEvent.ACTION_MOVE == type) {
                    Log.e(TAG,"onTouch====MyButton=====ACTION_MOVE");
                }
                if (MotionEvent.ACTION_UP == type) {
                    Log.e(TAG,"onTouch====MyButton=====ACTION_UP");
                }
                return false;
            }
        });

        btnDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WatchVideoActivity.navToActivity(getActivity());
                VideoListActivity.navToActivity(getActivity());
            }
        });
    }


    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        Logger.d(getClass().getSimpleName() + " onefragment 对用户第一次可见");

    }
    android.os.Handler handler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            //这里接受并处理消息
            ToastUtils.showShort("3333");
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getClass().getSimpleName() + " tonefragment onResume");

//        UIHandler.getInstance()
        //发送消息
        Message message=Message.obtain();
//        handler.sendMessage(message);
        handler.sendMessageDelayed(message,3000);
//        handler.post(runnable);runnable



    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Logger.d(getClass().getSimpleName() + " tonefragment onFragmentResume");
    }

}
