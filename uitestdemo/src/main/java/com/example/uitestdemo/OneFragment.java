package com.example.uitestdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.juziwl.uilibrary.activity.VideoListActivity;
import com.juziwl.uilibrary.activity.WatchVideoActivity;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.dispatch.MyDispatchButton;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;

import java.lang.reflect.Proxy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends BaseFragment {
    private static String TAG = "OneFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R2.id.btn_dispatch)
    MyDispatchButton btnDispatch;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
