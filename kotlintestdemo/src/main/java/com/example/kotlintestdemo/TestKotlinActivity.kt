package com.example.kotlintestdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_kotlin.*

class TestKotlinActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_kotlin)
        tv_button.setOnClickListener(View.OnClickListener {view :View->
            Toast.makeText(this,"onClick", Toast.LENGTH_SHORT).show()
        }
        )

        tv_button.setOnClickListener(fun (view:View ){
            Toast.makeText(this,"two", Toast.LENGTH_SHORT).show()
        })


        tv_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
//                Toast.makeText(TestKotlinActivity.,"onClick",Toast.LENGTH_SHORT).show()
            }
        })

    }
}