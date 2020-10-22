//package com.example.uitestdemo.fragment.components.popupwindow;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.uitestdemo.R;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class PopupWindowViewActivity extends AppCompatActivity {
//
//    @BindView(R.id.tv_basic)
//    TextView tvBasic;
//    @BindView(R.id.tv_easypopup)
//    TextView tvEasypopup;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_popup_window_view);
//        ButterKnife.bind(this);
//    }
//
//    @OnClick({R.id.tv_basic, R.id.tv_easypopup})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_basic:
//                startActivity(new Intent(PopupWindowViewActivity.this,BasicPopupWindowActivity.class));
//                break;
//            case R.id.tv_easypopup:
//                startActivity(new Intent(PopupWindowViewActivity.this,EasyPopActivity.class));
//                break;
//        }
//    }
//}
