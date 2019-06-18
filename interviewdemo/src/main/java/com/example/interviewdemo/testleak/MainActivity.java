package com.example.interviewdemo.testleak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.interviewdemo.R;
import com.example.interviewdemo.okhttp.HttpClient;
import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.call.Call;
import com.example.interviewdemo.okhttp.call.Callback;
import com.example.interviewdemo.okhttp.chain.Interceptor;
import com.example.interviewdemo.okhttp.chain.InterceptorChain;
import com.example.interviewdemo.okhttp.request.Request;
import com.example.interviewdemo.okhttp.request.RequestBody;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView tv_test;


    HttpClient httpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                testMianShi();
                for (int i = 0; i < 50; i++) {
                    get();
                    post();
                }
//                get();
//                post();
//                post();
//                get();
//                get();
//                post();
//                get();
//                post();
//                get();
//                get();
//                post();
//                get();
            }
        });


        initHttpClient();

    }

    HttpClient client;

    private void initHttpClient() {
        client = new HttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(InterceptorChain chain) throws IOException {
                //dosomething
                Response proceed = chain.proceed();
                //dosomething
                return proceed;
            }
        }).build();

    }

    /**
     * get请求
     */
    public void get() {
        Request request = new Request.Builder()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                showReult(tv_test, throwable.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                showReult(tv_test, response.getBody());
            }
        });
    }

    /**
     * post请求
     */
    public void post() {
        RequestBody body = new RequestBody()
                .add("city", "长沙")
                .add("key", "13cb58f5884f9749287abbead9c658f2");
        Request request = new Request.Builder().url("http://restapi.amap" +
                ".com/v3/weather/weatherInfo").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                showReult(tv_test, throwable.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                showReult(tv_test, response.getBody());
            }
        });
    }

    /**
     * 显示请求结果
     */
    private void showReult(final TextView tv, final String result) {
        tv.post(new Runnable() {
            @Override
            public void run() {
                tv.setText(result);
            }
        });
    }


    /**
     * 测试面试题目
     */
    private void testMianShi() {
        startActivity(new Intent(this, ThreadLeakActivity.class));
    }
}
