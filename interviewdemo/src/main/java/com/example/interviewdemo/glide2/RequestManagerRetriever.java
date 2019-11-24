package com.example.interviewdemo.glide2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.interviewdemo.glide2.manager.ApplicationLifecycle;
import com.example.interviewdemo.glide2.manager.SupportRequestManagerFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/9.
 * 获得一个RequestManager的作用
 */
public class RequestManagerRetriever implements Handler.Callback {

    public static final String FRAG_TAG = "glide_fragment";
    private final static int REMOVE_SUPPORT_FRAGMENT = 1;
    private final GlideContext glideContext;
    RequestManager applicationRequestManager;
    //临时集合
    private Map<FragmentManager, SupportRequestManagerFragment> supports = new HashMap<>();
    private Handler handler;

    public RequestManagerRetriever(GlideContext glideContext) {
        this.glideContext = glideContext;
        handler = new Handler(Looper.getMainLooper(), this);
    }

    /**
     * 给Application使用的 不管理生命周期
     *
     * @return
     */
    private RequestManager getApplicationManager() {
        if (null == applicationRequestManager) {
            applicationRequestManager = new RequestManager(glideContext, new ApplicationLifecycle());
        }
        return applicationRequestManager;
    }

    protected RequestManager get(Context context) {
        if (!(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            } else if (context instanceof Activity) {

            } else if (context instanceof ContextWrapper) {
                get(((ContextWrapper) context).getBaseContext());
            }
        }
        return getApplicationManager();
    }

    private RequestManager get(FragmentActivity activity) {
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        return supportFragmentGet(supportFragmentManager);
    }

    private RequestManager supportFragmentGet(FragmentManager fm) {
        //从frgamentmanager中查找RequestManagerFragment
        SupportRequestManagerFragment fragment = getSupportRequestManagerFragment(fm);
        // 将RequestManager交给 Frgament 来保存
        RequestManager requestManager = fragment.getRequestManager();
        if (null == requestManager) {
            requestManager = new RequestManager(glideContext, fragment.getGlideLifecycle());
            fragment.setRequestManager(requestManager);
        }
        return requestManager;
    }

    /**
     * 获得Frqagment
     *
     * @param fm
     * @return
     */
    private SupportRequestManagerFragment getSupportRequestManagerFragment(FragmentManager fm) {
        SupportRequestManagerFragment fragment = (SupportRequestManagerFragment) fm.findFragmentByTag(FRAG_TAG);
        if (null == fragment) {
            fragment = supports.get(fm);
            if (null == fragment) {
                fragment = new SupportRequestManagerFragment();
                //先加入集合
                supports.put(fm, fragment);
                fm.beginTransaction().add(fragment, FRAG_TAG).commitAllowingStateLoss();
                //通过handler发送 移除集合的消息
                // Glide.with
                // Glide.with
                // 连续调用两次 第一次 创建Frgament 但是由于 FM.add 不是commitAllowingStateLoss就绑定上去了
                // 所以可能导致第二次调用仍然创建Fragment
                //所以使用一个sopports临时集合
                handler.obtainMessage(REMOVE_SUPPORT_FRAGMENT, fm).sendToTarget();
            }
        }
        return fragment;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REMOVE_SUPPORT_FRAGMENT:
                FragmentManager fm = (FragmentManager) msg.obj;
                supports.remove(fm);
                break;
        }
        return false;
    }
}
