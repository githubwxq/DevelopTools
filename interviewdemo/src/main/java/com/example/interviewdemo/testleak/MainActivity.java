//package com.example.interviewdemo.testleak;
//
//import android.app.usage.UsageEvents;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.inject.compile.InjectView;
//import com.example.inject.runtime.InjectUtils;
//import com.example.inject.runtime.ViewInject;
//import com.example.inject_annotion.BindView;
//import com.example.interviewdemo.R;
//import com.example.interviewdemo.okhttp.HttpClient;
//import com.example.interviewdemo.okhttp.Response;
//import com.example.interviewdemo.okhttp.call.Call;
//import com.example.interviewdemo.okhttp.call.Callback;
//import com.example.interviewdemo.okhttp.chain.Interceptor;
//import com.example.interviewdemo.okhttp.chain.InterceptorChain;
//import com.example.interviewdemo.okhttp.request.Request;
//import com.example.interviewdemo.okhttp.request.RequestBody;
//import com.wxq.commonlibrary.eventbus.EventBus;
//import com.wxq.commonlibrary.eventbus.Subscribe;
//import com.wxq.commonlibrary.eventbus.ThreadMode;
//import com.wxq.commonlibrary.mydb.bean.DbUser;
//import com.wxq.commonlibrary.mydb.db.BaseDaoFactory;
//import com.wxq.commonlibrary.mydb.db.BaseDaoNewImpl;
//import com.wxq.commonlibrary.mydb.db.IBaseDao;
//import com.wxq.commonlibrary.util.ToastUtils;
//
//import java.io.IOException;
//import java.util.List;
//
//import butterknife.BindViews;
//import butterknife.ButterKnife;
//
//import static com.wxq.commonlibrary.eventbus.ThreadMode.MainThread;
//
//public class MainActivity extends AppCompatActivity {
//
//    @ViewInject(R.id.tv_runtime_annotion)
//    TextView tv_runtime_annotion;
//    @ViewInject(R.id.tv_post_event)
//    TextView tv_post_event;
//
//    @ViewInject(R.id.tv_add)
//    TextView tv_add;
//
// @ViewInject(R.id.tv_find)
//    TextView tv_find;
//
//
//    //注意导包
//    @BindView(R.id.tv_build_annotation)
//    TextView tv_build_annotation;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);
//        InjectUtils.inject(this);
//        tv_runtime_annotion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showShort("我是被viewinject运行时反射调用");
//            }
//        });
//        InjectView.bind(this);
//        tv_build_annotation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showShort("我编译时被调用了");
//            }
//        });
//        tv_post_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new MyEvent(1, "111"));
//            }
//        });
//
//        tv_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                IBaseDao baseDao = BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class, DbUser.class);
//                baseDao.insert(new DbUser(1, "wxq", "123"));
//                Toast.makeText(MainActivity.this, "执行成功!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
////
////        tv_find.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                BaseDaoNewImpl baseDao = BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class, DbUser.class);
////                DbUser where = new DbUser();
////                where.setName("wxq");
////                List<DbUser> list = baseDao.query(where);
////                ToastUtils.showShort(list.size() + "调数据");
////            }
////
////        });
//
//
//
//
//
//
//    }
//
//
//    @Subscribe(threadMode = MainThread)
//    public void receiveEvent(MyEvent event) {
//        ToastUtils.showShort("currentg therad" + Thread.currentThread().getName());
//    }
//
//
//    HttpClient client;
//
//    private void initHttpClient() {
//        client = new HttpClient.Builder().addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(InterceptorChain chain) throws IOException {
//                //dosomething
//                Response proceed = chain.proceed();
//                //dosomething
//                return proceed;
//            }
//        }).build();
//
//    }
//
//    /**
//     * get请求
//     */
////    public void get() {
////        Request request = new Request.Builder()
////                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
////                .build();
////
////
////        Call call = client.newCall(request);
////        call.enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, Throwable throwable) {
////                showReult(tv_test, throwable.getMessage());
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) {
////                showReult(tv_test, response.getBody());
////            }
////        });
////    }
//
//    /**
//     * post请求
//     */
////    public void post() {
////        RequestBody body = new RequestBody()
////                .add("city", "长沙")
////                .add("key", "13cb58f5884f9749287abbead9c658f2");
////        Request request = new Request.Builder().url("http://restapi.amap" +
////                ".com/v3/weather/weatherInfo").post(body).build();
////        client.newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(Call call, Throwable throwable) {
////                showReult(tv_test, throwable.getMessage());
////            }
////
////            @Override
////            public void onResponse(Call call, Response response) {
////                showReult(tv_test, response.getBody());
////            }
////        });
////    }
//
//    /**
//     * 显示请求结果
//     */
//    private void showReult(final TextView tv, final String result) {
//        tv.post(new Runnable() {
//            @Override
//            public void run() {
//                tv.setText(result);
//            }
//        });
//    }
//
//
//    /**
//     * 测试面试题目
//     */
//    private void testMianShi() {
//        startActivity(new Intent(this, ThreadLeakActivity.class));
//    }
//}
