//package com.example.uitestdemo.fragment.components.popupwindow;
//
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.uitestdemo.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class BasicPopupWindowActivity extends AppCompatActivity {
//    @BindView(R.id.tv_show)
//    TextView tvShow;
//    private PopupWindow baseUse;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_basic_popup_window);
//        ButterKnife.bind(this);
//        initBaseUse();
//    }
//
//
//    private void initBaseUse() {
//        View view = LayoutInflater.from(this).inflate(R.layout.popup_item, null);
//        baseUse = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        baseUse.setAnimationStyle(R.style.RightPopAnim);
//        baseUse.setFocusable(true);
//        baseUse.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        baseUse.setOutsideTouchable(true);
//    }
//
//
//    @OnClick(R.id.tv_show)
//    public void onViewClicked() {
//        baseUse.showAsDropDown(tvShow);
//
//    }
//}
