package com.example.kotlintestdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlintestdemo.databinding.ActivityTestMvvmBinding

class TestMvvmActivity : AppCompatActivity() {
    var viewModel: TestMvvmViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data: ActivityTestMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_test_mvvm)
        data.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(TestMvvmViewModel::class.java)
        data.viewmodel = viewModel
        data.tvTest.setOnClickListener { viewModel!!.changPersonName() }
        viewModel!!.mCurrentApiStr.observe(this, Observer { s -> Log.e("数据改变了", "mCurrentApiStr====$s") })
    }
}