package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView tv=findViewById(R.id.tv);


        SpannableStringBuilder builder = (SpannableStringBuilder) Html.fromHtml("<p>34234234__234234__</p>");


        tv.setText(builder);
    }
}
