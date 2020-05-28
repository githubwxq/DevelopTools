package com.example.kotlintestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kotlintestdemo.databinding.ActivityTestMvvmBinding;

public class TestMvvmActivity extends AppCompatActivity {
    TestMvvmViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestMvvmBinding data= DataBindingUtil.setContentView(this,R.layout.activity_test_mvvm);
        data.setLifecycleOwner(this);
         viewModel = ViewModelProviders.of(this).get(TestMvvmViewModel.class);
        data.setViewmodel(viewModel);
        data.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.changPersonName();




            }
        });
        viewModel.mCurrentApiStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("数据改变了","mCurrentApiStr===="+s);
            }
        });
    }
}
