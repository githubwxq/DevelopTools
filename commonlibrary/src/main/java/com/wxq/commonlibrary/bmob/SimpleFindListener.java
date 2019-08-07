//package com.wxq.commonlibrary.bmob;
//
//import android.content.Context;
//import android.os.Handler;
//import com.wxq.commonlibrary.util.ToastUtils;
//import com.wxq.commonlibrary.weiget.DialogManager;
//import java.util.List;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
///**
// * Created by CWQ on 2017/5/1.
// */
//
//public abstract class SimpleFindListener<T> extends FindListener<T> {
//    //
////    private int delayMillis = DELAYMILLIS_FOR_RQUEST_FINISH;
//    private long startTimeMillis;
//    private Context context;
//
//
//    public SimpleFindListener() {
//        onBefore();
//    }
//
//    public SimpleFindListener(Context context) {
//        this.context = context;
//        onBefore();
//    }
//
//    public final void onBefore() {
//        startTimeMillis = System.currentTimeMillis();
//        DialogManager.getInstance().createLoadingDialog(context, "正在加载中...").show();
//        before();
//    }
//
//    @Override
//    public void done(List<T> list, BmobException e) {
//        delayExcute(new DoneRun(list, e));
//
//    }
//
//    private class DoneRun implements Runnable {
//
//        private List<T> list;
//        private BmobException e;
//
//        public DoneRun(List<T> list, BmobException e) {
//            this.list = list;
//            this.e = e;
//        }
//
//        @Override
//        public void run() {
//            if (e == null) {
//                success(list);
//            } else {
//                error(e);
//            }
//            onAfter();
//        }
//    }
//
//    public final void onAfter() {
//        DialogManager.getInstance().cancelDialog();
//        after();
//    }
//
//    private void delayExcute(final Runnable r) {
//        new Handler().post(r);
//    }
//
//    private long getTimeDif() {
//        return System.currentTimeMillis() - startTimeMillis;
//    }
//
//    protected void before() {
//
//    }
//
//    public abstract void success(final List<T> list);
//
//    protected void error(BmobException e) {
//        ToastUtils.showShort(e.getMessage());
//    }
//
//    protected void after() {
//
//    }
//}
